package com.jaqen.roseshadow.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.canaanai.drawables.FishDrawable;
import com.jaqen.roseshadow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FishFragment extends Fragment {

    private FishDrawable fishDrawable;

    public FishFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fish, container, false);

        fishDrawable = new FishDrawable();
        ((ImageView)root.findViewById(R.id.imageView)).setImageDrawable(fishDrawable);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        fishDrawable.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        fishDrawable.stop();
    }
}
