package com.frogobox.mvvm.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frogobox.base.ui.BaseActivity

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * mvvm
 * Copyright (C) 16/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.mvvm.util
 *
 */
open class BaseAppActivity : BaseActivity() {

    fun <T : ViewModel> obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProvider(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

}