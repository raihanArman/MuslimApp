package com.raydev.anabstract.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import com.raydev.anabstract.util.ViewDataBindingOwner

abstract class BaseActivity<VM : BaseViewModel?, B : ViewDataBinding> :
    FragmentActivity(),
    ViewDataBindingOwner<B> {

    abstract val layoutResourceId: Int
    abstract val viewModel: VM?
    var dialogIsShown = false

    override var binding: B? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutIfDefined()
    }

    private fun setLayoutIfDefined() {
        setContentViewBinding(
            activity = this,
            layoutResId = layoutResourceId
        )
        binding?.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDataBinding()
    }
}
