package android.androidchallenge.core.retrofit;

public interface ResponseCallback {
    void success(Object object);
    void fail(String error);
}
