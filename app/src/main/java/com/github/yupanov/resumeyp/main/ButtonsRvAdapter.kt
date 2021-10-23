package com.github.yupanov.resumeyp.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.info.infoBase

class ButtonsRvAdapter : RecyclerView.Adapter<ButtonsRvAdapter.ButtonsHolder>() {
    var data = infoBase.base
    set(value) {
        field = value
        notifyDataSetChanged() // DiffUtil?
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rv_holder, parent, false)
        return ButtonsHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonsHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.title
//        holder.container.setOnClickListener { view ->
//            view.findNavController()
//                .navigate(MainFragmentDirections.actionMainFragmentToInfoFragment(position))
//        }
    }

    override fun getItemCount() = data.size


    class ButtonsHolder(itemView: View) : ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_rv_button)
        val container = itemView.findViewById<FrameLayout>(R.id.container)
    }
}