package android.androidchallenge.base;

import android.androidchallenge.util.loadingMontion.RequestLoadingMotion;
import android.androidchallenge.util.loadingMontion.RequestLoadingMotionInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rob on 9/22/17.
 */

public class BaseActivity extends AppCompatActivity implements RequestLoadingMotionInterface {
    protected RequestLoadingMotion requestLoadingMotion;

    @Override
    public RequestLoadingMotion getRequestLoadingMotion() {
        if (null == requestLoadingMotion) {
            if (findViewById(android.R.id.content) != null) {
                requestLoadingMotion = new RequestLoadingMotion((ViewGroup) findViewById(android.R.id.content), getApplicationContext());
            }
        }
        return requestLoadingMotion;
    }
}
