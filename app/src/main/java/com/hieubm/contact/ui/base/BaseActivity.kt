package com.hieubm.contact.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivity : AppCompatActivity() {

    open fun back() {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        getNavHostFragment()
            ?.childFragmentManager
            ?.fragments
            ?.find { it.isVisible }
            ?.let {
                (it as? BaseFragment)?.onBackPressed()
                return
            }

        super.onBackPressed()
    }

    protected open fun getNavHostFragment(): NavHostFragment? = null
}