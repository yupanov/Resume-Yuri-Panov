package com.github.yupanov.resumeyp.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.yupanov.resumeyp.databinding.FragmentInfoPagerBinding

class InfoFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = InfoPagerAdapter(resources)
        val binding = FragmentInfoPagerBinding.inflate(inflater)

        binding.infoPager.adapter = adapter
        val pageId = requireArguments().getInt("id")
        binding.infoPager.setCurrentItem(pageId, false)

        return binding.root
    }
}