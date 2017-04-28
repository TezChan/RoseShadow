package com.jaqen.roseshadow.fragments;


import android.app.Activity;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaqen.roseshadow.R;
import com.jaqen.roseshadow.WordDetailActivity;
import com.jaqen.roseshadow.databinding.FragmentWordListBinding;
import com.jaqen.roseshadow.databinding.ItemWordListBinding;
import com.jaqen.roseshadow.fragments.base.BaseFragment;
import com.jaqen.roseshadow.models.bean.XinWord;
import com.jaqen.roseshadow.viewmodels.WordItemViewModel;
import com.jaqen.roseshadow.viewmodels.WordListViewModel;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordListFragment extends BaseFragment {
    private static final int REQUEST_EDIT = 1000;

    private WordListViewModel viewModel;

    public WordListFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);

        setContentView(R.layout.fragment_word_list);
        FragmentWordListBinding binding = FragmentWordListBinding.bind(getContentView());
        viewModel = new WordListViewModel();

        binding.rvWordList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.setViewModel(viewModel);
        viewModel.setListener(new WordListViewModel.OnWordDetailStartListener() {
            @Override
            public void onWordView(long id) {
                Intent intent = new Intent(getActivity(), WordDetailActivity.class);

                intent.putExtra(WordDetailActivity.EXTRA_XIN_WORD_ID, id);
                startActivityForResult(intent, REQUEST_EDIT);
            }

            @Override
            public void onWordAdd() {
                Intent intent = new Intent(getActivity(), WordDetailActivity.class);
                startActivityForResult(intent, REQUEST_EDIT);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        viewModel.onStart(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();

        viewModel.onStop(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_EDIT:
                    viewModel.getWord(data.getLongExtra(WordDetailActivity.RESULT_DATA, 0));
                    break;
            }
        }
    }
}
