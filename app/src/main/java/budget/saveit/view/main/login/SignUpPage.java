package budget.saveit.view.main.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import budget.saveit.R;

/**
 * Created by mortuie on 15/06/17.
 */

public class SignUpPage extends Activity {

    private Button createAccount;
    private Button back;
    private EditText username;
    private EditText firstName;
    private EditText surname;
    private EditText email;
    private EditText password1;
    private EditText password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_signup);
        setTitle("Signup Page");

        createAccount = (Button) findViewById(R.id.createAccount);
        back = (Button) findViewById(R.id.back);
        username = (EditText) findViewById(R.id.username);
        firstName = (EditText) findViewById(R.id.firstName);
        surname = (EditText) findViewById(R.id.surname);
        email = (EditText) findViewById(R.id.email);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                Toast.makeText(getApplicationContext(), "Going back...", Toast.LENGTH_SHORT).show();
                Intent backToLogin = new Intent(SignUpPage.this, LoginScreen.class);
                startActivity(backToLogin);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


}
