package android.androidchallenge.util.loadingMontion;

import android.androidchallenge.R;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by rob on 9/22/17.
 */
public class RequestLoadingMotion extends ProgressBar {
    private RelativeLayout relativeLayout;

    public RequestLoadingMotion(Context context) {
        super(context);
    }

    public RequestLoadingMotion(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RequestLoadingMotion(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RequestLoadingMotion(ViewGroup topView, Context context) {
        super(context);
        relativeLayout = new RelativeLayout(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(layoutParams);

        relativeLayout.addView(this);

        topView.addView(relativeLayout);
        relativeLayout.setClickable(true);
        relativeLayout.setVisibility(INVISIBLE);
        relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.loadingBackground));
    }

    public void show() {
        relativeLayout.setVisibility(VISIBLE);
    }

    public void hide() {
        relativeLayout.setVisibility(INVISIBLE);
    }
}
