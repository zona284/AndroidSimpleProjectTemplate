package com.rakha.simpleprojecttemplate.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rakha.simpleprojecttemplate.R
import com.rakha.simpleprojecttemplate.view.viewholder.BaseViewHolder
import com.rakha.simpleprojecttemplate.view.viewholder.LoadMoreViewHolder

/**
 *   Created By rakha
 *   2020-01-31
 */
abstract class BaseRecyclerViewAdapter<T, VH>(
    val context: Context,
    private val layoutRes: Int,
    data: MutableList<T>
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var data: MutableList<T>? = null
    private var inflater: LayoutInflater? = null
    private val itemLayout: Int = 0

    private var isShowFooter: Boolean = false
    abstract var recyclerAction: RecyclerAction<VH>
    /**
     * method for get header viewHolder which used
     *
     * @return BaseViewHolder
     */
    val headerViewHolder: BaseViewHolder? = null
    private var footerViewHolder: BaseViewHolder? = null

    init {
        setData(data)
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == TYPE_HEADER) {
            return headerViewHolder!!
        } else if (viewType == TYPE_FOOTER) {
            val view = inflater!!.inflate(R.layout.view_load_more, parent, false)
            return LoadMoreViewHolder(view)
        } else {
            val view = inflater!!.inflate(layoutRes, parent, false)
            return recyclerAction.getItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.itemViewType === TYPE_ITEM) {
            recyclerAction.onCreateListItemView(holder as VH, position)
        } else if (holder.itemViewType === TYPE_HEADER) {
            recyclerAction.onCreateHeaderItemView(holder, position)
        } else if (holder.itemViewType === TYPE_FOOTER) {
            recyclerAction.onCreateFooterItemView(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionHeader(position)) {
            return TYPE_HEADER
        } else if (isPositionFooter(position)) {
            return TYPE_FOOTER
        }
        return TYPE_ITEM
    }

    /**
     * check is header position
     *
     * @param position int
     * @return boolean
     */
    private fun isPositionHeader(position: Int): Boolean {
        return position == 0 && headerViewHolder != null && data!![position] == null
    }

    /**
     * check is footer position
     *
     * @param position int
     * @return boolean
     */
    private fun isPositionFooter(position: Int): Boolean {
        return isShowFooter && data!![position] == null
    }


    override fun getItemCount(): Int {
        if (data == null) {
            try {
                throw Exception("data list adapter Null")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return 0
        }
        return data!!.size
    }

    fun setData(data: MutableList<T>) {
        this.data = data
    }

    fun getData(): List<T>? {
        return data
    }

    /**
     * method for returning adapter item object
     *
     * @param position int
     * @return T
     */
    fun getItem(position: Int): T {
        return data!![position]
    }

    fun animateTo(models: List<T>) {
        applyAndAnimateRemovals(models)
        applyAndAnimateAdditions(models)
        applyAndAnimateMovedItems(models)
    }

    private fun applyAndAnimateRemovals(newModels: List<T>) {
        for (i in data!!.indices.reversed()) {
            val model = data!![i]
            if (!newModels.contains(model)) {
                removeItem(i)
            }
        }
    }

    private fun applyAndAnimateAdditions(newModels: List<T>) {
        var i = 0
        val count = newModels.size
        while (i < count) {
            val model = newModels[i]
            if (!data!!.contains(model)) {
                addItem(i, model)
            }
            i++
        }
    }

    private fun applyAndAnimateMovedItems(newModels: List<T>) {
        for (toPosition in newModels.indices.reversed()) {
            val model = newModels[toPosition]
            val fromPosition = data!!.indexOf(model)
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }

    fun removeItem(position: Int): T {
        val model = data!!.removeAt(position)
        notifyItemRemoved(position)
        return model
    }

    fun addItem(position: Int, model: T) {
        data!!.add(position, model)
        notifyItemInserted(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val model = data!!.removeAt(fromPosition)
        data!!.add(toPosition, model)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun setShowFooter(showFooter: Boolean) {
        isShowFooter = showFooter
    }

    /**
     * method for get footer viewHolder which used
     *
     * @return BaseViewHolder
     */
    fun getFooterViewHolder(): BaseViewHolder? {
        if (footerViewHolder == null) {
            val view = inflater!!.inflate(R.layout.view_load_more, null, false)
            footerViewHolder = LoadMoreViewHolder(view)
        }
        return footerViewHolder
    }

    fun setAction(recyclerAction: RecyclerAction<VH>) {
        this.recyclerAction = recyclerAction
    }

    interface RecyclerAction<VH> {
        fun getItemViewHolder(view: View): BaseViewHolder

        fun onCreateListItemView(holder: VH, position: Int)

        fun onCreateHeaderItemView(holder: BaseViewHolder, position: Int)

        fun onCreateFooterItemView(holder: BaseViewHolder, position: Int)

    }

    companion object {
        private val TYPE_HEADER = 0
        private val TYPE_ITEM = 1
        private val TYPE_FOOTER = 2
    }
}