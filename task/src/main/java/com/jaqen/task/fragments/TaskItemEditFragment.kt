package com.jaqen.task.fragments


import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jaqen.task.R
import com.jaqen.task.bean.MediaInfo
import com.jaqen.task.bean.TaskItem
import com.jaqen.task.databinding.FragmentTaskItemEditBinding
import com.jaqen.task.viewmodels.TaskItemEditViewModel

/**
 * A simple [Fragment] subclass.
 */
class TaskItemEditFragment : Fragment(){

    val viewModel: TaskItemEditViewModel = TaskItemEditViewModel()
    private var listener: TaskItemEditListener? = null

    companion object{
        @JvmStatic
        val TAG = "TaskItemEditFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_task_item_edit, container, false)
        val dataBinding = DataBindingUtil.bind<FragmentTaskItemEditBinding>(rootView)

        viewModel.isComplete.addOnPropertyChangedCallback(object :Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                Log.i(TAG, "Complete")
                if (viewModel.isComplete.get()){
                    listener?.onEditComplete(viewModel.taskItem)
                }
            }
        })

        dataBinding.taskItem = viewModel
        dataBinding.btnCancel.setOnClickListener {
            listener?.onEditCancel()
        }
        dataBinding.imgMedia.setOnClickListener {
            FileSelectFragment().show(childFragmentManager, "fileSelector")
        }

        return rootView
    }

    fun setTaskItemEditListener(listener: TaskItemEditListener){
        this.listener = listener
    }

    fun setTaskItem(taskItem: TaskItem){
        viewModel.taskItem = taskItem
    }

    fun getTaskItem(): TaskItem?{
        return viewModel.taskItem
    }

    interface TaskItemEditListener{
        fun onEditComplete(taskItem: TaskItem?)
        fun onEditCancel()
    }
}// Required empty public constructor
