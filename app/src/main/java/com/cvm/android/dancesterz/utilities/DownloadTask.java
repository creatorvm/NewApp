package com.cvm.android.dancesterz.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Devalopment-1 on 15-01-2018.
 */

public class DownloadTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private PowerManager.WakeLock mWakeLock;
    Handler handler = new Handler();
    int status = 0;
    ProgressDialog progressdialog;

    public DownloadTask(Context context) {
        this.context = context;
        CreateProgressDialog();
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            Log.e("Song", sUrl[0]);
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
//            output = new FileOutputStream("/sdcard/download.m4a");
            output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "download.m4a");

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
//        // take CPU lock to prevent CPU from going off if the user
//        // presses the power button during download
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                getClass().getName());
//        mWakeLock.acquire();
//        ShowProgressDialog();
////        VideoRecordingActivity.mProgressDialog.show();
    }

    public void ShowProgressDialog(Integer progress) {
        status = progress;
        Log.e("Progress Dialoge", status + "");


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < 100) {

//                    status += 1;

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressdialog.setProgress(status);
                            if (status == 100) {
                                progressdialog.dismiss();
                            }
                        }
                    });
                }
            }
        }).start();

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
//        VideoRecordingActivity.mProgressDialog.setIndeterminate(false);
//        VideoRecordingActivity.mProgressDialog.setMax(100);
//        VideoRecordingActivity.mProgressDialog.setProgress(progress[0]);
        ShowProgressDialog(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
//        mWakeLock.release();
//        VideoRecordingActivity.mProgressDialog.dismiss();
        if (progressdialog != null) {
            progressdialog.dismiss();
        }
        if (result != null) {
            Toast.makeText(context, "Song is not valid", Toast.LENGTH_LONG).show();
            ((Activity)context).finish();
        } else {
            Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    public void CreateProgressDialog() {

        progressdialog = new ProgressDialog(context);

        progressdialog.setIndeterminate(false);

        progressdialog.setMessage("Downloading...");

        progressdialog.setCanceledOnTouchOutside(false);

        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressdialog.setMax(100);

        progressdialog.show();

    }
}
