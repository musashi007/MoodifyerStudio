package com.example.cbt_ones.moodifyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cbt_ones.moodifyer.R;
import com.example.cbt_ones.moodifyer.pojo.Track;

import java.util.ArrayList;

/**
 * Created by cbt_ones on 1/15/2016.
 */
public class ListViewAdapter extends BaseAdapter {

    public ArrayList<Track> trackarray_list = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public ListViewAdapter(Context context, ArrayList<Track> track_array_list) {
        this.trackarray_list = track_array_list;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return trackarray_list.size();
    }

    @Override
    public Object getItem(int position) {
        return trackarray_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_playlist_item, null);
            myViewHolder = new MyViewHolder();
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        String duration = String.valueOf(trackarray_list.get(position).getSong_duration());
        myViewHolder.song_title = detail(convertView, R.id.song_title, trackarray_list.get(position).getSong_title());
        myViewHolder.song_artist = detail(convertView, R.id.song_artist, trackarray_list.get(position).getSong_artist());
        myViewHolder.song_duration = detail(convertView, R.id.song_duration, duration);


        return convertView;
    }

    private TextView detail(View v, int resId, String text) {
        TextView tv = (TextView) v.findViewById(resId);
        tv.setText(text);
        return tv;
    }

    private class MyViewHolder {
        //        song_title, song_album, song_artist, song_path_data, song_display_name, song_id, song_duration
        TextView song_title, song_album, song_artist, song_path_data, song_display_name, song_id, song_duration;
        ImageView track_photo;

    }
}
