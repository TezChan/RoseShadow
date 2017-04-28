package com.jaqen.roseshadow;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jaqen.roseshadow.databinding.ActivityWordDetailBinding;
import com.jaqen.roseshadow.viewmodels.WordDetailViewModel;

public class WordDetailActivity extends AppCompatActivity{
    public static final String EXTRA_EDIT = "edit";
    public static final String EXTRA_XIN_WORD_ID = "id";
    public static final String RESULT_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityWordDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_word_detail);

        boolean isEdit = getIntent().getBooleanExtra(EXTRA_EDIT, false);
        long id = getIntent().getLongExtra(EXTRA_XIN_WORD_ID, -1);
        final WordDetailViewModel viewModel = new WordDetailViewModel();

        viewModel.setIsEditting(isEdit);
        viewModel.setWordId(id);
        viewModel.isSaveCompleted.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Intent data = new Intent();

                data.putExtra(RESULT_DATA, viewModel.getXinWordId());
                setResult(RESULT_OK, data);
            }
        });
        viewModel.isFinished.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                finish();
            }
        });

        binding.setWordDetailViewModel(viewModel);
    }
}
