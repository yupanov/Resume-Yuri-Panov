package com.github.yupanov.resumeyp.info

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.InfoItemBinding
import com.github.yupanov.resumeyp.main.ButtonsRvAdapter


class InfoPagerAdapter(resources: Resources): RecyclerView.Adapter<InfoPagerAdapter.InfoPagerViewHolder>() {
    var data = infoBase(resources).getInstance()
        set(value) {
            field = value
            notifyDataSetChanged() // DiffUtil?
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoPagerViewHolder {
        return InfoPagerViewHolder.getHolder(parent)
    }

    override fun onBindViewHolder(holder: InfoPagerViewHolder, position: Int) {
        val curData = data[position]
        holder.bind(curData)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class InfoPagerViewHolder private constructor(binding: InfoItemBinding): RecyclerView.ViewHolder(binding.root) {
        val tvDescription = binding.tvInfoDescription
        val tvTitle = binding.tvInfoTitle
        val iv = binding.ivInfo


        companion object{
            fun getHolder(parent: ViewGroup): InfoPagerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = InfoItemBinding.inflate(layoutInflater, parent, false)

                return InfoPagerViewHolder(binding)
            }
        }

        fun bind(curData: Info) {
            tvTitle.text = curData.title
            tvDescription.text = curData.description
            iv.setImageResource(R.drawable.silla)
        }
    }
}