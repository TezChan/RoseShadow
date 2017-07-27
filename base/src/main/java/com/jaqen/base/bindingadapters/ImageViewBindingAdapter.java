package com.jaqen.base.bindingadapters;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author chenp
 * @version 2017-05-05 12:00
 */

public class ImageViewBindingAdapter {
    @BindingAdapter({"thumbOf"})
    public static void setThumb(ImageView view, String path){
        Glide.with(view.getContext())
                .load(path)
                .into(view);
    }
}
