package com.rakha.simpleprojecttemplate.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *   Created By rakha
 *   2020-01-31
 */

abstract class BaseViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * abstract method for initialize viewHolder view
     *
     * @param view View
     */
    protected abstract fun initView(view: View)
}