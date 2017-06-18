package budget.saveit.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import budget.saveit.R;
import budget.saveit.view.MainActivity;

/**
 * Created by mortuie on 14/06/17.
 */

public class LoginScreen extends Activity {

    private Button login;
    private Button signUp;
    private Button quit;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_login);

        login = (Button) findViewById(R.id.login);
        signUp = (Button) findViewById(R.id.signUp);
        quit = (Button) findViewById(R.id.quit);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    onDestroy();
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    Intent loginPage = new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(loginPage);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong combination of details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent signUpPage = new Intent(LoginScreen.this, SignUpPage.class);
                startActivity(signUpPage);
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


}
