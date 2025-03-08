package com.toba.nick2905.kubatak.ui

import android.content.Intent
import com.toba.nick2905.kubatak.base.BaseActivityBinding
import com.toba.nick2905.kubatak.databinding.ActivitySplashscreenBinding
import com.toba.nick2905.kubatak.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashscreenActivity : BaseActivityBinding<ActivitySplashscreenBinding>() {

    private val SPLASH_DELAY: Long = 3000
    override fun bindingInflater(): ActivitySplashscreenBinding =
        ActivitySplashscreenBinding.inflate(layoutInflater)

    override fun setupView() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_DELAY)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
