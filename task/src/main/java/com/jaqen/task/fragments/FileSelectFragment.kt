package com.jaqen.task.fragments


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window

import com.jaqen.task.R
import com.jaqen.task.databinding.FragmentFileSelectBinding


/**
 * A simple [Fragment] subclass.
 */
class FileSelectFragment : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_file_select, container, false)
        val dataBinding = DataBindingUtil.bind<FragmentFileSelectBinding>(rootView)

        dataBinding.btnAudio.setOnClickListener {  }
        dataBinding.btnVideo.setOnClickListener {  }
        dataBinding.btnImg.setOnClickListener {  }
        dataBinding.btnRemove.setOnClickListener {  }
        dataBinding.btnPreview.setOnClickListener {  }
        dataBinding.btnCancel.setOnClickListener {  }

        return rootView
    }

}// Required empty public constructor
