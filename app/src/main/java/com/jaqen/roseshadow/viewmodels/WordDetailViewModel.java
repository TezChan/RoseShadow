package com.jaqen.roseshadow.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.jaqen.roseshadow.base.BaseActivity;
import com.jaqen.roseshadow.colors.NipponColor;
import com.jaqen.roseshadow.models.bean.XinWord;
import com.jaqen.roseshadow.viewmodels.base.BaseActivityViewModel;
import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.command.ReplyCommand;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindCallback;
import org.litepal.crud.callback.SaveCallback;

import java.util.Date;

import rx.functions.Action0;

/**
 * @author jm
 * @version 2017-04-26 17:05
 */

public class WordDetailViewModel implements ViewModel {
    public final ObservableInt bgColorTitle = new ObservableInt();
    public final ObservableField<String> word = new ObservableField<>();
    public final ObservableBoolean isSaveCompleted = new ObservableBoolean(false);
    public final ObservableBoolean isEditting = new ObservableBoolean(false);
    public final ObservableBoolean isFinished = new ObservableBoolean();

    private XinWord xinWord;

    public WordDetailViewModel(){
        bgColorTitle.set(NipponColor.getColor());
    }

    public void setWordId(long id){
        if (id < 0) {
            isEditting.set(true);
            return;
        }

        DataSupport.findAsync(XinWord.class, id).listen(new FindCallback() {
            @Override
            public <T> void onFinish(T t) {
                xinWord = (XinWord) t;

                word.set(xinWord.getWord());
            }
        });
    }

    public void setIsEditting(boolean isEditting){
        this.isEditting.set(isEditting);
    }

    public ReplyCommand cmdSave = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            if (xinWord == null){
                xinWord = new XinWord();

                xinWord.setCreateTime(new Date());
            }

            xinWord.setWord(word.get());
            xinWord.setEditTime(new Date());
            xinWord.saveAsync().listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
                    isSaveCompleted.set(success);
                    isEditting.set(false);
                }
            });
        }
    });

    public ReplyCommand cmdCancelEdit = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            if (xinWord != null)
                isEditting.set(false);
            else
                isFinished.set(true);
        }
    });

    public ReplyCommand cmdEdit = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            isEditting.set(true);
        }
    });

    public ReplyCommand cmdClose = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            isFinished.set(true);
        }
    });

    public long getXinWordId(){
        return xinWord.getBaseObjId();
    }
}
