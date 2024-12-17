package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginbutton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbutton = findViewById(R.id.loginbutton);
        TextView signupText = findViewById(R.id.signupText);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Handle signup redirection
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        // Handle login button click
        loginbutton.setOnClickListener(v -> {
            String userInput = username.getText().toString();
            String passInput = password.getText().toString();

            // Check if email format is valid
            if (!isValidEmail(userInput)) {
                Toast.makeText(MainActivity.this, "Invalid Email Format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Login using Firebase Authentication
            mAuth.signInWithEmailAndPassword(userInput, passInput)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Login successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            // Navigate to MainActivity3
                            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                            startActivity(intent);
                        } else {
                            // Login failed
                            Toast.makeText(MainActivity.this, "Incorrect Email & Password", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
