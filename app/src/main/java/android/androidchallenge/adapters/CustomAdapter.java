package android.androidchallenge.adapters;

import android.androidchallenge.R;
import android.androidchallenge.base.MyApplication;
import android.androidchallenge.core.models.Response;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends BaseAdapter {


    public final int TYPE_IMAGE = 0;
    public final int TYPE_LOAD = 1;

    private Context context;
    private List list;
    private static LayoutInflater inflater = null;
    private int imageSize = (int) MyApplication.getMyApplicationContext().getResources().getDimension(R.dimen.image_size);
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    public CustomAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(imageSize, imageSize));
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setBackgroundResource(R.drawable.default_img);
        Response response = (Response) list.get(position);
        Picasso.with(context)
                .load("https://farm" + response.getFarm() + ".static.flickr.com/" + response.getServer() + "/" + response.getId() + "_" +
                        response.getSecret() + ".jpg")
                .fit()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imageView);
        return imageView;
    }

    interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
} 