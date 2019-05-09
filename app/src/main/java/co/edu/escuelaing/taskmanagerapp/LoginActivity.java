package co.edu.escuelaing.taskmanagerapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private EditText username, password;

    private Button login;

    private TextView attempts;

    private int counter = 3;

    private AuthService authService;

    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );


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
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") //localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authService = retrofit.create(AuthService.class);

    }

    public void onLoginClicked(View view) {

        executorService.execute(new Runnable() {

            @Override
            public void run() {

                try {
                    Response<Token> response =
                            authService.login(new LoginWrapper("test@mail.com", "password")).execute();
                    Token token = response.body();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
