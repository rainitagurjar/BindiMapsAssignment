package com.example.bindimaps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bindimaps.R
import com.example.bindimaps.model.MainUserSessionsItem
import java.util.Collections.emptyList

class SessionAdapter(private val onClickListener: OnClickListener) :

    RecyclerView.Adapter<SessionAdapter.ViewHolder>() {

    private var mList: List<MainUserSessionsItem> = emptyList()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sessions, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mMainUserSessionsItem = mList[position]

        holder.tvUserId.text = mList[position].userId
        holder.itemView.setOnClickListener {
            onClickListener.onClick(mMainUserSessionsItem)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(list: List<MainUserSessionsItem>) {
        mList = emptyList()
        mList = list
        notifyDataSetChanged()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvUserId: TextView = itemView.findViewById(R.id.tvUserId)
    }

    class OnClickListener(val clickListener: (mMainUserSessionsItem: MainUserSessionsItem) -> Unit) {
        fun onClick(mMainUserSessionsItem: MainUserSessionsItem) =
            clickListener(mMainUserSessionsItem)
    }
}
