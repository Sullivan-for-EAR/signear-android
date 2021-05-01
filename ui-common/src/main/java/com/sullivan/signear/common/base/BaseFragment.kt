package com.sullivan.signear.common.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sullivan.signear.common.ex.viewLifecycle

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected var binding: T by viewLifecycle()
}