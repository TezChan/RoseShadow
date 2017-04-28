package com.jaqen.roseshadow.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableLong;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;

import com.jaqen.roseshadow.models.bean.XinWord;
import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.command.ReplyCommand;
import com.kelin.mvvmlight.messenger.Messenger;

import rx.functions.Action0;

/**
 * @author chenp
 * @version 2017-04-27 15:20
 */

public class WordItemViewModel implements ViewModel{
    public final ObservableBoolean isActionShowing = new ObservableBoolean();
    public final ObservableField<String> wordContent = new ObservableField<>();
    public final ObservableField<String> date = new ObservableField();
    public final ObservableBoolean isSetToStart = new ObservableBoolean();

    private XinWord xinWord;
    //private int index;
    private RecyclerView.ViewHolder viewHolder;

    public final ReplyCommand cmdDelete = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            Messenger.getDefault().send(viewHolder.getAdapterPosition(), WordListViewModel.TOKEN_DELETE);
        }
    });
    public final ReplyCommand cmdDetail = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            if (isActionShowing.get())
                isActionShowing.set(false);
            else
                Messenger.getDefault().send(viewHolder.getAdapterPosition(), WordListViewModel.TOLEN_DETAIL);
        }
    });
    public final ReplyCommand cmdShowAction = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            isActionShowing.set(true);
        }
    });
    public final ReplyCommand cmdSetStart = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            //
        }
    });

    public WordItemViewModel(XinWord xinWord){
        setXinWord(xinWord);

    }

    public void setViewHolder(RecyclerView.ViewHolder viewHolder){
        this.viewHolder = viewHolder;
    }

    public XinWord getXinWord() {
        return xinWord;
    }

    public void setXinWord(XinWord xinWord) {
        this.xinWord = xinWord;

        isSetToStart.set(xinWord.isShowingStart());
        wordContent.set(xinWord.getWord());
        date.set(DateFormat.format("yyyy-MM-dd", xinWord.getCreateTime()).toString());
    }
}
