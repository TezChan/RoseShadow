package com.jaqen.roseshadow.viewmodels;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jaqen.roseshadow.BR;
import com.jaqen.roseshadow.R;
import com.jaqen.roseshadow.models.bean.XinWord;
import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.command.ReplyCommand;
import com.kelin.mvvmlight.messenger.Messenger;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindCallback;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author chenp
 * @version 2017-04-27 16:11
 */

public class WordListViewModel implements ViewModel{
    public static final String TOKEN_DELETE = "wordListViewModelDeleteItem";
    public static final String TOLEN_DETAIL = "wordListViewModelItemDetail";

    public final ObservableArrayList<WordItemViewModel> items = new ObservableArrayList<>();
    public final ItemBinding<WordItemViewModel> itemBinding = ItemBinding.of(BR.itemViewModel, R.layout.item_word_list);
    public final WordListAdapter adapter = new WordListAdapter();

    private int editIndex = -1;
    private OnWordDetailStartListener listener;

    public final ReplyCommand cmdAddWord = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            if (listener != null)
                listener.onWordAdd();
        }
    });

    public WordListViewModel(){
        getWordsData();
    }

    public void getWordsData(){
        DataSupport.order("editTime").findAsync(XinWord.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                for (int i = 0; i < t.size(); i ++){
                    items.add(new WordItemViewModel((XinWord) t.get(i)));
                }
            }
        });
    }

    public void getWord(final long id){
        DataSupport.findAsync(XinWord.class, id).listen(new FindCallback() {
            @Override
            public <T> void onFinish(T t) {
                if (editIndex > 0){
                    items.set(editIndex, new WordItemViewModel((XinWord) t));
                    editIndex = -1;
                }else {
                    items.add(new WordItemViewModel((XinWord) t));
                }
            }
        });
    }

    public void delete(int position){

        DataSupport.delete(XinWord.class, items.remove(position)
                .getXinWord().getBaseObjId());
    }

    public void setListener(OnWordDetailStartListener listener){
        this.listener = listener;
    }

    public void onStart(Context context){
        Messenger.getDefault().register(context, TOKEN_DELETE, Integer.class, new Action1<Integer>() {
            @Override
            public void call(Integer pos) {
                delete(pos);
            }
        });
        Messenger.getDefault().register(context, TOLEN_DETAIL, Integer.class, new Action1<Integer>() {
            @Override
            public void call(Integer pos) {
                editIndex = pos;
                if (listener != null)
                    listener.onWordView(items.get(pos).getXinWord().getBaseObjId());
            }
        });
    }

    public void onStop(Context context){
        Messenger.getDefault().unregister(context);
    }

    public interface OnWordDetailStartListener{
        void onWordView(long id);
        void onWordAdd();
    }

    public class WordListAdapter extends BindingRecyclerViewAdapter<WordItemViewModel>{
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);

            getAdapterItem(position).setViewHolder(holder);
        }
    }
}
