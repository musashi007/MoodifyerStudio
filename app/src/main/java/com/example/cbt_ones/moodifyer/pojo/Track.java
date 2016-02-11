package com.example.cbt_ones.moodifyer.pojo;

/**
 * Created by cbt_ones on 1/13/2016.
 */
public class Track {
    String song_title;
    String song_album;
    String song_artist;
    String song_path_data;
    String song_display_name;
    String song_duration;
    String cover_photo_path;
    int song_id;

    public Track(String song_title, String song_album, String song_artist, String song_path_data, String song_display_name, int song_id, String cover_photo_path, String song_duration) {
        this.song_title = song_title;
        this.song_album = song_album;
        this.song_artist = song_artist;
        this.song_path_data = song_path_data;
        this.song_display_name = song_display_name;
        this.song_id = song_id;
        this.cover_photo_path = cover_photo_path;
        this.song_duration = song_duration;
    }

    public String getCover_photo_path() {
        return cover_photo_path;
    }

    public void setCover_photo_path(String cover_photo_path) {
        this.cover_photo_path = cover_photo_path;
    }

    public String getSong_title() {
        return song_title;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public String getSong_album() {
        return song_album;
    }

    public void setSong_album(String song_album) {
        this.song_album = song_album;
    }

    public String getSong_artist() {
        return song_artist;
    }

    public void setSong_artist(String song_artist) {
        this.song_artist = song_artist;
    }

    public String getSong_path_data() {
        return song_path_data;
    }

    public void setSong_path_data(String song_path_data) {
        this.song_path_data = song_path_data;
    }

    public String getSong_display_name() {
        return song_display_name;
    }

    public void setSong_display_name(String song_display_name) {
        this.song_display_name = song_display_name;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public String getSong_duration() {
        return song_duration;
    }

    public void setSong_duration(String song_duration) {
        this.song_duration = song_duration;
    }
}
