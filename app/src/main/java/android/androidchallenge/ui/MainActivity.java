package android.androidchallenge.ui;

import android.androidchallenge.R;
import android.androidchallenge.adapters.CustomAdapter;
import android.androidchallenge.base.BaseActivity;
import android.androidchallenge.core.models.Response;
import android.androidchallenge.core.models.ResponseDataResult;
import android.androidchallenge.core.retrofit.RequestManager;
import android.androidchallenge.core.retrofit.ResponseCallback;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private EditText searchBar;
    private GridView gridView;
    private CustomAdapter adapter;
    private List<Response> data;
    Boolean loadingMore = true;
    private String previosSearchText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureViews();
        adapterConfigs();
    }

    private void adapterConfigs() {
        data = new ArrayList<>();
        adapter = new CustomAdapter(this, data);
        gridView.setAdapter(adapter);
    }

    private void configureViews() {
        searchBar = (EditText) findViewById(R.id.search_bar);
        gridView = (GridView) findViewById(R.id.images_grid_view);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    searchByQueryActionPerformed(searchBar.getText().toString());
                }
                return false;
            }
        });
        gridViewConfigs();
    }

    private void gridViewConfigs() {
        gridView = (GridView) findViewById(R.id.images_grid_view);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if (lastInScreen == totalItemCount && loadingMore) {
                    if(searchBar.getText().toString().isEmpty() && previosSearchText != null){
                        searchByQueryActionPerformed(previosSearchText);
                    }else {
                        searchByQueryActionPerformed(searchBar.getText().toString());
                    }
                }
            }
        });
    }

    public void searchByQueryActionPerformed(final String text) {
        if (validateSearchField(text)) {
//            Toast.makeText(this, "You need to type some word", Toast.LENGTH_SHORT).show();
            return;
        }
        getRequestLoadingMotion().show();
        new RequestManager().getSerachResults(this, text, new ResponseCallback() {
            @Override
            public void success(Object object) {
                getRequestLoadingMotion().hide();
                ResponseDataResult responseDataResult = (ResponseDataResult) object;
                if (responseDataResult.getPhotos() != null &&  !responseDataResult.getPhotos().getPhoto().isEmpty()) {
                    if (previosSearchText != null && previosSearchText.equals(text)){
                        loadingMore = true;
                        updateGridView(responseDataResult.getPhotos().getPhoto(), false);
                    } else {
                        loadingMore = false;
                        gridView.smoothScrollToPosition(0);
                        updateGridView(responseDataResult.getPhotos().getPhoto(), true);
                    }
                }
                if(previosSearchText != null && !previosSearchText.equals(text)){
                    loadingMore = true;
                }
                if (!text.isEmpty()){
                    previosSearchText = text;
                }

            }

            @Override
            public void fail(String error) {
                loadingMore = false;
                getRequestLoadingMotion().hide();
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateSearchField(String text) {
        return text.isEmpty();
    }

    private void updateGridView(ArrayList<Response> photos, boolean recover) {
        if (recover) {
            data.clear();
        }
        data.addAll(photos);
        adapter.notifyDataSetChanged();
    }
}
