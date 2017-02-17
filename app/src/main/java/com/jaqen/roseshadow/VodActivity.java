package com.jaqen.roseshadow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.laifeng.sopcastsdk.camera.CameraListener;
import com.laifeng.sopcastsdk.configuration.AudioConfiguration;
import com.laifeng.sopcastsdk.configuration.CameraConfiguration;
import com.laifeng.sopcastsdk.configuration.VideoConfiguration;
import com.laifeng.sopcastsdk.stream.packer.rtmp.RtmpPacker;
import com.laifeng.sopcastsdk.stream.sender.rtmp.RtmpSender;
import com.laifeng.sopcastsdk.ui.CameraLivingView;
import com.laifeng.sopcastsdk.utils.SopCastLog;

import static com.laifeng.sopcastsdk.configuration.AudioConfiguration.DEFAULT_AAC_PROFILE;
import static com.laifeng.sopcastsdk.configuration.AudioConfiguration.DEFAULT_ADTS;
import static com.laifeng.sopcastsdk.configuration.AudioConfiguration.DEFAULT_AUDIO_ENCODING;

public class VodActivity extends AppCompatActivity {

    private static final String url = "rtmp://192.168.3.104/oflaDemo/stream1";
    private static final int outputVideoWidth = 360;

    private CameraLivingView mLFLiveView;
    private RtmpSender mRtmpSender;
    private CameraConfiguration cameraConfiguration;
    private VideoConfiguration mVideoConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod);

        mLFLiveView = (CameraLivingView) findViewById(R.id.liveView);

        mLFLiveView.setLivingStartListener(new CameraLivingView.LivingStartListener() {
            @Override
            public void startError(int error) {
                Toast.makeText(VodActivity.this, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void startSuccess() {
                Toast.makeText(VodActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
        });

        initLiveView();
        initCamera();
        initVideo();
        initAudio();
        initPacker();
        initSender();
    }

    private void initCamera(){
        CameraConfiguration.Builder cameraBuilder = new CameraConfiguration.Builder();
        cameraBuilder.setOrientation(CameraConfiguration.Orientation.PORTRAIT)
                .setFacing(CameraConfiguration.Facing.BACK)
                .setFocusMode(CameraConfiguration.FocusMode.TOUCH);
        cameraConfiguration = cameraBuilder.build();
        mLFLiveView.setCameraConfiguration(cameraConfiguration);
    }

    private void initVideo(){
        VideoConfiguration.Builder videoBuilder = new VideoConfiguration.Builder();
        videoBuilder.setSize(outputVideoWidth, outputVideoWidth * cameraConfiguration.height / cameraConfiguration.width).setMime(VideoConfiguration.DEFAULT_MIME)
                .setFps(15).setBps(100, 400).setIfi(2);
        mVideoConfiguration = videoBuilder.build();
        mLFLiveView.setVideoConfiguration(mVideoConfiguration);
    }

    private void initAudio(){
        AudioConfiguration.Builder audioBuilder = new AudioConfiguration.Builder();
        audioBuilder.setAec(true).setBps(32, 64).setFrequency(16000).setMime(AudioConfiguration.DEFAULT_MIME).
                setAacProfile(DEFAULT_AAC_PROFILE).setAdts(DEFAULT_ADTS).
                setChannelCount(1).setEncoding(DEFAULT_AUDIO_ENCODING);
        AudioConfiguration audioConfiguration = audioBuilder.build();
        mLFLiveView.setAudioConfiguration(audioConfiguration);
    }

    private void initPacker(){
        RtmpPacker packer = new RtmpPacker();
        packer.initAudioParams(AudioConfiguration.DEFAULT_FREQUENCY, 16, false);
        mLFLiveView.setPacker(packer);
    }

    private void initSender(){

        mRtmpSender = new RtmpSender();

        mRtmpSender.setAddress(url);
        mRtmpSender.setVideoParams(mVideoConfiguration.width, mVideoConfiguration.height);
        mRtmpSender.setAudioParams(AudioConfiguration.DEFAULT_FREQUENCY, 16, false);
        mRtmpSender.setSenderListener(new SenderListener());
        mLFLiveView.setSender(mRtmpSender);
    }

    private void initLiveView(){
        SopCastLog.isOpen(true);
        mLFLiveView.init();
        //设置预览监听
        mLFLiveView.setCameraOpenListener(new CameraListener() {
            @Override
            public void onOpenSuccess() {
                Toast.makeText(VodActivity.this, "摄像头开启成功", Toast.LENGTH_LONG).show();
                mRtmpSender.connect();
            }

            @Override
            public void onOpenFail(int error) {
                Toast.makeText(VodActivity.this, "摄像头开启失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCameraChange() {
                Toast.makeText(VodActivity.this, "摄像头切换", Toast.LENGTH_LONG).show();
            }
        });
    }

    class SenderListener implements RtmpSender.OnSenderListener{

        @Override
        public void onConnecting() {

        }

        @Override
        public void onConnected() {
            Log.d("VodActivity", "connected");
            mLFLiveView.start();
        }

        @Override
        public void onDisConnected() {
            Log.d("VodActivity", "disconnected");
        }

        @Override
        public void onPublishFail() {
            Log.d("VodActivity", "onPublishFail");
        }

        @Override
        public void onNetGood() {

        }

        @Override
        public void onNetBad() {

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mLFLiveView.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLFLiveView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLFLiveView.stop();
        mLFLiveView.release();
    }
}
