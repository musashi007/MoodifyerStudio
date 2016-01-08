package com.example.cbt_ones.moodifyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.cbt_ones.moodifyer.adapter.MoodGridViewAdapter;
import com.example.cbt_ones.moodifyer.adapter.ViewPagerAdapter;
import com.example.cbt_ones.moodifyer.view.ExpandableGridView;

public class ScrollingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static ExpandableGridView mood_grid_view;
    public static MoodGridViewAdapter adapter;
    private FloatingActionButton fab;
    public static ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        pager = (ViewPager)findViewById(R.id.viewPager);
//        pager.setAdapter(new ViewPagerAdapter(this));
        mood_grid_view = (ExpandableGridView) findViewById(R.id.gridview);
        adapter = new MoodGridViewAdapter(getApplicationContext());
        mood_grid_view.setAdapter(adapter);
        mood_grid_view.setExpanded(true);
        mood_grid_view.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0: {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("mood_playlist_tag","Angry Playlist");
                startActivity(intent);
                break;
            }
            case 1: {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("mood_playlist_tag","Sad Playlist");
                startActivity(intent);
                break;
            }
            case 2: {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("mood_playlist_tag","Happy Playlist");
                startActivity(intent);
                break;
            }
            case 3: {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("mood_playlist_tag","Relaxed Playlist");
                startActivity(intent);
                break;
            }
            case 4: {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("mood_playlist_tag","Energetic Playlist");
                startActivity(intent);
                break;
            }
            case 5: {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("mood_playlist_tag","Mood Elevate Playlist");
                startActivity(intent);
                break;
            }
        }
    }
}
