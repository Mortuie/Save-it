package budget.saveit;

import android.app.Application;

import budget.saveit.model.db.DB;

/**
 * Created by aa on 12/06/17.
 */

public class SaveIt extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DB db = new DB(getApplicationContext());
        db.clearDB();


    }
}
