package com.jaqen.roseshadow.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaqen.roseshadow.R;
import com.jaqen.roseshadow.databinding.FragmentImageBinding;
import com.jaqen.roseshadow.fragments.base.BaseFragment;
import com.jaqen.roseshadow.fragments.base.LazyFragment;
import com.jaqen.roseshadow.viewmodels.BeautifulImageViewModel;
import com.kelin.mvvmlight.command.ReplyCommand;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeImageTransform;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends BaseFragment{

    private FragmentImageBinding binding;
    private GestureDetectorCompat gestureDetector;
    private boolean expanded = true;

    public final ReplyCommand cmdDoubleClicked = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            expanded = !expanded;

            TransitionManager.beginDelayedTransition(binding.rootView, new TransitionSet()
                    .addTransition(new ChangeBounds())
                    .addTransition(new ChangeImageTransform()));

            ViewGroup.LayoutParams params = binding.image.getLayoutParams();
            params.height = expanded ? ViewGroup.LayoutParams.MATCH_PARENT :
                    ViewGroup.LayoutParams.WRAP_CONTENT;

            binding.image.setLayoutParams(params);
            binding.image.setScaleType( expanded ?
                    ImageView.ScaleType.CENTER_CROP : ImageView.ScaleType.FIT_CENTER);
        }
    });

    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_image);
        binding = FragmentImageBinding.bind(getContentView());

        binding.setBeautifulImage(new BeautifulImageViewModel(getActivity()));
        binding.setCmdImageDoubleClicked(cmdDoubleClicked);
    }

    @Override
    public void onStart() {
        super.onStart();

        //getActivity().setTitle("看图");
    }
}
