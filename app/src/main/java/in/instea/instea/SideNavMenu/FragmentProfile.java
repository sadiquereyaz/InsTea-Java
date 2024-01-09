package in.instea.instea.SideNavMenu;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.instea.instea.ActivityHome;
import in.instea.instea.R;
import in.instea.instea.UniversityAdapter;

public class FragmentProfile extends Fragment {
//    AutoCompleteTextView autoCompleteTextViewUniversity, autoCompleteTextViewDepartment, autoCompleteTextViewSemester;
    //    String
    TextInputLayout layoutUsername, layoutFullname;
    ImageView imgProfile;
    private String[] universityName, departmentName, semesterName;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextInputEditText universityField, departmentField, semesterField;
    private AlertDialog dialog;
    private DatabaseReference uniRef, usersRef;
//    private ArrayAdapter<String> universityAdapter;
//    private ArrayAdapter<String> departmentAdapter;
//    private ArrayAdapter<String> semesterAdapter;
    //    DatabaseReference databaseReference;
    String FromSharedPrefUniversity, FromSharedPrefSemester, FromSharedPrefDepartment, FromSharedPrefFullname, FromSharedPrefGender,
            FromSharedPrefUsername, updatedFullname, updatedDepartment, updatedSemester, updatedUniversity;
    TextView txtFullname, txtUniversity, txtDepartment, txtSemester;
    Button updateBtn;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtFullname = view.findViewById(R.id.heading_profile_fullname);
        txtUniversity = view.findViewById(R.id.heading_profile_university);
        txtDepartment = view.findViewById(R.id.heading_profile_department);
        txtSemester = view.findViewById(R.id.heading_profile_semester);
        imgProfile = view.findViewById(R.id.profile_pic);

        layoutUsername = view.findViewById(R.id.profile_layout_username);
        layoutFullname = view.findViewById(R.id.fullname_layout_profile);
//        autoCompleteTextViewUniversity = view.findViewById(R.id.university_profile);
//        autoCompleteTextViewDepartment = view.findViewById(R.id.profile_department);
//        autoCompleteTextViewSemester = view.findViewById(R.id.profile_semester);

        universityField = view.findViewById(R.id.textInputEditTextUniversity);
        semesterField = view.findViewById(R.id.textInputEditTextSemester);
        departmentField = view.findViewById(R.id.textInputEditTextDepartment);

        semesterField.clearFocus();
        departmentField.clearFocus();
        universityField.clearFocus();

        updateBtn = view.findViewById(R.id.btn_update);

        preferences = requireActivity().getSharedPreferences("USER_DATA", MODE_PRIVATE); //we are saving the details in the "UserData" SharedPreferences "file"
        editor = preferences.edit();

//        SharedData sharedData = SharedData.getInstance();
//        String university = sharedData.getUni();
//        String department = sharedData.getDep();
//        String semester = sharedData.getSem();

//        Retrieve SharedPreferences
        FromSharedPrefUniversity = preferences.getString("university", "Aspirant");      //the second is a default value that will be returned if the "university" key is not found in the SharedPreferences file. if the value is string, only then it wil be under double quote
        FromSharedPrefDepartment = preferences.getString("department", "Blank");
        FromSharedPrefSemester = preferences.getString("semester", "blank");
        FromSharedPrefFullname = preferences.getString("fullname", "blank");
        FromSharedPrefUsername = preferences.getString("username", "blank");
        FromSharedPrefGender = preferences.getString("gender", "male");

        universityField.setText(FromSharedPrefUniversity);
        departmentField.setText(FromSharedPrefDepartment);
        semesterField.setText(FromSharedPrefSemester);

        uniRef = FirebaseDatabase.getInstance().getReference("university");
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        loadUniversityNames();
        loadDepartmentNames(FromSharedPrefUniversity);
        loadSemesterNames(FromSharedPrefUniversity,FromSharedPrefDepartment);

        if (FromSharedPrefGender.equals("Female")) {
            imgProfile.setImageResource(R.drawable.female);
        }

        updatedFullname = FromSharedPrefFullname;
        updatedUniversity = FromSharedPrefUniversity;
        updatedDepartment = FromSharedPrefDepartment;
        updatedSemester = FromSharedPrefSemester;

        // setting text at input feilds
        layoutUsername.getEditText().setText(FromSharedPrefUsername);
        layoutFullname.getEditText().setText(FromSharedPrefFullname);

        // setting text at heading
        txtFullname.setText(FromSharedPrefFullname);    //heading
        txtUniversity.setText(FromSharedPrefUniversity);    //heading
        txtDepartment.setText(FromSharedPrefDepartment);    //heading
        txtSemester.setText(FromSharedPrefSemester);

//        universityAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line);
//        autoCompleteTextViewUniversity.setAdapter(universityAdapter);
//
//        departmentAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line);
//        autoCompleteTextViewDepartment.setAdapter(departmentAdapter);
//
//        semesterAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line);
//        autoCompleteTextViewSemester.setAdapter(semesterAdapter);

        universityField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUniversityNames();
                showUniversityDialog();
            }
        });
        departmentField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eU = universityField.getText().toString();
                if (!eU.isEmpty()) {
                    loadDepartmentNames(eU);
                    showDepartmentDialog();
                } else {
                    Toast.makeText(getContext(), "Select Your University First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        semesterField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eU = universityField.getText().toString();
                String eD = departmentField.getText().toString();

                if (!eU.isEmpty() && !eD.isEmpty()) {
                    loadSemesterNames(eU, eD);
                    showSemesterDialog();
                } else {
                    Toast.makeText(getContext(), "Select Preceding Entries First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Load university names into the first AutoCompleteTextView
//        Query universityQuery = uniRef.orderByKey();
//        universityQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<String> universities = new ArrayList<>();
//
//                for (DataSnapshot universitySnapshot : dataSnapshot.getChildren()) {
//                    universities.add(universitySnapshot.getKey());
//                }
//                universityAdapter.addAll(universities);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle error
//            }
//        });

        // Set listeners to update department and semester based on user selections
//        autoCompleteTextViewUniversity.setOnItemClickListener((parent, view1, position, id) -> {
//            String selectedUniversity = (String) parent.getItemAtPosition(position);
//            loadDepartments(selectedUniversity);
//
//        });
//
//        autoCompleteTextViewDepartment.setOnItemClickListener((parent, view1, position, id) -> {
//            String selectedUniversity = autoCompleteTextViewUniversity.getText().toString();
//            String selectedDepartment = (String) parent.getItemAtPosition(position);
//            loadSemesters(selectedUniversity, selectedDepartment);
//        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredFullname, enteredUniversity, enteredDepartment, enteredSemester;
                enteredFullname = layoutFullname.getEditText().getText().toString().trim();
                enteredUniversity = universityField.getText().toString().trim();
                enteredDepartment = departmentField.getText().toString().trim();
                enteredSemester = semesterField.getText().toString().trim();

                if (enteredFullname.isEmpty()) {
                    layoutFullname.setError("Full name is required");
                    return;
                } else {
                    layoutFullname.setError(null); // Clear any previous error
                }

                if (enteredUniversity.isEmpty()) {
                    universityField.setError("University is required");
                    return;
                } else {
                    universityField.setError(null); // Clear any previous error
                }

                if (enteredDepartment.isEmpty()) {
                    departmentField.setError("Department is required");
                    return;
                } else {
                    departmentField.setError(null); // Clear any previous error
                }

                if (enteredSemester.isEmpty()) {
                    semesterField.setError("Semester is required");
                    return;
                } else {
                    semesterField.setError(null); // Clear any previous error
                }

                boolean fullnameChange = fullnameChange();
                boolean departmentChange = departmentChange();
                boolean universityChange = universityChange();
                boolean semesterChange = semesterChange();

                if (fullnameChange || departmentChange || universityChange || semesterChange) {
                    if (fullnameChange) {
                        updateUserDataInSharedPreferences(updatedFullname, updatedDepartment, updatedUniversity, updatedSemester);
                        Toast.makeText(getContext(), "Updated Successful", Toast.LENGTH_SHORT).show();
                    }
                    if (universityChange) {
                        updateUserDataInSharedPreferences(updatedFullname, updatedDepartment, updatedUniversity, updatedSemester);
                        Toast.makeText(getContext(), "Updated Successful", Toast.LENGTH_SHORT).show();
                    }
                    if (departmentChange) {
                        updateUserDataInSharedPreferences(updatedFullname, updatedDepartment, updatedUniversity, updatedSemester);
                        Toast.makeText(getContext(), "Updated Successful", Toast.LENGTH_SHORT).show();
                    }
                    if (semesterChange) {
                        updateUserDataInSharedPreferences(updatedFullname, updatedDepartment, updatedUniversity, updatedSemester);
                        Toast.makeText(getContext(), "Updated Successful", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "You did not make any changes", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getActivity(), ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                // Start the main activity
                startActivity(intent);
                // Finish the current activity (FragmentProfile)
//                getActivity().finish();
            }
        });
        return view;
    }
    private void loadUniversityNames() {
        uniRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Assuming your canteen names are stored as children under "canteens" node
                List<String> universityList = new ArrayList<>();
                for (DataSnapshot universitySnapshot : dataSnapshot.getChildren()) {
                    String universityName = universitySnapshot.getKey();

                    if (universityName != null) {
                        universityList.add(universityName);
                    }
                }
                // Convert the list to an array
                universityName = universityList.toArray(new String[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    private void showUniversityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select a University");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.canteenRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        UniversityAdapter adapter = new UniversityAdapter(universityName, new UniversityAdapter.OnUniversityClickListener() {
            @Override
            public void onUniversityClick(String universityName) {

                editor.putString("university", universityName);
                editor.apply();

                loadDepartmentNames(universityName);                departmentField.setText("");
                semesterField.setText("");
                universityField.setText(universityName);
                editor.putString("university", universityName);
                editor.apply();

                if (dialog != null) {
                    dialog.dismiss(); // Close the dialog when a canteen is selected
                }
            }
        });
        recyclerView.setAdapter(adapter);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = builder.create(); // Initialize the dialog here
        dialog.show();
    }
    private void loadDepartmentNames(String enteredUniversity) {
        uniRef.child(enteredUniversity).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Assuming your canteen names are stored as children under "canteens" node
                List<String> departmentList = new ArrayList<>();
                for (DataSnapshot departmentSnapshot : dataSnapshot.getChildren()) {
                    String departmentName = departmentSnapshot.getKey();

                    if (departmentName != null) {
                        departmentList.add(departmentName);
                    }
                }
                // Convert the list to an array
                departmentName = departmentList.toArray(new String[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    private void showDepartmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Your Department");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.canteenRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        UniversityAdapter adapter = new UniversityAdapter(departmentName, new UniversityAdapter.OnUniversityClickListener() {
            @Override
            public void onUniversityClick(String departmentName) {
                String eUni = universityField.getText().toString();
                loadSemesterNames(eUni, departmentName);
                departmentField.setText(departmentName);
                semesterField.setText("");
                editor.putString("department", departmentName);
                editor.apply();

                if (dialog != null) {
                    dialog.dismiss(); // Close the dialog when a canteen is selected
                }
            }
        });
        recyclerView.setAdapter(adapter);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = builder.create(); // Initialize the dialog here
        dialog.show();
    }
    private void loadSemesterNames(String enteredUniversity, String enteredDepartment) {

        uniRef.child(enteredUniversity).child(enteredDepartment).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Assuming your canteen names are stored as children under "canteens" node
                List<String> semesterList = new ArrayList<>();
                for (DataSnapshot semesterSnapshot : dataSnapshot.getChildren()) {
                    String semesterName = semesterSnapshot.getKey();

                    if (semesterName != null) {
                        semesterList.add(semesterName);
                    }
                }
                // Convert the list to an array
                semesterName = semesterList.toArray(new String[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    private void showSemesterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Your Semester");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.canteenRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        UniversityAdapter adapter = new UniversityAdapter(semesterName, new UniversityAdapter.OnUniversityClickListener() {
            @Override
            public void onUniversityClick(String semesterName) {
                semesterField.setText(semesterName);
                editor.putString("semester", semesterName);
                editor.apply();

                if (dialog != null) {
                    dialog.dismiss(); // Close the dialog when a canteen is selected
                }
            }
        });
        recyclerView.setAdapter(adapter);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = builder.create(); // Initialize the dialog here
        dialog.show();
    }
//    private void loadDepartments(String universityName) {
//        departmentAdapter.clear();
//        semesterAdapter.clear();
//
//        Query departmentQuery = uniRef.child(universityName).orderByKey();
//        departmentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<String> departments = new ArrayList<>();
//                for (DataSnapshot departmentSnapshot : dataSnapshot.getChildren()) {
//                    departments.add(departmentSnapshot.getKey());
//                }
//                departmentAdapter.addAll(departments);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//    }
//    private void loadSemesters(String universityName, String departmentName) {
//        semesterAdapter.clear();
//
//        Query semesterQuery = uniRef.child(universityName).child(departmentName).orderByKey();
//        semesterQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<String> semesters = new ArrayList<>();
//                for (DataSnapshot semesterSnapshot : dataSnapshot.getChildren()) {
//                    semesters.add(semesterSnapshot.getKey());
//                }
//                semesterAdapter.addAll(semesters);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//    }

    //updating fullname in students database and apps profile fragment
    private boolean fullnameChange() {
        String newFullname = layoutFullname.getEditText().getText().toString();
        if (!FromSharedPrefFullname.equals(newFullname)) {
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            uniRef = database.getReference("users");
            usersRef.child(FromSharedPrefUsername).child("fullname").setValue(newFullname);
            updatedFullname = newFullname;
            txtFullname.setText(updatedFullname);
            return true;
        } else {
            return false;
        }
    }
    private boolean universityChange() {
        String newUniversity = universityField.getText().toString();
        if (!FromSharedPrefUniversity.equals(newUniversity)) {
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            uniRef = database.getReference("users");
            usersRef.child(FromSharedPrefUsername).child("university").setValue(newUniversity);
            updatedUniversity = newUniversity;
            txtUniversity.setText(updatedUniversity);
            return true;
        } else {
            return false;
        }
    }
    private boolean departmentChange() {
        String newDepartment = departmentField.getText().toString().trim();
        if (!FromSharedPrefDepartment.equals(newDepartment)) {
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            uniRef = database.getReference("users");
            usersRef.child(FromSharedPrefUsername).child("department").setValue(newDepartment);
            updatedDepartment = newDepartment;
            txtDepartment.setText(updatedDepartment);
            return true;
        } else {
            return false;
        }
    }

    //updating semester in students database and profile fragment
    private boolean semesterChange() {
        String newSemester = semesterField.getText().toString();
        if (!FromSharedPrefSemester.equals(newSemester)) {
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            uniRef = database.getReference("users");
            usersRef.child(FromSharedPrefUsername).child("semester").setValue(newSemester);
            updatedSemester = newSemester;
            txtSemester.setText(updatedSemester);
            return true;
        } else {
            return false;
        }
    }

    private void updateUserDataInSharedPreferences(String fullname, String department, String university, String semester) {
        SharedPreferences preferences = requireActivity().getSharedPreferences("USER_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("fullname", fullname);
        editor.putString("university", university);
        editor.putString("department", department);
        editor.putString("semester", semester);
        editor.apply();
    }
}