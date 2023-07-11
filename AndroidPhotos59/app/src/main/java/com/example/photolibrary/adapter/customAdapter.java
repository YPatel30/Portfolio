// Yash Patel(ykp15)
// Om Shah (os207)

package com.example.photolibrary.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.content.Context;
import android.net.Uri;

import com.example.photolibrary.R;

public class customAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> URIs = new ArrayList<String>();
    LayoutInflater inflater;

    public customAdapter(Context contextOfApplication, ArrayList<String> logo) {
        this.context = contextOfApplication;
        this.URIs = logo;
        inflater = (LayoutInflater.from(contextOfApplication));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return URIs.size();
    }

    @Override
    public View getView(int x, View v, ViewGroup groupView) {

        if(v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.row_items, groupView, false);
        }
        ImageView icons = (ImageView) v.findViewById(R.id.image);
        icons.setImageURI(null);
        icons.setImageURI(Uri.parse(URIs.get(x)));

        return icons;
    }
}