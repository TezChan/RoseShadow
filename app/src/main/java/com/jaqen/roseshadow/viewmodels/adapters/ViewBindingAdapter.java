package com.jaqen.roseshadow.viewmodels.adapters;

import android.databinding.BindingAdapter;
import android.view.View;

import com.kelin.mvvmlight.command.ReplyCommand;

/**
 * @author chenp
 * @version 2017-03-07 16:45
 */

public class ViewBindingAdapter {
    @BindingAdapter({"longClickCommand"})
    public static void setClickedCommand(View view, final ReplyCommand command){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (command != null){
                    command.execute();

                    return true;
                }

                return false;
            }
        });
    }
}
