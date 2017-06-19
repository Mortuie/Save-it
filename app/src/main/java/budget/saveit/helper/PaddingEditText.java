package budget.saveit.helper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by aa on 19 June 2017.
 */

@SuppressLint("AppCompatCustomView")
public class PaddingEditText extends EditText {
    public PaddingEditText(Context context) {
        super(context);
    }

    public PaddingEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaddingEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaddingEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

// --------------------------------------->

    @Override
    public void setBackgroundResource(int resid) {
        int pl = getPaddingLeft();
        int pt = getPaddingTop();
        int pr = getPaddingRight();
        int pb = getPaddingBottom();

        super.setBackgroundResource(resid);

        this.setPadding(pl, pt, pr, pb);
    }
}
