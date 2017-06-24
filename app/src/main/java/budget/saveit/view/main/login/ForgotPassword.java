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
 * Created by mortuie on 24/06/17.
 */

public class ForgotPassword extends Activity {

    private EditText email;
    private Button getNewPassword;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_forgot_password);

        email = (EditText) findViewById(R.id.email);
        getNewPassword = (Button) findViewById(R.id.newPassword);
        back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent backToLogin = new Intent(ForgotPassword.this, LoginScreen.class);
                startActivity(backToLogin);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
