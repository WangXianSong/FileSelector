package com.songsong.fileselector;

/**
 * Created by SongSong on 2019/2/24
 */
public class FileBean {
    private String filepath;

    public FileBean(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
