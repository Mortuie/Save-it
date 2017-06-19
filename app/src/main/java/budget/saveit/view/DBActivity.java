package budget.saveit.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import budget.saveit.model.db.DB;

/**
 * Created by hampe on 19 June 2017.
 */

public class DBActivity extends AppCompatActivity {
    protected DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
