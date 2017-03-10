package com.jaqen.roseshadow.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;

import com.canaanai.net.EasyNet;
import com.canaanai.net.NetAction;
import com.canaanai.net.SimpleIndeterminateProgress;
import com.jaqen.roseshadow.WebActivity;
import com.jaqen.roseshadow.models.bean.GankContent;
import com.jaqen.roseshadow.models.bean.GankData;
import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.command.ReplyCommand;

import rx.functions.Action0;

/**
 * @author chenp
 * @version 2017-02-21 16:40
 */

public class BeautifulImageViewModel implements ViewModel{
    private GankContent gankContent;
    private EasyNet easyNet;
    private Activity activity;

    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ReplyCommand imageLongClicked = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            Intent intent = new Intent(activity, WebActivity.class);

            intent.putExtra(WebActiviryViewModel.EXTRA_TITLE, "干客集中营");
            intent.putExtra(WebActiviryViewModel.EXTRA_URL, "http://gank.io/");
            activity.startActivity(intent);
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
                .doGet(String.valueOf(1), GankData.class)
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
}
