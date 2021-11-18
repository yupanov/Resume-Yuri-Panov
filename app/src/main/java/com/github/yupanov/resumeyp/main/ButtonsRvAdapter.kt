package com.github.yupanov.resumeyp.main

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.RvHolderBinding
import com.github.yupanov.resumeyp.info.Info
import com.github.yupanov.resumeyp.info.infoBase

class ButtonsRvAdapter(resources: Resources) : RecyclerView.Adapter<ButtonsRvAdapter.ButtonsHolder>() {
    var data: List<Info> = infoBase(resources).getInstance()
    set(value) {
        field = value
        notifyDataSetChanged() // DiffUtil?
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonsHolder {
        return ButtonsHolder.getHolder(parent)
    }

    override fun onBindViewHolder(holder: ButtonsHolder, position: Int) {
        val curData = data[position]
        holder.bind(curData, position)
    }

    override fun getItemCount() = data.size


    class ButtonsHolder private constructor(binding: RvHolderBinding) : ViewHolder(binding.root) {
        val tvTitle = binding.tvRvTitle
        val container = binding.containerRv
        val tvDescription = binding.tvRvDescription

        companion object{
            fun getHolder(parent: ViewGroup): ButtonsHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RvHolderBinding.inflate(inflater)
                return ButtonsHolder(binding)
            }
        }

        fun bind(curData: Info, position: Int) {
            tvTitle.text = curData.title
            tvDescription.text = curData.description
            container.setOnClickListener { view ->
                view.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToInfoFragment(position))
            }
        }
    }

}