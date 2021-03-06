package com.example.cbt_ones.moodifyer;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbt_ones.moodifyer.pojo.Track;
import com.example.cbt_ones.moodifyer.services.StreamSongService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final int CONTENT_VIEW_ID = 10101010;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static ViewPager mViewPager;
    public static PagerAdapter mPagerAdapter;
    private String value;
    private ViewGroup viewGroup;
    public static Button playbtn, pausebtn, nextbtn, previousbtn;
    public static TextView song_artist_textview, song_title_textview, song_start_time, song_end_time;
    public static ArrayList<Track> tracklist;
    public static FloatingActionButton fab;
    public int global_song_position = 0;
    private SongsManager track = new SongsManager();
    public static Context context;
    private static String initial_image_path;
    private static int current_song_position;
    public static SeekBar seekbar;
    private final static Handler handler = new Handler();
    private static int mediaFileLengthInMilliseconds;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
setSupportActionBar(toolbar);
Create the adapter that will return a fragment for each of the three
primary sections of the activity.
mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
Set up the ViewPager with the sections adapter.
*/
        seekbar = (SeekBar) findViewById(R.id.seekbar_button);
        playbtn = (Button) findViewById(R.id.play);
        pausebtn = (Button) findViewById(R.id.pause);
        nextbtn = (Button) findViewById(R.id.next);
        previousbtn = (Button) findViewById(R.id.previous);
        song_artist_textview = (TextView) findViewById(R.id.song_artist_textview);
        song_title_textview = (TextView) findViewById(R.id.song_title_textview);
        song_start_time = (TextView) findViewById(R.id.start_time_textview);
        song_end_time = (TextView) findViewById(R.id.end_time_textview);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tracklist = new ArrayList<>();
        new loadSongsFromLocal().execute();

        CoordinatorLayout tile = (CoordinatorLayout) findViewById(R.id.main_content);
        Bitmap backgroundImage = BitmapFactory.decodeResource(getResources(),
                R.drawable.tactilenoise);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),
                backgroundImage);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        tile.setBackgroundDrawable(bitmapDrawable);

        viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("mood_playlist_tag");
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState == null) {
                    android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                    tx.replace(R.id.content_frame,
                            Fragment.instantiate(MainActivity.this, "com.example.cbt_ones.moodifyer.fragment.FragmentPlaylist"));
                    tx.commit();

                }
            }
        });



        pausebtn.setVisibility(View.GONE);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(final int position) {
                song_artist_textview.setText(tracklist.get(position).getSong_artist());
                song_title_textview.setText(tracklist.get(position).getSong_title());
                song_end_time.setText(String.valueOf(tracklist.get(position).getSong_duration()));
                try {
                    String path = get_album_art(tracklist.get(position).getSong_id());
                    PlaceholderFragment.coverPhoto.setImageBitmap(BitmapFactory.decodeFile(""));
                    PlaceholderFragment.coverPhoto.setImageBitmap(BitmapFactory.decodeFile(path));
                    current_song_position = position;
                    Intent intent = new Intent(getApplicationContext(), StreamSongService.class);
                    intent.setAction(StreamSongService.ACTION_PLAYPAUSE);
                    intent.putExtra("value_song_path", tracklist.get(current_song_position).getSong_path_data());
                    getApplicationContext().startService(intent);
//                    primarySeekBarProgressUpdater();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                playbtn.setVisibility(View.GONE);
//                pausebtn.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), StreamSongService.class);
                intent.setAction(StreamSongService.ACTION_PLAY);
//                intent.putExtra("value_song_path", tracklist.get(current_song_position).getSong_path_data());
                getApplicationContext().startService(intent);
            }
        });

        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                playbtn.setVisibility(View.VISIBLE);
//                pausebtn.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), StreamSongService.class);
                intent.setAction(StreamSongService.ACTION_PAUSE);
                getApplicationContext().startService(intent);
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);

            }
        });
        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);

            }
        });

//        Snackbar.make(viewGroup, "Generating " + value, Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

    }

    public static void primarySeekBarProgressUpdater() {
        seekbar.setProgress((int) (((float) StreamSongService.mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This
        // //"was playing"/"song length"
        if (StreamSongService.mediaPlayer.isPlaying()) {
            showDuration(StreamSongService.mediaPlayer.getCurrentPosition());
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    public static void showDuration(long milliseconds) {
        StringBuffer buf = new StringBuffer();
        int minutes = (int) ((milliseconds % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((milliseconds % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf.append(String.format("%02d", minutes)).append(":")
                .append(String.format("%02d", seconds));
        song_start_time.setText(buf.toString());
    }

    public String get_album_art(int song_id) {
        Uri album_art = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor_image;
        String path = "";

        if (song_id > -1) {
            cursor_image = getApplicationContext().getContentResolver().query(album_art, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums._ID + " = ?",
                    new String[]{String.valueOf(song_id)},
                    null);
            if (cursor_image != null) {
                if (cursor_image.moveToFirst()) {
                    path = cursor_image.getString(cursor_image.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    // do whatever you need to do
                }
                cursor_image.close();
            }
        }
        return path;
    }

    private class loadSongsFromLocal extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String s = postData(params);
            return s;
        }

        private String postData(String[] params) {

            return null;
        }

        protected void onPostExecute(String result) {
            try {
                tracklist = track.get_songs_from_sdcard(getApplicationContext());
                mPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), getSupportFragmentManager(), tracklist);
                mViewPager.setAdapter(mPagerAdapter);

            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            song_artist_textview.setText(tracklist.get(0).getSong_artist());
            song_title_textview.setText(tracklist.get(0).getSong_title());
            song_end_time.setText(String.valueOf(tracklist.get(0).getSong_duration()));
            initial_image_path = get_album_art(tracklist.get(0).getSong_id());
            PlaceholderFragment.coverPhoto.setImageBitmap(BitmapFactory.decodeFile(initial_image_path));
        }

    }


//    @Override
//    public void onBackPressed() {
//        if (mViewPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public ArrayList<Track> track_list;
        public int pos = 0;
        private Context context;
        LayoutInflater inflater;

        public SectionsPagerAdapter(Context c, FragmentManager fm, ArrayList<Track> track_songs) {
            super(fm);
            track_list = track_songs;
            this.context = c;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return track_list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return track_list.get(position).getSong_title();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private Integer arrayListId;
        public static final String ARTIST = "artist";
        public static final String SONGTITLE = "songtitle";
        public static final String END_TIME = "endtime";
        public static ImageView coverPhoto;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            coverPhoto = (ImageView) rootView.findViewById(R.id.cover_photo);
//            coverPhoto.setImageBitmap(BitmapFactory.decodeFile(initial_image_path));

            return rootView;
        }


    }
}
