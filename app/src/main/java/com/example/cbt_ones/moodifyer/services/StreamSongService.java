package com.example.cbt_ones.moodifyer.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.cbt_ones.moodifyer.MainActivity;

import java.io.IOException;
import java.security.Provider;
import java.util.List;
import java.util.Map;

/**
 * Created by cbt_ones on 2/12/2016.
 */
public class StreamSongService extends Service implements MediaPlayer.OnCompletionListener {
    public static final String ACTION_PLAY = "com.example.cbt_ones.moodifyer.services.action.PLAY";
    public static final String ACTION_PAUSE = "com.example.cbt_ones.moodifyer.services.action.PAUSE";
    public static final String ACTION_PLAYPAUSE = "com.example.cbt_ones.moodifyer.services.action.PLAYPAUSE";
    public static final String ACTION_PLAYPAUSEBROWSE = "com.cbt_ones.example.moodifyer.services.action.PLAYPAUSEBROWSE";
    public static final String ACTION_MOODLIST = "com.example.cbt_ones.moodifyer.services.action.MOODLIST";
    public static final String ACTION_LOGOUTSTOP = "com.example.cbt_ones.moodifyer.services.action.LOGOUTSTOP";
    public static MediaPlayer mediaPlayer;
    private String path = "";

    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    public int onStartCommand(Intent intent, int flag, int startId) {
        String action = intent.getAction();
        path = intent.getStringExtra("value_song_path");

        if (action.equals(ACTION_PLAY)) {
            Toast.makeText(getApplicationContext(), "Service Started. ACTION: " + action, Toast.LENGTH_SHORT).show();
            processPlayPauseRequest(path);
        } else if (action.equals(ACTION_PAUSE)) {
            processPauseRequest();
        }
//        switch (action) {
//            case ACTION_PLAY:
//
//                processPlayPauseRequest(path);
//                break;
//            case ACTION_PAUSE:
//                processPauseRequest();
//                break;
//        }
        return START_NOT_STICKY;
    }

    private void processPauseRequest() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void processPlayPauseRequest(String path) {

        try {
            Toast.makeText(getApplicationContext(), "Service Started. Data: " + path, Toast.LENGTH_SHORT).show();
            if (mediaPlayer.isPlaying())
                mediaPlayer.reset();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}


