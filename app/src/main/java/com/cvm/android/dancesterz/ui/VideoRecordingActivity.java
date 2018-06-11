package com.cvm.android.dancesterz.ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cvm.android.dancesterz.MyCameraSurfaceView;
import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VideoRecordingActivity extends AppCompatActivity implements View.OnClickListener {

    private PreferencesManager preferencesManager;
    private Camera myCamera;
    private MyCameraSurfaceView myCameraSurfaceView;
    private MediaRecorder mediaRecorder;
    private ImageView cameraFlipImageView = null;
    private boolean recording = false;
    private int cameraId = 0;
    private ImageButton recordingImageButton = null;
    private ProgressBar progressBar = null;
    private TextView timerTextView = null;
    private ProgressDialog mProgressDialog;
    private MediaPlayer mediaPlayer = null;
    private FrameLayout myCameraRecordingFrameLayout;
    static final String TAG = "VideoRecordingActivity";
    private String audiopath, Challenge_videoPath, usertype;
    private Long ChallengeId, ChallengeVideoId, chaudioId, audioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recordingold);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        cameraFlipImageView = findViewById(R.id.cameraFlipsImageView);
        recordingImageButton = findViewById(R.id.recordingImageButton);
        progressBar = findViewById(R.id.progressBar1);
        myCameraRecordingFrameLayout = findViewById(R.id.videoRecordingFrameLayout);

        mediaPlayer = new MediaPlayer();
        mediaRecorder = new MediaRecorder();

        usertype = getIntent().getExtras().getString(AppConstants.USERTYPE);
        audiopath = getIntent().getExtras().getString(AppConstants.CHALLENGE_AUDIOPATH);
        if (usertype.equals("user1")) {
            Log.i(TAG, "first user");
            Challenge_videoPath = getIntent().getExtras().getString(AppConstants.PLAY_AUDIO);
            audioId = getIntent().getExtras().getLong(AppConstants.AUDIO_ID);
            Log.e(TAG, audiopath + "audio ID" + audioId);
        } else {

            Log.i(TAG, "second user");
            Challenge_videoPath = getIntent().getExtras().getString(AppConstants.PLAY_VIDEO);
            ChallengeId = getIntent().getExtras().getLong(AppConstants.CHALLENGE_ID);
            ChallengeVideoId = getIntent().getExtras().getLong(AppConstants.CHALLENGE_VIDEOID);

            chaudioId = getIntent().getExtras().getLong(AppConstants.CHAUDIO_ID);
            Log.i(TAG, Challenge_videoPath + "ChallengeId " + ChallengeId + "ChallengeVideoId " + ChallengeVideoId + " chaudioId" + chaudioId + " audiopath" + audiopath);
        }
        //download song
        preferencesManager = new PreferencesManager(getApplicationContext());
        mProgressDialog = new ProgressDialog(VideoRecordingActivity.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage("Fetching Song..Please Wait");
        final DownloadTask downloadTask = new DownloadTask(VideoRecordingActivity.this);
        downloadTask.execute(audiopath);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });


        //Get Camera for preview
        myCamera = getCameraInstance();
        if (myCamera == null) {
            Toast.makeText(VideoRecordingActivity.this, "Fail to get Camera", Toast.LENGTH_LONG).show();
        } else {
            myCameraSurfaceView = new MyCameraSurfaceView(this, myCamera);
            myCameraRecordingFrameLayout.addView(myCameraSurfaceView);
            timerTextView = new TextView(VideoRecordingActivity.this);
            timerTextView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timerTextView.setGravity(Gravity.CENTER);
            timerTextView.setTextSize(75);
            timerTextView.setTypeface(null, Typeface.BOLD_ITALIC);
            //timerTextView.setTextColor(getResources().getColor(R.color.timercolor));
            myCameraRecordingFrameLayout.addView(timerTextView);
            timerTextView.setVisibility(View.GONE);
            recordingImageButton.setOnClickListener(this);
            cameraFlipImageView.setOnClickListener(this);
        }
    }

    private Camera getCameraInstance() {
        // TODO Auto-generated method stub
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            c.setDisplayOrientation(90);
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c;        // returns null if camera is unavailable
    }

    //    private  boolean checkAndRequestPermissions() {
//        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int loc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//        int loc2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        List<String> listPermissionsNeeded = new ArrayList<>();
//
//        if (camera != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CAMERA);
//        }
//        if (storage != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//
//        if (loc2 != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
//        }
//
//        if (!listPermissionsNeeded.isEmpty())
//        {
//            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
//                    (new String[listPermissionsNeeded.size()]), URLs.REQUEST_ID_MULTIPLE_PERMISSIONS);
//            return false;
//        }
//        return true;
//    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean prepareMediaRecorder() {
        Log.i(TAG, "In prepareMediaRecorder Method");
        myCamera = getCameraInstance();
        myCamera.unlock();
        mediaRecorder.setCamera(myCamera);
        mediaRecorder.setOrientationHint(90);
//        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
//        mediaRecorder.setVideoFrameRate(27);
//        mediaRecorder.setVideoEncodingBitRate(500000);
        mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getPath() + File.separator + "video.mp4");
        mediaRecorder.setMaxDuration(60000); // Set max duration 60 sec.
        mediaRecorder.setPreviewDisplay(myCameraSurfaceView.getHolder().getSurface());
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            Log.e("Preparing camera", e.getMessage());
            return false;
        } catch (IOException e) {
            releaseMediaRecorder();
            Log.e("Preparing camera", e.getMessage());
            return false;
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void playAudio(String audio) {
        Log.i(TAG, "In Play Audio method");
        Uri myUri = Uri.parse(audio); // initialize Uri here
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
            mediaPlayer.prepare();
            mediaPlayer.start();

            Runnable stopPlayerTask = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(VideoRecordingActivity.this, "Limit reached", Toast.LENGTH_SHORT).show();
                    mediaPlayer.stop();
                    releaseMediaRecorder();
                    finish();
                    if (usertype.equals("user1")) {
                        Intent intent = new Intent(getApplicationContext(), PlaceChallengeActivity.class);
                        intent.putExtra(AppConstants.PLAY_VIDEO, Environment.getExternalStorageDirectory().getPath() + File.separator + "challengevideo.mp4");
                        intent.putExtra(AppConstants.CHALLENGE_ID, ChallengeId);
                        intent.putExtra(AppConstants.CHALLENGE_VIDEOID, ChallengeVideoId);
                        intent.putExtra(AppConstants.CHALLENGE_AUDIOPATH, audiopath);
                        intent.putExtra(AppConstants.CHAUDIO_ID, chaudioId);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                        intent.putExtra(AppConstants.PLAY_VIDEO, Environment.getExternalStorageDirectory().getPath() + File.separator + "challengevideo.mp4");
                        intent.putExtra(AppConstants.CHALLENGE_ID, ChallengeId);
                        startActivity(intent);
                    }
//                     if you are using MediaRecorder, release it first
//                    startActivity(new Intent(getApplicationContext(), PlaceChallengeActivity.class).putExtra(AppConstants.PLAY_VIDEO, Environment.getExternalStorageDirectory().getPath() + File.separator + "myvideo.mp4"));

                }
            };
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.circular);
            progressBar.setProgress(0);   // Main Progress
            progressBar.setMax(60);      // Maximum Progress
            progressBar.setProgressDrawable(drawable);
            final Handler handler = new Handler();
            new CountDownTimer(60000, 1000) {
                float progress = 1;

                public void onTick(long millisUntilFinished) {
                    progress += 1;
                    progressBar.setProgress((int) progress);
                }

                public void onFinish() {
                }
            }.start();
            handler.postDelayed(stopPlayerTask, AppConstants.ONE_MINUTE);
        } catch (IOException e) {
            Log.e("VideoRecordingActivity", e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();// if you are using MediaRecorder, release it first
        releaseCamera();      // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
        Log.i(TAG, "In releaseMediaRecorder Method");
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = null;
            myCamera.lock();         // lock camera for later use


        }
    }

    private void releaseCamera() {
        Log.i(TAG, "In releaseCamera Method");
        if (myCamera != null) {
            myCamera.release();      // release the camera for other applications
            myCamera = null;
        }
    }

    protected void onResume() {
        super.onResume();
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View clickView) {
        if (clickView.getId() == R.id.recordingImageButton) {
            if (recording) {
                // stop recording and release camera
//                mediaPlayer.stop();
//                mediaRecorder.stop();   // stop the recording
//                releaseMediaRecorder(); // release the MediaRecorder object
//                                        //Exit after saved
//                finish();
            } else {
                //Release Camera before MediaRecorder start
//                releaseCamera();

                if (!prepareMediaRecorder()) {
                    Toast.makeText(VideoRecordingActivity.this,
                            "Fail in prepareMediaRecorder()!\n - Ended -",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    new CountDownTimer(5000, 1000) {
                        int timer = 3;

                        @Override
                        public void onFinish() {
                            // Camera is available and unlocked, MediaRecorder is prepared,
                            // now you can start recording
                            timerTextView.setVisibility(View.GONE);
                            mediaRecorder.start();
                            playAudio(Environment.getExternalStorageDirectory().getPath() + File.separator + "mp3download.mp3");
                            // inform the user that recording has started
                            recording = true;
                        }

                        @Override
                        public void onTick(long millisUntilFinished) {
                            timerTextView.setVisibility(View.VISIBLE);
                            if (timer >= 1) {
                                timerTextView.setText(timer + "");
                                timer--;
                            } else if (timer == 0) {
                                timerTextView.setText("GO");
                                timer--;
                            }
                        }

                    }.start();
                }
            }
        }
//        else if (clickView.getId() == cameraFlipImageView.getId()) {
//            if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
//                Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
//            } else {
//                cameraId = findFrontFacingCamera();
//                if (cameraId < 0) {
//                    Toast.makeText(this, "No front facing camera found.",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    myCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
//                }
//            }
//        }
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;

        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int index = 0; index < numberOfCameras; index++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(index, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.i(TAG, "Camera found");
                cameraId = index;
                break;
            }
        }
        return cameraId;
    }


    //downloading class
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            Log.d(TAG, "In Download task");
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();
                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + File.separator + "mp3download.mp3");
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "Post Download Task");
            mWakeLock.release();
            mProgressDialog.dismiss();

            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Click  Record Button to Start Recording", Toast.LENGTH_LONG).show();
            }
        }

    }
//    @Override
//    public void onTaskCompleted() {
//
//    }
}

