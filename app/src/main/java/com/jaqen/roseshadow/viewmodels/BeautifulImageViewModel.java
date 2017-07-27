package com.jaqen.roseshadow.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;

import com.canaanai.net.EasyNet;
import com.canaanai.net.NetAction;
import com.canaanai.net.SimpleIndeterminateProgress;
import com.jaqen.roseshadow.WebActivity;
import com.jaqen.base.bindingadapters.bean.FlingData;
import com.jaqen.roseshadow.models.bean.GankContent;
import com.jaqen.roseshadow.models.bean.GankData;
import com.jaqen.roseshadow.models.bean.GankHistory;
import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.command.ReplyCommand;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author chenp
 * @version 2017-02-21 16:40
 */

public class BeautifulImageViewModel implements ViewModel{
    private GankContent gankContent;
    private EasyNet easyNet;
    private Activity activity;
    private int index = 1;


    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ReplyCommand imageLongClicked = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            getWebUrl();
        }
    });

    public ReplyCommand<FlingData> cmdImgFling = new ReplyCommand<>(new Action1<FlingData>() {
        @Override
        public void call(FlingData flingData) {
            if (flingData.getVelocityX() < 0){
                index ++;

                requestData();
            }else if (flingData.getVelocityX() > 0 && index > 1) {
                index --;

                requestData();
            }
        }
    });

    public BeautifulImageViewModel(Activity activity){
        this.activity = activity;

        easyNet = new EasyNet.Builder()
                .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/1/")
                .progress(new SimpleIndeterminateProgress(activity)).builder();

        requestData();
    }

    public void requestData(){
        easyNet.request("welfare", null)
                .workOnMainThread()
                .showProgress()
                .workOnSubThread()
                .doGet(String.valueOf(index), GankData.class)
                .doNext(new NetAction<GankData>() {
                    @Override
                    public void call(GankData gankData) {
                        gankContent = gankData.getResults().get(0);
                        imageUrl.set(gankContent.getUrl());
                    }
                })
                .workOnMainThread()
                .hideProgress()
                .start();
    }

    public void getWebUrl(){
        easyNet.request("webUrl", null)
                .workOnSubThread()
                .doGet("http://gank.io/api/day/history", GankHistory.class)
                .workOnMainThread()
                .doNext(new NetAction<GankHistory>() {
                    @Override
                    public void call(GankHistory gankHistory) {
                        if (!gankHistory.isError()){
                            Intent intent = new Intent(activity, WebActivity.class);

                            intent.putExtra(WebActiviryViewModel.EXTRA_TITLE, "干客集中营");
                            intent.putExtra(WebActiviryViewModel.EXTRA_URL, "http://gank.io/"
                                    + gankHistory.getResults().get(index - 1).replace("-", "/"));
                            activity.startActivity(intent);
                        }
                    }
                })
                .start();
    }
}
