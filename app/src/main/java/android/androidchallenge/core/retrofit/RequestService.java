package android.androidchallenge.core.retrofit;


import android.androidchallenge.core.models.ResponseDataResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestService {

    @GET("services/rest/")
    Call<ResponseDataResult> getSearchResults(
            @Query("method") String method,
            @Query("api_key") String api_key,
            @Query("format") String format,
            @Query("nojsoncallback") int nojsoncallback,
            @Query("safe_search") int safe_search,
            @Query("text") String text);

}

