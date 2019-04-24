package co.edu.escuelaing.taskmanagerapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login;

    TextView attempts;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        attempts = findViewById(R.id.attempts);
        attempts.setVisibility(View.GONE);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    Toast.makeText(LoginActivity.this,"You have Authenticated Successfully", Toast.LENGTH_LONG).show();

                } else if (username.length() < 3) {
                    username.setError("At least 3 alphanumeric characters");

                } else if (password.length() < 4) {
                    password.setError("At least 4 alphanumeric characters");

                } else {
                    Toast.makeText(LoginActivity.this,"Authentication Failed!",Toast.LENGTH_LONG).show();
                    attempts.setVisibility(View.VISIBLE);
                    attempts.setBackgroundColor(Color.YELLOW);
                    counter--;
                    attempts.setText(Integer.toString(counter));
                    if (counter == 0) {
                        login.setEnabled(false);
                    }
                }
            }
        });
    }
}
