package budget.saveit.helper;

import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.Button;

/**
 * Created by hampe on 19 June 2017.
 */

public class CompatHelper {
    public static void removeButtonBorder(@NonNull Button button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            button.setOutlineProvider(null);
        }
    }
}
