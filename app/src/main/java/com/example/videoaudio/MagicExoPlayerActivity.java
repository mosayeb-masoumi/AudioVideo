package com.example.videoaudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.potyvideo.library.AndExoPlayerView;
import com.potyvideo.library.globalEnums.EnumAspectRatio;


import java.io.File;

import me.tankery.lib.circularseekbar.CircularSeekBar;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class

MagicExoPlayerActivity extends AppCompatActivity {

//    https://github.com/HamidrezaAmz/MagicalExoPlayer

    private AndExoPlayerView andExoPlayerView;
    private int req_code = 129;

    String url_mp4 = "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_480_1_5MG.mp4";
    String url_mp3 = "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_1MG.mp3";

    Button btn_mp4, btn_mp3, btn_local, btn_local_audio, btn_download_mp4, btn_download_mp3;


    //https://github.com/tankery/CircularSeekBar
    CircularSeekBar seek_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);


        seek_bar = findViewById(R.id.seek_bar);

        // make sure to initialize downloader
        PRDownloader.initialize(getApplicationContext());
        // give internet permissio too
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 5);

        andExoPlayerView = findViewById(R.id.andExoPlayerView);


        btn_mp4 = findViewById(R.id.btn_mp4);
        btn_mp3 = findViewById(R.id.btn_mp3);
        btn_local = findViewById(R.id.btn_local);
        btn_local_audio = findViewById(R.id.btn_local_audio);
        btn_download_mp4 = findViewById(R.id.btn_download_mp4);
        btn_download_mp3 = findViewById(R.id.btn_download_mp3);

        btn_mp4.setOnClickListener(view -> {
            andExoPlayerView.setSource(url_mp4);
            andExoPlayerView.setAspectRatio(EnumAspectRatio.UNDEFINE);
        });
        btn_mp3.setOnClickListener(view -> {
            andExoPlayerView.setSource(url_mp3);
            andExoPlayerView.setAspectRatio(EnumAspectRatio.ASPECT_MP3);
        });

        btn_local.setOnClickListener(view -> {


//           /storage/emulated/0/VideoAudio/audio/audio.mp3
//           /storage/emulated/0/VideoAudio/video/video.mp4

            String filePath = App.path_save_vid + File.separator + "video.mp4";
            andExoPlayerView.setSource(filePath);
//                selectLocaleVideo();
            andExoPlayerView.setAspectRatio(EnumAspectRatio.UNDEFINE);
        });

        btn_local_audio.setOnClickListener(view -> {
//            String filePath = App.path_save_vid + File.separator + "audio2.mp3";

            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Music" + File.separator + "ESLPod092.mp3";
            andExoPlayerView.setSource(filePath);
            andExoPlayerView.setAspectRatio(EnumAspectRatio.ASPECT_MP3);
        });


        btn_download_mp4.setOnClickListener(view -> {
            saveVideo("mp4");
            andExoPlayerView.setAspectRatio(EnumAspectRatio.UNDEFINE);
        });
        btn_download_mp3.setOnClickListener(view -> {
            saveVideo("mp3");
            andExoPlayerView.setAspectRatio(EnumAspectRatio.ASPECT_MP3);
        });


        // check file is downloaded or not
//        File file = new File(App.path_save_vid+ File.separator+"video1");
//        if (file.isFile()) {
//            Toast.makeText(MainActivity.this, App.path_save_vid+File.separator+"video1" + "/n exists", Toast.LENGTH_SHORT).show();
//
//        } else {
//            // stream video
//            Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
//            new BackgroundAsyncTask().execute(url);
//        }

    }

//    private void loadMP4Locale(String filePath) {
//        andExoPlayerView.setSource(filePath);
//    }


//    private void selectLocaleVideo() {
//        if (PublicFunctions.checkAccessStoragePermission(this)) {
//            Intent intent = new Intent();
//            intent.setType("video/*");
//            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Select Video"), req_code);
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == req_code && resultCode == RESULT_OK) {
//            Uri finalVideoUri = data.getData();
//            String filePath = null;
//            try {
//                filePath = PathUtil.getPath(this, finalVideoUri);
//                loadMP4Locale(filePath);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


    ProgressDialog progressDialog;

    private void saveVideo(String type) {

        String name = "";
        String URL = "";
        String path = "";
        if (type.equals("mp4")) {
            name = "video.mp4";
            URL = url_mp4;
            path = App.path_save_vid;
        } else if (type.equals("mp3")) {
            name = "audio2.mp3";
            URL = url_mp3;
            path = App.path_save_audio;
        }


        PRDownloader.download(URL, path, name)
                .build()
                .setOnStartOrResumeListener(() -> {

//                    Typeface font = Typeface.createFromAsset(getAssets(), FontUtil.getInstance(ExoPlayerActivity.this).getPathRegularFont());
//                    SpannableStringBuilder spannableSB = new SpannableStringBuilder("درحال دانلود");
//                    spannableSB.setSpan (new CustomTypefaceSpan("myfont.ttf", font), 0, spannableSB.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    progressDialog = new ProgressDialog(new ContextThemeWrapper(MagicExoPlayerActivity.this, R.style.CustomFontDialog));
                    progressDialog.setCancelable(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        Drawable drawable = new ProgressBar(MagicExoPlayerActivity.this).getIndeterminateDrawable().mutate();
                        drawable.setColorFilter(ContextCompat.getColor(MagicExoPlayerActivity.this, R.color.colorAccent),
                                PorterDuff.Mode.SRC_IN);
                        progressDialog.setIndeterminateDrawable(drawable);
                    }
                    progressDialog.setTitle("درحال دانلود");
                    progressDialog.show();
                })
                .setOnPauseListener(() -> {
                    progressDialog.dismiss();
                })
                .setOnCancelListener(() -> {
                    progressDialog.dismiss();
                })
                .setOnProgressListener(progress -> {
                    progressDialog.setMax((int) progress.totalBytes / 1000);
                    progressDialog.setProgress((int) progress.currentBytes / 1000);


                    seek_bar.setMax(progress.totalBytes / 1000);
                    seek_bar.setProgress(progress.currentBytes / 1000);

                })

                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(MagicExoPlayerActivity.this, "download finished.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Error error) {
                        String errors = error.toString();
                        progressDialog.dismiss();
                    }
                });




        // show download notif at top and click on it to play music // same as download .apk

        long downloadReference = 0;
        android.app.DownloadManager downloadManager =
                (android.app.DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        android.app.DownloadManager.Request request =
                new android.app.DownloadManager.Request(Uri.parse(URL));
        request.setTitle(getResources().getString(R.string.app_name));
        request.setDescription("دانلود");
        request.setNotificationVisibility(1);
        request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, path);  //original
        downloadReference = downloadManager.enqueue(request);

    }


}