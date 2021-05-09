package com.sullivan.signear.common.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sullivan.signear.common.ex.viewLifecycle

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected var binding: T by viewLifecycle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    abstract fun setupView()
}