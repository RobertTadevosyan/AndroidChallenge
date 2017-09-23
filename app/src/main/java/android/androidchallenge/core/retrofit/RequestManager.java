package android.androidchallenge.core.retrofit;

import android.androidchallenge.core.models.ResponseDataResult;
import android.androidchallenge.util.Constants;
import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rob on 9/22/17.
 */

public class RequestManager {
    private static final String method = "flickr.photos.search";
    private static final String format = "json";
    private static final int nojsoncallback = 1;
    private static final int safe_search = 1;

    public void getSerachResults(Context context, String text, final ResponseCallback callback) {
        Call<ResponseDataResult> call = RetrofitHelper.requestService(context).getSearchResults(
                method,
                Constants.FLICKR_KEY,
                format,
                nojsoncallback,
                safe_search, text);
        call.enqueue(new Callback<ResponseDataResult>() {
            @Override
            public void onResponse(Call<ResponseDataResult> call, Response<ResponseDataResult> response) {
                if (response == null || response.body() == null) {
                    callback.fail("Something went wrong!");
                    return;
                }
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<ResponseDataResult> call, Throwable t) {
                callback.fail(t.getLocalizedMessage());
            }
        });
    }
}
