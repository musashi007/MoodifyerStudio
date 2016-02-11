package com.example.cbt_ones.moodifyer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cbt_ones.moodifyer.MainActivity;
import com.example.cbt_ones.moodifyer.R;
import com.example.cbt_ones.moodifyer.adapter.ListViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPlaylist extends ListFragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentPlaylist() {

    }

    public ArrayAdapter arrayAdapter;
    public ViewGroup root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_fragment_playlist, container, false);
        ListView listview = (ListView) root.findViewById(android.R.id.list);
        listview.setAdapter(new ListViewAdapter(getActivity(), MainActivity.tracklist));


        return root;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

}
