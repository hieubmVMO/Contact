package com.hieubm.contact.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    open fun onBackPressed() {
        (requireActivity() as BaseActivity).back()
    }
}