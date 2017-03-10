package com.jaqen.roseshadow.viewmodels.base;

import android.databinding.ObservableField;

import com.jaqen.roseshadow.base.BaseActivity;
import com.jaqen.roseshadow.colors.NipponColor;
import com.jaqen.roseshadow.viewmodels.ToolbarViewModel;
import com.kelin.mvvmlight.base.ViewModel;

/**
 * @author chenp
 * @version 2017-02-23 15:39
 */

public class BaseActivityViewModel implements ViewModel {
    public ObservableField<ToolbarViewModel> toolbarViewModel = new ObservableField<>();

    public void setTitle(String title){
        toolbarViewModel.get().tiltle.set(title);
    }

    public void setToolbarColor(int color){
        toolbarViewModel.get().bgColor.set(color);
    }

    public void isBackEnabled(boolean isBackEnabled){
        toolbarViewModel.get().isBackEnabled.set(isBackEnabled);
    }

    public BaseActivityViewModel(BaseActivity baseActivity){
        ToolbarViewModel viewModel = new ToolbarViewModel(NipponColor.getColor(), "", false);

        toolbarViewModel.set(viewModel);
        //baseActivity.setViewModel(this);
    }

    public BaseActivityViewModel(BaseActivity activity, ToolbarViewModel toolbarViewModel){
        this.toolbarViewModel.set(toolbarViewModel);
    }
}
