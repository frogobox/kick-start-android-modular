package com.frogobox.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

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
 * com.frogobox.base
 *
 */
abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    open fun bindItem(data: T, listener: BaseListener<T>){
        onItemViewClicked(data, listener)
        initComponent(data)
    }

    protected fun onItemViewClicked(data: T, listener: BaseListener<T>){
        itemView.setOnClickListener {
            listener.onItemClicked(data)
        }
    }

    open fun initComponent(data: T){
        // component view
    }

}