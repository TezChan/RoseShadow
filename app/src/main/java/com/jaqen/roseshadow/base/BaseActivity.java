package com.jaqen.roseshadow.base;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.jaqen.roseshadow.BR;
import com.jaqen.roseshadow.R;
import com.jaqen.roseshadow.viewmodels.ToolbarViewModel;
import com.jaqen.roseshadow.viewmodels.base.BaseActivityViewModel;

/**
 * @author chenp
 * @version 2017-02-23 14:33
 */

public class BaseActivity extends AppCompatActivity {

    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setViewModel(BaseActivityViewModel viewModel){
        bindToolbar(viewModel.toolbarViewModel.get());
    }

    protected void bindToolbar(ToolbarViewModel toolbarViewModel){
        ViewDataBinding binding = DataBindingUtil.bind(findViewById(R.id.toolbarBg));

        binding.setVariable(BR.toolbarViewModel, toolbarViewModel);
        final ToolbarViewModel finalToolbarViewModel = toolbarViewModel;
        setBackEnabled(finalToolbarViewModel.isBackEnabled.get());
        toolbarViewModel.isBackEnabled.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                setBackEnabled(finalToolbarViewModel.isBackEnabled.get());
            }
        });
    }

    public <T extends ViewDataBinding> T bindContentView(@LayoutRes int layoutResID, BaseActivityViewModel viewModel){
        T t = DataBindingUtil.setContentView(this, layoutResID);

        setViewModel(viewModel);

        return t;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        initToolbar();
    }

    /*@Override
    public void setTitle(CharSequence title) {
        //super.setTitle(title);
        if (tvTitle == null){
            tvTitle = (TextView) findViewById(R.id.tvTitle);
        }

        if (tvTitle != null){
            tvTitle.setText(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        //super.setTitle(titleId);
        setTitle(getText(titleId));
    }*/

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.toolbarBg);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            setTitle("");
        }

        /*if (appBarLayout != null){
            appBarLayout.setBackgroundColor(NipponColor.getColor());
        }*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setBackEnabled(boolean enabled){
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setHomeButtonEnabled(enabled);
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }

}
