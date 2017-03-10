package com.jaqen.roseshadow;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.jaqen.roseshadow.base.BaseActivity;
import com.jaqen.roseshadow.databinding.ActivityWebBinding;
import com.jaqen.roseshadow.viewmodels.WebActiviryViewModel;

public class WebActivity extends BaseActivity {

    private ActivityWebBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(WebActiviryViewModel.EXTRA_URL);
        String title = getIntent().getStringExtra(WebActiviryViewModel.EXTRA_TITLE);
        final WebActiviryViewModel viewModel = new WebActiviryViewModel(url, title, this);
        binding = bindContentView(R.layout.activity_web, viewModel);

        initWebView();

        binding.webView.loadUrl(viewModel.url.get());
        viewModel.url.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                binding.webView.loadUrl(viewModel.url.get());
            }
        });

        binding.setWebActivityViewModel(viewModel);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (binding.webView.canGoBack()){
            binding.webView.goBack();
        }else {
            finish();
        }
    }

    private void initWebView(){
        WebSettings webSettings = binding.webView.getSettings();

        if (webSettings != null){
            webSettings.setJavaScriptEnabled(true);
        }

        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.setWebChromeClient(new WebChromeClient());

    }
}
