package com.example.cbt_ones.moodifyer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.cbt_ones.moodifyer.pojo.Track;

import java.util.ArrayList;


/**
 * Created by cbt_ones on 1/12/2016.
 */
public class SongsManager {

//    public static String[] check_all_songs_storage = {"*"};

    public static String[] check_all_songs_storage =
            {
                    MediaStore.Audio.Media.DISPLAY_NAME,    // Contract class constant for the _ID column name
                    MediaStore.Audio.Media.ALBUM_ID,   // Contract class constant for the word column name
                    MediaStore.Audio.Media.DATA,  // Contract class constant for the locale column name
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.DURATION

            };


    public static ArrayList<Track> track_playlist = new ArrayList<>();
    public Context context_photo;
    private Uri album_art = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    private Cursor cursor_image;

    public SongsManager() {

    }

    public ArrayList<Track> get_songs_from_sdcard(Context context) {
        Cursor cursor;
        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        context_photo = context;
        if (checkSDCard()) {

            cursor = context.getContentResolver().query(allsongsuri, check_all_songs_storage, selection, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String song_title = cursor
                                .getString(cursor
                                        .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

                        if (song_title.endsWith(".mp3")) {
                            int song_id = cursor.getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                            String song_path_data = cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DATA));

                            String song_album = cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM));

                            String song_artist = cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));

                            String song_display_name = cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

                            int song_duration = cursor.getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DURATION));

//                            String path = get_album_art(song_id);

                            track_playlist.add(new Track(song_title, song_album, song_artist, song_path_data, song_display_name, song_id, "", setCorrectDuration(song_duration)));

                        }


                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
        }

        return track_playlist;
    }

    public static boolean checkSDCard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public String get_album_art(int song_id) {
        String path = "";

        if (song_id > -1) {
            cursor_image = context_photo.getContentResolver().query(album_art, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums._ID + " = ?",
                    new String[]{String.valueOf(song_id)},
                    null);

            if (cursor_image.moveToFirst()) {
                path = cursor_image.getString(cursor_image.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                // do whatever you need to do
            }
            cursor_image.close();

        }
        return path;
    }

    public String setCorrectDuration(int duration) {
        String correct_duration = "";

        if (duration != 0) {
            int seconds = duration / 1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            if (seconds < 10) {
                correct_duration = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
            } else {

                correct_duration = String.valueOf(minutes) + ":" + String.valueOf(seconds);
            }
        }


        return correct_duration;
    }
}
