package com.jaqen.task.fragments


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.bumptech.glide.Glide
import com.jaqen.base.ui.VoiceRecorderActivity

import com.jaqen.task.R
import com.jaqen.task.bean.MediaInfo
import com.jaqen.task.databinding.FragmentFileSelectBinding
import com.unisky.videoselector.VideoConfig
import com.unisky.videoselector.VideoSelector
import com.unisky.videoselector.VideoSelectorActivity
import com.yancy.imageselector.ImageConfig
import com.yancy.imageselector.ImageSelector
import com.yancy.imageselector.ImageSelectorActivity
import com.yanzhenjie.permission.AndPermission


/**
 * A simple [Fragment] subclass.
 */
class MediaSelectFragment : DialogFragment() {
    companion object{
        /**
         * 选择图片
         */
        val REQUEST_CODE_IMAGE = 10001
        /**
         * 选择视频
         */
        val REQUEST_CODE_VIDEO = 10002
        /**
         * 选择音频
         */
        val REQUEST_CODE_AUDIO = 10003
    }

    val dataBinding: FragmentFileSelectBinding? = null

    var mMedia: MediaInfo? = null
    var listener: OnMediaSelectListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_file_select, container, false)
        val dataBinding = DataBindingUtil.bind<FragmentFileSelectBinding>(rootView)

        dataBinding.btnAudio.setOnClickListener {
            startActivityForResult(Intent(context, VoiceRecorderActivity::class.java), REQUEST_CODE_AUDIO)
        }
        dataBinding.btnVideo.setOnClickListener {
            val vSelectorConfig = VideoConfig.Builder{ mContext, path, imgView ->
                Glide.with(mContext)
                        .load(path)
                        .placeholder(com.yancy.imageselector.R.mipmap.imageselector_photo)
                        .centerCrop()
                        .into(imgView) }
                    .titleBgColor(resources.getColor(R.color.colorPrimary))
                    .titleSubmitTextColor(Color.WHITE)
                    .titleTextColor(Color.WHITE)
                    // 开启单选   （默认为多选）
                    .singleSelect()
                    // 开启拍照功能 （默认关闭）
                    .showCamera()
                    // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                    .filePath("/VideoSelector/Pictures")
                    .requestCode(REQUEST_CODE_VIDEO)
                    .build()
            VideoSelector.open(this, vSelectorConfig)
        }
        dataBinding.btnImg.setOnClickListener {
            val imgConfig = ImageConfig.Builder{ mContext, path, imgView ->
                Glide.with(mContext)
                        .load(path)
                        .placeholder(com.yancy.imageselector.R.mipmap.imageselector_photo)
                        .centerCrop()
                        .into(imgView) }
                    .titleBgColor(resources.getColor(R.color.colorPrimary))
                    .titleSubmitTextColor(Color.WHITE)
                    .titleTextColor(Color.WHITE)
                    // 开启单选   （默认为多选）
                    .singleSelect()
                    // 开启拍照功能 （默认关闭）
                    .showCamera()
                    // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                    .filePath("/ImageSelector/Pictures")
                    .requestCode(REQUEST_CODE_IMAGE)
                    .build()

            ImageSelector.open(this, imgConfig)   // 开启图片选择器
        }
        dataBinding.btnRemove.setOnClickListener {
            mMedia?.let {
                listener?.onMediaDelete(it)
            }
            dismiss()
        }
        dataBinding.btnPreview.setOnClickListener {
            mMedia?.let {
                listener?.onPreview(it)
            }
            dismiss()
        }
        dataBinding.btnCancel.setOnClickListener {
            listener?.onCancel()
            dismiss()
        }

        dataBinding?.let {
            val visibility = if (mMedia != null) View.VISIBLE else View.GONE

            it.btnRemove?.visibility = visibility
            it.btnPreview?.visibility = visibility
        }

        return rootView
    }

    override fun onStart() {
        super.onStart()

        val window = dialog.window

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){

            var media: MediaInfo? = null

            when (requestCode){
                REQUEST_CODE_IMAGE -> {
                    val paths = data?.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT)

                    paths?.let {
                        media = MediaInfo(MediaInfo.MediaType.IMAGE, paths[0], "")
                    }
                }
                REQUEST_CODE_AUDIO -> {
                    media = data?.getStringExtra(VoiceRecorderActivity.KEY_VOICE_FILE)?.let {
                        MediaInfo(MediaInfo.MediaType.AUDIO, it, "")
                    }
                }
                REQUEST_CODE_VIDEO -> {
                    val paths = data?.getStringArrayListExtra(VideoSelectorActivity.EXTRA_RESULT)

                    paths?.let {
                        media = MediaInfo(MediaInfo.MediaType.VIDEO, paths[0], "")
                    }
                }
                else ->{

                }
            }

            media?.let {
                listener?.onMediaSelected(it)
                dismiss()
            }
        }
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        super.show(manager, tag)

        this.mMedia = null
    }

    fun show(manager: FragmentManager?, tag: String?, media: MediaInfo?) {
        super.show(manager, tag)

        this.mMedia = media
    }

    /**
     * 媒体文件选择的监听
     */
    interface OnMediaSelectListener{
        fun onMediaSelected(media: MediaInfo)
        fun onMediaDelete(media: MediaInfo)
        fun onPreview(media: MediaInfo)
        fun onCancel()
    }
}// Required empty public constructor
