package budget.saveit.helper;

import android.os.Build;
import android.widget.Button;

/**
 * Created by hampe on 19 June 2017.
 */

public class CompatHelper {
    public static void removeButtonBorder(Button button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setOutlineProvider(null);
        }
    }
}
