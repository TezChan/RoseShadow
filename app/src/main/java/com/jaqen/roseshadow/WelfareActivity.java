package com.jaqen.roseshadow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.canaanai.net.EasyNet;
import com.canaanai.net.INetInterceptor;
import com.canaanai.net.NetAction;
import com.canaanai.net.SimpleIndeterminateProgress;
import com.jaqen.roseshadow.bean.GankData;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class WelfareActivity extends AppCompatActivity {

    private ImageView imgWelfare;
    private EasyNet easyNet;

    private int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare);

        imgWelfare = (ImageView) findViewById(R.id.imgWelfare);

        imgWelfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWelfare(++pageIndex);
            }
        });

        easyNet = new EasyNet.Builder().url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/1/")
                .progress(new SimpleIndeterminateProgress(this))
                .netInterceptor(new INetInterceptor() {
                    @Override
                    public void onRequest(@NotNull String tag, Object data) {
                        Log.d("WelfareActivity", tag + ":" + data);
                    }

                    @Override
                    public void onResponse(@NotNull String tag, Object data) {
                        Log.d("WelfareActivity", tag + ":" + ((GankData)data).getResults().get(0).getUrl());
                    }
                })
                .builder();

        showWelfare(1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void showWelfare(int index){
        easyNet.request("welfare", null)
                .workOnMainThread()
                .showProgress()
                .workOnSubThread()
                .doGet(String.valueOf(index), GankData.class)
                .workOnMainThread()
                .doNext(new NetAction<GankData>() {
                    @Override
                    public void call(GankData gankData) {
                        Picasso.with(WelfareActivity.this)
                                .load(gankData.getResults().get(0).getUrl() + "?imageView2/0/w/" + imgWelfare.getWidth())
                                .into(imgWelfare);
                    }
                })
                .hideProgress()
                .start();
    }
}
