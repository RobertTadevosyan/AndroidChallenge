package android.androidchallenge.core.models;

import java.util.ArrayList;

/**
 * Created by rob on 9/22/17.
 */

public class ResponseData {
    private String page;
    private String pages;
    private String perpage;
    private String total;
    private ArrayList<Response> photo;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPerpage() {
        return perpage;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Response> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<Response> photo) {
        this.photo = photo;
    }
}
