package com.jaqen.roseshadow.viewmodels;

import android.databinding.ObservableField;

import com.jaqen.roseshadow.base.BaseActivity;
import com.jaqen.roseshadow.viewmodels.base.BaseActivityViewModel;

/**
 * @author chenp
 * @version 2017-02-23 16:25
 */

public class WebActiviryViewModel extends BaseActivityViewModel {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";

    public ObservableField<String> url = new ObservableField<>();

    public WebActiviryViewModel(String url, String title, BaseActivity activity){
        super(activity);

        setTitle(title);
        isBackEnabled(true);
        this.url.set(url);
    }
}
