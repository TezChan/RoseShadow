package com.jaqen.roseshadow.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jaqen.roseshadow.R;
import com.jaqen.roseshadow.databinding.FragmentImageBinding;
import com.jaqen.roseshadow.fragments.base.LazyFragment;
import com.jaqen.roseshadow.viewmodels.BeautifulImageViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends LazyFragment {


    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_image);
        FragmentImageBinding binding = FragmentImageBinding.bind(getContentView());

        binding.setBeautifulImage(new BeautifulImageViewModel(getActivity()));
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        getActivity().setTitle("看图");
    }
}
