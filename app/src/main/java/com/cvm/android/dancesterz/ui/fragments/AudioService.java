package com.cvm.android.dancesterz.ui.fragments;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by prasadprabhakaran on 8/14/17.
 *
 * Usage:
 * Intent serviceIntent = new Intent(this,AudioService.class);
 * serviceIntent.putExtra("AudioUrl", Uri);
 * startService(serviceIntent);
 */

public class AudioService extends Service {

    private Uri audioUri;
    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        audioUri = (Uri)extras.get("AudioUrl");
//        player = MediaPlayer.create(this, audioUri);
//        player.setLooping(false);
//        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    public void play(Context ctx, Uri file){
        player = MediaPlayer.create(ctx,file);
        player.setLooping(false);
        player.start();

    }

    public void stop(){
        player.stop();
    }
}
