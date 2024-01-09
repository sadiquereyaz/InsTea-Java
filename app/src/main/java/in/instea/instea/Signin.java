package in.instea.instea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {

    Button signinBtn;
    TextInputLayout usernameLayout, passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        signinBtn = findViewById(R.id.login_btn);
        usernameLayout = findViewById(R.id.signin_layout_username);
        passwordLayout = findViewById(R.id.signin_layout_password);

        // Check if user data exists in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        if (preferences.contains("username")) {
            // User data exists, directly navigate to the profile activity
            Intent intent = new Intent(getApplicationContext(), ActivityHome.class);

            SharedData sharedData = SharedData.getInstance();
            sharedData.setUni(preferences.getString("university", "Aspirant"));
            sharedData.setDep(preferences.getString("department",""));
            sharedData.setSem(preferences.getString("semester",""));

            startActivity(intent);
            finish();
        }
//        signinBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //getting data entered at login page
//                final String username_data = username_var.getEditText().getText().toString().trim();       //final is used so that user cannot change it meanwhile
//                final String password_data = password_var.getEditText().getText().toString().trim();
//
//                //getting data from firebase
//                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                DatabaseReference databaseReference = firebaseDatabase.getReference("users");
//
//                //checking for existence of data in database
//                Query check_username = databaseReference.orderByChild("username").equalTo(username_data);
//
//                check_username.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            username_var.setError(null);
//                            username_var.setErrorEnabled(false);
//
////                            //getting password from the firebase for the entered username
//                            String check_password = snapshot.child(username_data).child("password").getValue(String.class);
//
////                            //check if entered password matches the password at firebase
//                            if (check_password.equals(password_data)) {
//                                password_var.setError(null);
//                                password_var.setErrorEnabled(false);
//
//                                Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
//
//                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//                                startActivity(intent);
//                                finish();
//
//                            } else {
//                                password_var.setError("Wrong Password");
//                            }
//
//                        } else {
//                            username_var.setError("User doesn't exists");
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // Error handling if the query is canceled or fails
//                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }

    public void toHomeScreen(View view) {        //on clicking login button

        //getting data entered at login page
        final String enteredUsername = usernameLayout.getEditText().getText().toString().trim();             //trim(): The String class provides the trim() method, which removes any leading and trailing whitespace characters (such as spaces or tabs)
        final String enteredPassword= passwordLayout.getEditText().getText().toString().trim();             //final is used so that user cannot change it meanwhile

        if (!enteredUsername.isEmpty()) {
            usernameLayout.setError(null);
            usernameLayout.setErrorEnabled(false);

            if (!enteredPassword.isEmpty()) {
                passwordLayout.setError(null);
                passwordLayout.setErrorEnabled(false);
                final Toast pleaseWaitToast = Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT);
                pleaseWaitToast.show();

                //getting data from firebase
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("users");
                // or alternatively
                // DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

                //checking for existence of data in database
                Query check_username = databaseReference.orderByChild("username").equalTo(enteredUsername);

                check_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            usernameLayout.setError(null);
                            usernameLayout.setErrorEnabled(false);

                            //getting password from the firebase for the entered username
                            String passwordFrmDb = snapshot.child(enteredUsername).child("password").getValue(String.class);

                            if (passwordFrmDb.equals(enteredPassword)) {
                                passwordLayout.setError(null);
                                passwordLayout.setErrorEnabled(false);

                                // Save user data to SharedPreferences (offline)
                                SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE); //we are saving the details in the "UserData" SharedPreferences "file"
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("username", enteredUsername);
                                editor.putString("fullname", snapshot.child(enteredUsername).child("fullname").getValue(String.class));
                                editor.putString("gender", snapshot.child(enteredUsername).child("gender").getValue(String.class));
                                editor.putString("university", snapshot.child(enteredUsername).child("university").getValue(String.class));
                                editor.putString("department", snapshot.child(enteredUsername).child("department").getValue(String.class));
                                editor.putString("semester", snapshot.child(enteredUsername).child("semester").getValue(String.class));
                                editor.putString("email", snapshot.child(enteredUsername).child("email").getValue(String.class));
                                editor.putString("phone", snapshot.child(enteredUsername).child("phone").getValue(String.class));
                                editor.putString("password", enteredPassword);
                                editor.apply();

                                //passing the data to dashboard activity
                                Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
                                startActivity(intent);
                                finish();

                                Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                passwordLayout.setError("Incorrect Password");
                            }

                        } else {
                            usernameLayout.setError("User doesn't exists");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pleaseWaitToast.cancel();
                        // Error handling if the query is canceled or fails
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            } else {
                passwordLayout.setError("Please enter your Password");
            }
        } else {
            usernameLayout.setError("Please enter your Username");
        }
    }
    public void signupscreen(View view) {
        Intent intent = new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
    }
}