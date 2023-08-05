package com.raydev.anabstract.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.raydev.anabstract.extention.toast

abstract class BaseActivity<B : ViewBinding>(
): AppCompatActivity(), BaseView {
    private var _binding: B? = null
    val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
    }

    abstract fun getViewBinding(): B

    override fun onMessage(message: String?) {
        toast(message)
    }

    override fun onMessage(stringResId: Int) {
        onMessage(getString(stringResId))
    }

    /**
     * check internet connection
     */
    override fun isNetworkConnect(): Boolean {
        return true //TODO(make a utilities class for this)
    }

//    fun showCustomNotification(title: Int, text: Int, color: Int, listener: OnHideAlertListener? = null){
//        val alert = Alerter.create(this)
//            .setTitle(title)
//            .setText(text)
//            .setBackgroundColorRes(color)
//        if (listener != null) alert.setOnHideListener(listener)
//        alert.show()
//    }


}