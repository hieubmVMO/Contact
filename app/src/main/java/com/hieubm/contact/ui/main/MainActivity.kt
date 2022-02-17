package com.hieubm.contact.ui.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.hieubm.contact.R
import com.hieubm.contact.databinding.ActivityMainBinding
import com.hieubm.contact.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setLayoutFullscreen()
    }

    @Suppress("deprecation")
    private fun setLayoutFullscreen() {
        val flags = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    override fun getNavHostFragment(): NavHostFragment {
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main)
        return fragment as NavHostFragment
    }
}