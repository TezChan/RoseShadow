package com.jaqen.roseshadow.colors;

import android.graphics.Color;

import com.canaanai.net.EasyNet;
import com.canaanai.net.EasyNetHelper;
import com.canaanai.net.NetAction;
import com.jaqen.roseshadow.models.bean.ColorRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.functions.Function1;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author chenp
 * @version 2017-02-20 11:27
 */

public class NipponColor {
    private static int initColor = -1;
    private static List<String[]> colorMap;
    private static EasyNet net;

    public static void init(final NipponInitListener listener){
        net = new EasyNet.Builder().url("http://nipponcolors.com/").builder();

        Observable<ColorRequest> observable = Observable.create(new Observable.OnSubscribe<List<String[]>>() {
            @Override
            public void call(Subscriber<? super List<String[]>> subscriber) {
                subscriber.onNext(getColorArray());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .filter(new Func1<List<String[]>, Boolean>() {
            @Override
            public Boolean call(List<String[]> strings) {
                return strings != null;
            }
        }).map(new Func1<List<String[]>, String[]>() {
                    @Override
                    public String[] call(List<String[]> strings) {
                        return strings.get((int)(Math.random() * strings.size()));
                    }
         }).map(new Func1<String[], ColorRequest>() {
                    @Override
                    public ColorRequest call(String[] strings) {
                        return new ColorRequest(strings[1]);
                    }
         });

        EasyNetHelper.easyNet(observable, net, "color")
                .workOnSubThread()
                .doPost("php/io.php", String.class)
                .map(new Function1<String, String>() {
                    @Override
                    public String invoke(String s) {
                        try {
                            return "#" + new JSONObject(s).getString("rgb");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }).workOnMainThread()
                .doNext(new NetAction<String>() {
                    @Override
                    public void call(String s) {
                        initColor = Color.parseColor(s);
                        if (listener != null)
                            listener.onInitFinished(initColor);
                    }
                })
                .start();
    }

    private static List<String[]> getColorArray(){
        try {
            Document document = Jsoup.parse(new URL("http://nipponcolors.com/"), 5000);

            Elements elems = document.select("#colors a");

            colorMap = new ArrayList<>();
            for (int i = 0; i < elems.size(); i++){
                colorMap.add(elems.get(i).text().split(", "));
            }

            return colorMap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getColor(){
        return initColor;
    }

    public interface NipponInitListener{
        void onInitFinished(int initColor);
    }
}
