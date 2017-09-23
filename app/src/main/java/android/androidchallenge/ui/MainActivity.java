package android.androidchallenge.ui;

import android.androidchallenge.R;
import android.androidchallenge.adapters.CustomAdapter;
import android.androidchallenge.base.BaseActivity;
import android.androidchallenge.core.models.Response;
import android.androidchallenge.core.models.ResponseDataResult;
import android.androidchallenge.core.realM.History;
import android.androidchallenge.core.retrofit.RequestManager;
import android.androidchallenge.core.retrofit.ResponseCallback;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
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
    private Button historyBtn;
    private List<String> histories;


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
        historyBtn = (Button) findViewById(R.id.history_btn);
        histories = new ArrayList<>();
        checkingHistories();
        searchBar = (EditText) findViewById(R.id.search_bar);
        gridView = (GridView) findViewById(R.id.images_grid_view);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    searchByQueryActionPerformed(searchBar.getText().toString());
                    History.addHistoryToDB(searchBar.getText().toString());
                }
                return false;
            }
        });
        gridViewConfigs();
    }

    private void checkingHistories() {
        if (History.getAllHistory().isEmpty()) {
            historyBtn.setVisibility(View.GONE);
        } else {
            histories.clear();
            List<History> list = History.getAllHistory();
            for (History history : list) {
                histories.add(history.getText());
            }
            historyBtn.setVisibility(View.VISIBLE);
        }
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
                    if (searchBar.getText().toString().isEmpty() && previosSearchText != null) {
                        searchByQueryActionPerformed(previosSearchText);
                    } else {
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
                if (responseDataResult.getPhotos() != null && !responseDataResult.getPhotos().getPhoto().isEmpty()) {
                    if (previosSearchText != null && previosSearchText.equals(text)) {
                        loadingMore = true;
                        updateGridView(responseDataResult.getPhotos().getPhoto(), false);
                    } else {
                        loadingMore = false;
                        gridView.smoothScrollToPosition(0);
                        updateGridView(responseDataResult.getPhotos().getPhoto(), true);
                    }
                }
                if (previosSearchText != null && !previosSearchText.equals(text)) {
                    loadingMore = true;
                }
                if (!text.isEmpty()) {
                    previosSearchText = text;
                }
                checkingHistories();
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

    public void onHistoryBtnClickActionPerformed(View view) {
        showHistoryAlert();
    }

    private void showHistoryAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(true);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_layout, null);
        dialogBuilder.setView(dialogView);
        ListView historyListView = dialogView.findViewById(R.id.historyListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, histories);
        historyListView.setAdapter(adapter);
        final AlertDialog alertDialog = dialogBuilder.create();
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchBar.setText(histories.get(i));
                searchByQueryActionPerformed(histories.get(i));
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
