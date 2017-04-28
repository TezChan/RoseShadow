package com.jaqen.roseshadow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.laifeng.sopcastsdk.configuration.CameraConfiguration;
import com.laifeng.sopcastsdk.configuration.VideoConfiguration;
import com.laifeng.sopcastsdk.stream.sender.rtmp.RtmpSender;
import com.laifeng.sopcastsdk.ui.CameraLivingView;
import com.unisky.livecomponect.BaseLivingListener;
import com.unisky.livecomponect.LiveEngine;
import com.unisky.livecomponect.effects.BeautifyEffect;

public class VodActivity extends AppCompatActivity {

    private static final String url = "rtmp://192.168.5.11/live/stream";

    private CameraLivingView mLFLiveView;
    private LiveEngine liveEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod);

        mLFLiveView = (CameraLivingView) findViewById(R.id.liveView);
        liveEngine = new LiveEngine.Builder(mLFLiveView, url)
                .cameraType(CameraConfiguration.Facing.BACK)
                .setEffect(new BeautifyEffect(this))
                .builder();

        liveEngine.setLivingListener(new BaseLivingListener(){
            @Override
            public void onCameraOpenSuccess() {
                super.onCameraOpenSuccess();

                liveEngine.openLiving();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        liveEngine.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        liveEngine.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        liveEngine.destroy();
    }
}
