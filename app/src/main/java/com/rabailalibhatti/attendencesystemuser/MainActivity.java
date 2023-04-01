package com.rabailalibhatti.attendencesystemuser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextInputEditText username, password;

    ArrayAdapter<String> arrayAdapter;
    MaterialAutoCompleteTextView dropdownmenu;

    public static String pusername;
    public static String pPassword;
    public static String userKey;

    DatabaseReference db;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CheckBox checkk;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        String key = sharedPreferences.getString("key", "");
        String type = sharedPreferences.getString("type", "");

        if (!key.isEmpty() && !type.isEmpty()) {
            if (isLoggedIn) {
                if (type.equals("Teacher")) {
                    startIntent(TeacherMainscreen.class, "KEY", key);
                } else if (type.equals("Student")) {
                    startIntent(StudentMainscreen.class, "KEY", key);
                } else if (type.equals("Admin")) {
                    startIntent(AdminMainscreen.class, "Data", "ADMIN");
                }
            }
        } else {
            setContentView(R.layout.activity_main);
            dropdownmenu = findViewById(R.id.dropdownmenu);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
            arrayAdapter.add("Admin");
            arrayAdapter.add("Teacher");
            arrayAdapter.add("Student");
            dropdownmenu.setAdapter(arrayAdapter);
        }

        username = findViewById(R.id.editview1);
        password = findViewById(R.id.editview2);

        checkk = findViewById(R.id.checkk);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Logging In..");
        progressDialog.setCancelable(false);

        db = FirebaseDatabase.getInstance().getReference();


    }

    public void openregistration(View view) {
        startActivity(new Intent(MainActivity.this, Registration.class));
    }

    public void startIntent(Class<?> cls, String input, String input_Text) {
        Intent intent = new Intent(MainActivity.this, cls);
        intent.putExtra(input, input_Text);
        startActivity(intent);
        finish();
    }

    public void openlogin(View view) {
        progressDialog.show();

        String user = username.getText().toString();
        String pass = password.getText().toString();
        String dropDownText = dropdownmenu.getText().toString();

        pusername = user;
        pPassword = pass;

        if (user.isEmpty()) {
            username.setError("Please Enter Username");
            progressDialog.dismiss();
        } else if (pass.isEmpty()) {
            password.setError("Please Enter Password");
                        progressDialog.dismiss();
        } else if (dropDownText.isEmpty()) {
            dropdownmenu.setError("Please Select Type");
                        progressDialog.dismiss();
        } else {
            if (dropDownText.equals("Admin") && (user.equals("admin") && pass.equals("admin123"))) {
                startActivity(new Intent(MainActivity.this, AdminMainscreen.class));
                sharedP("Admin", "RAKA");
                progressDialog.dismiss();
                finish();
            } else if (dropDownText.equals("Student")) {
                studentLogin(user, pass);
            } else if (dropDownText.equals("Teacher")) {
                teacherLogin(user, pass);
            } else {
                Toast.makeText(this, "Invalid authentication", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void studentLogin(String userName, String passWord) {
        FirebaseDatabase.getInstance().getReference("Students").orderByChild("userName").equalTo(userName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                studentModel model = userSnapshot.getValue(studentModel.class);
                                if (model.getPassword().equals(passWord)) {
                                    // Save the login status in the SharedPreferences
                                    if (checkk.isChecked()) {
                                        sharedP("Student", model.getKey());
                                    }
                                    startIntent(StudentMainscreen.class, "KEY", model.getKey());
                                    progressDialog.dismiss();

                                } else {
                                    Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void teacherLogin(String userName, String passWord) {
        FirebaseDatabase.getInstance().getReference("Teachers").orderByChild("username").equalTo(userName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            teacherModel model = userSnapshot.getValue(teacherModel.class);
                            if (model.getPassword().equals(passWord)) {
                                // Save the login status in the SharedPreferences
                                if (checkk.isChecked()) {
                                    sharedP("Teacher", model.getKey());
                                }
                                startIntent(TeacherMainscreen.class, "KEY", model.getKey());
                                progressDialog.dismiss();

                            } else {
                                // Display an error message
                                Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void sharedP(String type, String key) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("type", type);
        editor.putString("key", key);
        editor.apply();
    }
}