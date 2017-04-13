package com.jaqen.task.viewmodels.adapters;

import android.databinding.BindingAdapter;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaqen.task.R;
import com.jaqen.task.bean.MediaInfo;

/**
 * @author chenp
 * @version 2017-04-10 16:02
 */

public class ViewBindAdapter {
    @BindingAdapter({"mediaThumb"})
    public static void setThumb(ImageView view, MediaInfo media){
        if (media != null){
            if (media.getType() == MediaInfo.MediaType.AUDIO){
                view.setImageDrawable(
                        VectorDrawableCompat.create(view.getResources(), R.drawable.ic_audio_thumb, null));
            }else{
                Glide.with(view.getContext())
                        .load(media.getPath())
                        .centerCrop()
                        .into(view);
            }
        }else {
            view.setImageResource(R.mipmap.add_big);
        }
    }
}
