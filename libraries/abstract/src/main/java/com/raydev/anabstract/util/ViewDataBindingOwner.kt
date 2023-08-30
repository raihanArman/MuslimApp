package com.raydev.anabstract.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
interface ViewDataBindingOwner<T : ViewDataBinding> {
    var binding: T?

    val bindingNotNull: T
        get() = binding!!

    fun clearDataBinding() {
        binding?.lifecycleOwner = null
        binding = null
    }

    fun setViewBinding(view: View) {
        binding = DataBindingUtil.bind(view)!!
    }

    fun setContentViewBinding(activity: Activity, layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
    }

    fun inflateContentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        layoutResId: Int
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding!!.root
    }
}
