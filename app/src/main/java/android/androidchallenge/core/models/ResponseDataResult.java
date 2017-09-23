package android.androidchallenge.core.models;

/**
 * Created by rob on 9/22/17.
 */

public class ResponseDataResult {
    private ResponseData photos;
    private String stat;

    public ResponseData getPhotos() {
        return photos;
    }

    public void setPhotos(ResponseData photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
