package budget.saveit.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import budget.saveit.R;

/**
 * Created by mortuie on 14/06/17.
 */

public class LoginScreen extends Activity {

    private Button login;
    private Button signUp;
    private EditText username;
    private EditText password;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_login);

        login = (Button) findViewById(R.id.login);
        signUp = (Button) findViewById(R.id.signUp);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
