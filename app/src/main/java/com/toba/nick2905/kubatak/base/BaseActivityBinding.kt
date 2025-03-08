package com.toba.nick2905.kubatak.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


/**
 * @author Nicolas Manurung (nicolas.manurung@dana.id)
 * @version BaseActivityBinding, v 0.1 29/12/22 16.26 by Nicolas Manurung
 */
abstract class BaseActivityBinding<T : ViewBinding> : AppCompatActivity() {

    abstract fun bindingInflater(): T

    protected val binding: T by lazy(LazyThreadSafetyMode.NONE) { bindingInflater() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
    }

    abstract fun setupView()
}