package com.jaqen.base.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jaqen.base.R;
import com.jaqen.base.utils.DeviceUtils;
import com.jaqen.base.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * 录音选择页面
 * （1）在该可以可录音
 * （2）可点击按钮跳转到系统文件管理器选择音频文件
 * <p>
 * 得到结果用以下方法
 * startActivityForResult(new Intent(this,VoiceRecorderActivity.class),101);
 * //选择当前界面录音返回结果
 * if (resultCode == RESULT_OK & requestCode == 101) {
 * String voiceFilePath = data.getStringExtra(VoiceRecorderActivity.KEY_VOICE_FILE);
 * }
 * <p>
 * Created by Administrator on 2016/11/1.
 */

public class VoiceRecorderActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_VOICE_FILE = "voice.recorder.file";
    public static final String KEY_VOICE_NAME = "voice.recorder.name";

    private ImageButton mBtnRecord;
    private ImageButton mBtnPlayPause;
    private ImageButton mBtnOK;
    private ImageButton mBtnFile;
    private TextView mTxtTimeSpan;
    private ProgressBar mProgress;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private Handler mHandler;

    private long mMsBegin;
    private boolean mRecording;
    private boolean mPlaying;
    private boolean mIsUnderGB;
    private String mVoiceFilePath;

    private Intent mResult;
    private final static int PICK_Voice_RequestCode = 101;
    public final static int PICK_Voice_ResultCode = 102;
    private final static int Permissions_MICROPHONE=501;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recorder);

        mBtnRecord = (ImageButton) findViewById(R.id.voice_recorder_btn_rec);
        mBtnPlayPause = (ImageButton) findViewById(R.id.voice_recorder_btn_play);
        mBtnOK = (ImageButton) findViewById(R.id.voice_recorder_btn_ok);
        mBtnFile = (ImageButton) findViewById(R.id.voice_recorder_btn_file);
        mTxtTimeSpan = (TextView) findViewById(R.id.voice_recorder_txt_timespan);
        mProgress = (ProgressBar) findViewById(R.id.voice_recorder_play_progress);
        mBtnRecord.setOnClickListener(this);
        mBtnPlayPause.setOnClickListener(this);
        mBtnOK.setOnClickListener(this);
        mBtnFile.setOnClickListener(this);
        mBtnPlayPause.setEnabled(false);
        mBtnOK.setEnabled(false);
        mTxtTimeSpan.setText("00:00:00");
        mProgress.setVisibility(View.GONE);

        mRecorder = null;
        mPlayer = null;
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        });
        mRecording = false;
        mPlaying = false;
        mIsUnderGB = (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1);
        // this.mIsUnderGB = true;

        StringBuilder sb = new StringBuilder();
        sb.append(getExternalCacheDir().getPath());

        File folder = new File(sb.toString());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        sb.append(File.separator+"meaid_record_").append(DeviceUtils.formatDataCurrentTimeMillis());
//        sb.append(mIsUnderGB ? ".3gp" : ".mp4");
        sb.append(".mp3");
        mVoiceFilePath = sb.toString();

        mResult = new Intent();
        mResult.putExtra(KEY_VOICE_FILE, mVoiceFilePath);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, mResult);
        if (mRecording) {
            if (null != mRecorder) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }

            mBtnRecord.setSelected(false);
            mRecording = false;

            boolean isok = new File(mVoiceFilePath).exists();
            mBtnPlayPause.setEnabled(isok);
            mBtnPlayPause.setImageResource(R.drawable.voice_recorder_icon_play_selector);
            mBtnOK.setEnabled(isok);
            mProgress.setVisibility(isok ? View.VISIBLE : View.GONE);
            mProgress.setProgress(0);
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.voice_recorder_btn_rec) {
            checkSelfPermissionRecord();
        } else if (v.getId() == R.id.voice_recorder_btn_play) {
            onPlay(!mPlaying);
        } else if (v.getId() == R.id.voice_recorder_btn_ok) {
            setResult(RESULT_OK, mResult);
            finish();
        } else if (v.getId() == R.id.voice_recorder_btn_file) {
            //文件
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");
            intent.setType("audio/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), PICK_Voice_RequestCode);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
            }
//            setResult(RESULT_OK, mResult);
//            finish();
        }
    }

    /**
     * 检查麦克风权限
     */
    private void checkSelfPermissionRecord() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                Log.e("main", "没有权限，申请权限。");
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, Permissions_MICROPHONE);
            } else {
                Log.e("main", "有权限了，去放肆吧。");
                onRecord(!mRecording);
            }
        } else {
            onRecord(!mRecording);
        }



    }

    private void onRecord(boolean start) {
        if (start) {
            if (null != mRecorder) {
                mRecorder.release();
                mRecorder = null;
            }

            new File(mVoiceFilePath).delete();
            mRecorder = new MediaRecorder();
            try {
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                if (mIsUnderGB) {
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                } else {
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                }
                mRecorder.setOutputFile(mVoiceFilePath);
                mRecorder.prepare();
                mRecorder.start();
                mMsBegin = System.currentTimeMillis();
                mRecording = true;
                mBtnRecord.setSelected(true);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.removeCallbacks(this);
                        int ts = (int) (System.currentTimeMillis() - mMsBegin) / 1000;
                        int h = ts / 3600;
                        ts = ts % 3600;
                        int m = ts / 60;
                        int s = ts % 60;
                        mTxtTimeSpan.setText(String
                                .format(Locale.getDefault(), "%02d:%02d:%02d", h, m, s));
                        if (mRecording) {
                            mBtnRecord.setSelected(!mBtnRecord.isSelected());
                            mHandler.postDelayed(this, 200);
                        } else {
                            mBtnRecord.setSelected(false);
                        }
                    }
                }, 200);

                mBtnPlayPause.setEnabled(false);
                mBtnOK.setEnabled(false);
            } catch (Exception e) {
                mRecorder.release();
                mRecorder = null;
//                ULogger.e("MediaRecorder prepare() failed", e);
            }
        } else {
            if (null != mRecorder) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }

            mBtnRecord.setSelected(false);
            mRecording = false;

            boolean isok = new File(mVoiceFilePath).exists();
            mBtnPlayPause.setEnabled(isok);
            mBtnPlayPause.setImageResource(R.drawable.voice_recorder_icon_play_selector);
            mBtnOK.setEnabled(isok);
            mProgress.setVisibility(isok ? View.VISIBLE : View.GONE);
            mProgress.setProgress(0);
        }
    }

    private void onPlay(boolean play) {
        if (play) {
            if (null != mPlayer) {
                mPlayer.release();
                mPlayer = null;
            }
            mPlayer = new MediaPlayer();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    onPlay(false);
                    int max = mProgress.getMax();
                    mProgress.setProgress(max);
                }
            });
            try {
                mPlayer.setDataSource(mVoiceFilePath);
                mPlayer.prepare();
                mPlayer.start();
                mPlaying = true;
                mProgress.setMax(mPlayer.getDuration());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.removeCallbacks(this);
                        if (mPlaying && null != mPlayer) {
                            mProgress.setProgress(mPlayer.getCurrentPosition());
                            mHandler.postDelayed(this, 400);
                        }
                    }
                }, 400);
            } catch (IOException e) {
                mPlayer.release();
                mPlayer = null;
//                ULogger.e("MediaPlayer prepare() failed");
            }
        } else {
            if (null != mPlayer) {
                mPlayer.release();
                mPlayer = null;
            }
            mPlaying = false;
        }
        int resid = mPlaying ? R.drawable.voice_recorder_icon_pause_selector
                : R.drawable.voice_recorder_icon_play_selector;
        mBtnPlayPause.setImageResource(resid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_Voice_RequestCode) {
            if (data != null) {
                Uri uri = data.getData();
                String filePath = FileUtils.getPickFilePath(uri, this);
                Intent intent = new Intent();
                intent.putExtra(KEY_VOICE_FILE, filePath);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }


    /**
     * 处理权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Permissions_MICROPHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onRecord(!mRecording);
                } else {
                    Toast.makeText(this, "无法访问麦克风，请重试", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                break;
        }
    }


}
