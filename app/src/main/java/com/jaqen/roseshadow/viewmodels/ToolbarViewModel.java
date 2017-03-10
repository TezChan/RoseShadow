package com.jaqen.roseshadow.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.ColorDrawable;

import com.kelin.mvvmlight.base.ViewModel;

/**
 * @author chenp
 * @version 2017-02-23 15:15
 */

public class ToolbarViewModel implements ViewModel {

    public ObservableInt bgColor = new ObservableInt();
    public ObservableField<String> tiltle = new ObservableField<>();
    public ObservableBoolean isBackEnabled = new ObservableBoolean();


    public ToolbarViewModel(int bgColor, String titile, boolean isBackEnabled){
        this.bgColor.set(bgColor);
        this.tiltle.set(titile);
        this.isBackEnabled.set(isBackEnabled);
    }
    public ToolbarViewModel(){

    }
}
