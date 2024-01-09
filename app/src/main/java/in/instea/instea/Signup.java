package in.instea.instea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {


    RadioGroup genderRadioGroup;
    RadioButton lastRadioBtn;
    Button registerBtn;
    TextView gender;
    TextInputLayout usernameLayout, fullnameLayout, emailLayout, phoneLayout, passwordLayout;
    AutoCompleteTextView autoCompleteTextViewUniversity, autoCompleteTextViewDepartment, autoCompleteTextViewSemester;
    ArrayAdapter<String> adapterItemUniversity, adapterItemDepartment, adapterItemSemester;
    DatabaseReference databaseReference, usernameCheckRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        gender = findViewById(R.id.gender_txt);
        registerBtn = findViewById(R.id.register_btn);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        lastRadioBtn = findViewById(R.id.other_radio);
        usernameLayout = findViewById(R.id.username_field_signout);
        fullnameLayout = findViewById(R.id.full_name_field);
        emailLayout = findViewById(R.id.email_field);
        phoneLayout = findViewById(R.id.phone_number_registration_field);
        passwordLayout = findViewById(R.id.password_registration_field);
        autoCompleteTextViewUniversity = findViewById(R.id.signup_university);
        autoCompleteTextViewDepartment = findViewById(R.id.signup_department);
        autoCompleteTextViewSemester = findViewById(R.id.signup_semester);

        // Load the options from the string resource
//        String[] optionUniversity = getResources().getStringArray(R.array.item_university);
//        String[] optionDepartment = getResources().getStringArray(R.array.item_department);
//        String[] optionSemester = getResources().getStringArray(R.array.item_semester);

        adapterItemUniversity = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextViewUniversity.setAdapter(adapterItemUniversity);

        adapterItemDepartment = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextViewDepartment.setAdapter(adapterItemDepartment);

        adapterItemSemester = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextViewSemester.setAdapter(adapterItemSemester);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("university");

        // Load university names into the first AutoCompleteTextView
        Query universityQuery = databaseReference.orderByKey();
        universityQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> universities = new ArrayList<>();
                for (DataSnapshot universitySnapshot : dataSnapshot.getChildren()) {
                    universities.add(universitySnapshot.getKey());
                }
                adapterItemUniversity.addAll(universities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        // Set listeners to update department and semester based on user selections
        autoCompleteTextViewUniversity.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedUniversity = (String) parent.getItemAtPosition(position);
            loadDepartments(selectedUniversity);

        });
        autoCompleteTextViewDepartment.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedUniversity = autoCompleteTextViewUniversity.getText().toString();
            String selectedDepartment = (String) parent.getItemAtPosition(position);
            loadSemesters(selectedUniversity, selectedDepartment);
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // getting the ID of the selected radio button from the RadioGroup, later we will obtain the string of selected option
                int selectedRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();
                String enteredUsername = usernameLayout.getEditText().getText().toString().trim();
                String enteredFullname = fullnameLayout.getEditText().getText().toString().trim();
                String enteredPassword = passwordLayout.getEditText().getText().toString().trim();
                String enteredEmail = emailLayout.getEditText().getText().toString().trim();
                String enteredPhone = phoneLayout.getEditText().getText().toString().trim();
                String enteredUniversity = autoCompleteTextViewUniversity.getText().toString().trim();
                String enteredDepartment = autoCompleteTextViewDepartment.getText().toString().trim();
                String enteredSemester = autoCompleteTextViewSemester.getText().toString().trim().trim();

                if (!enteredUsername.isEmpty()) {
                    if (enteredUsername.contains(" ")) {
                        usernameLayout.setError("Username should not contain spaces");
                        return;
                    }
                    usernameLayout.setErrorEnabled(false);
                    usernameLayout.setError(null);

                    usernameCheckRef = FirebaseDatabase.getInstance().getReference("users")
                            .child(enteredUsername);

                    usernameCheckRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                // Username is available, proceed with registration
                                usernameLayout.setError(null);

                                if (!containsDisallowedCharacter(enteredUsername)) {
                                    usernameLayout.setError(null);


                                    if (!enteredFullname.isEmpty()) {
                                        fullnameLayout.setErrorEnabled(false);
                                        fullnameLayout.setError(null);

                                        if (!(selectedRadioButtonId <= 0)) {
                                            gender.setError(null);

                                            //finding the RadioButton that corresponds to the selected ID
                                            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                                            String enteredGender = selectedRadioButton.getText().toString();

                                            if (!enteredUniversity.isEmpty()) {
                                                autoCompleteTextViewUniversity.setError(null);

                                                if (!enteredDepartment.isEmpty()) {
                                                    autoCompleteTextViewDepartment.setError(null);

                                                    if (!enteredSemester.isEmpty()) {
                                                        autoCompleteTextViewSemester.setError(null);

                                                        if (enteredEmail.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
                                                            emailLayout.setErrorEnabled(false);
                                                            emailLayout.setError(null);

                                                            if (enteredPhone.matches("\\d{10}")) {
                                                                phoneLayout.setErrorEnabled(false);
                                                                phoneLayout.setError(null);

                                                                if (!enteredPassword.isEmpty()) {
                                                                    passwordLayout.setErrorEnabled(false);
                                                                    passwordLayout.setError(null);
                                                                    final Toast pleaseWaitToast = Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT);
                                                                    pleaseWaitToast.show();

                                                                    databaseReference = FirebaseDatabase.getInstance().getReference("users");

                                                                    ModelClassSignUp modelClassSignUp = new ModelClassSignUp(enteredUsername, enteredFullname, enteredGender, enteredUniversity, enteredDepartment, enteredSemester, enteredEmail, enteredPhone, enteredPassword);
                                                                    databaseReference.child(enteredUsername).setValue(modelClassSignUp);        //storing data to firebase

                                                                    pleaseWaitToast.cancel();
                                                                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                                                                    Intent intent = new Intent(getApplicationContext(), Signin.class);
                                                                    startActivity(intent);
                                                                    finish();

//                                                    getActivity().onBackPressed();          //finish the current activity or fragment to go back

                                                                } else {
                                                                    passwordLayout.setError("Required");
                                                                    return;
                                                                }
                                                            } else {
                                                                phoneLayout.setError("Invalid Phone Number (Must be 10 digits)");
                                                                return;
                                                            }
                                                        } else {
                                                            emailLayout.setError("Invalid Email address");
                                                            return;
                                                        }
                                                    } else {
                                                        autoCompleteTextViewSemester.setError("Choose Valid Semester");
                                                        return;
                                                    }
                                                } else {
                                                    autoCompleteTextViewDepartment.setError("Choose valid Department");
                                                    return;
                                                }
                                            } else {
                                                autoCompleteTextViewUniversity.setError("Choose valid University");
                                                return;
                                            }
                                        } else {
                                            gender.setError("Select your gender");
                                            return;
                                        }
                                    } else {
                                        fullnameLayout.setError("Enter your Fullname");
                                        return;
                                    }
                                } else {
                                    usernameLayout.setError("Must contain all lower case or valid character");
                                }

                            } else {
                                // Username already exists
                                usernameLayout.setError("Username already taken");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle any errors here
                            Toast.makeText(Signup.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    usernameLayout.setError("Required");
                }
            }
        });
    }

    private void loadDepartments(String universityName) {
        adapterItemDepartment.clear();
        adapterItemSemester.clear();

        Query departmentQuery = databaseReference.child(universityName).orderByKey();
        departmentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> departments = new ArrayList<>();
                for (DataSnapshot departmentSnapshot : dataSnapshot.getChildren()) {
                    departments.add(departmentSnapshot.getKey());
                }
                adapterItemDepartment.addAll(departments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void loadSemesters(String universityName, String departmentName) {
        adapterItemSemester.clear();

        Query semesterQuery = databaseReference.child(universityName).child(departmentName).orderByKey();
        semesterQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> semesters = new ArrayList<>();
                for (DataSnapshot semesterSnapshot : dataSnapshot.getChildren()) {
                    semesters.add(semesterSnapshot.getKey());
                }
                adapterItemSemester.addAll(semesters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void moveToLogin(View view) {
        // You can finish the current activity or fragment to go back
        finish();       // Close the current Activity to go back to the previous one
//        getActivity().onBackPressed();            //for fragment
    }


    private boolean containsDisallowedCharacter(String str) {
//        String disallowedCharPatterns = "[.$#\\[\\]/\\x00-\\x1F\\x7F]";       //node crash character
        String disallowedCharPatterns = "[A-Z.$#\\\\[\\\\]/\\\\x00-\\\\x1F\\\\x7F]";        // no capital letter
        Pattern pattern = Pattern.compile(disallowedCharPatterns);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}