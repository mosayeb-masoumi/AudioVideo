package com.example.videoaudio;

import android.app.Application;
import android.os.Environment;

import java.io.File;

public class App extends Application {

    public static String path_save_vid="";
    public static String path_save_audio="";

    @Override
    public void onCreate() {
        super.onCreate();

        path_save_vid=  Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + this.getResources().getString(R.string.app_name)+ File.separator+"video";
        path_save_audio=  Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + this.getResources().getString(R.string.app_name)+ File.separator+"audio";

        final File newVideoPath = new File(path_save_vid);
        newVideoPath.mkdir();
        newVideoPath.mkdirs();

        final File newAudioPath = new File(path_save_audio);
        newAudioPath.mkdir();
        newAudioPath.mkdirs();
    }
}
