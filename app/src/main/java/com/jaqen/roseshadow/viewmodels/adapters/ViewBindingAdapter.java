package com.jaqen.roseshadow.viewmodels.adapters;

import android.databinding.BindingAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.jaqen.roseshadow.models.bean.FlingData;
import com.kelin.mvvmlight.command.ReplyCommand;

/**
 * @author chenp
 * @version 2017-03-07 16:45
 */

public class ViewBindingAdapter {
    /*@BindingAdapter({"longClickCommand"})
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
    }*/

    @BindingAdapter(value = {"longClickCommand", "doubleClickCommand", "flingCommand", "singleTapCommand"}, requireAll = false)
    public static void setTouchCommand(View view,
                                       final ReplyCommand longClickCommand,
                                       final ReplyCommand doubleClickCommand,
                                       final ReplyCommand<FlingData> flingCommand,
                                       final ReplyCommand singleTapCommand){

        final GestureDetectorCompat gestureDetector = new GestureDetectorCompat(view.getContext(),
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public void onLongPress(MotionEvent e) {
                        if (longClickCommand != null)
                            longClickCommand.execute();
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {

                        if (flingCommand != null){
                            flingCommand.execute(new FlingData(velocityX, velocityY));

                            return true;
                        }

                        return false;
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {

                        if (doubleClickCommand != null){
                            doubleClickCommand.execute();

                            return true;
                        }

                        return false;
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        if (singleTapCommand != null){
                            singleTapCommand.execute();

                            return true;
                        }

                        return false;
                    }

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return gestureDetector.onTouchEvent(event);
            }
        });
    }
}
