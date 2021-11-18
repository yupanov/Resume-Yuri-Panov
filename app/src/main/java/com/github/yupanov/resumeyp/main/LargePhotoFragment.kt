package com.github.yupanov.resumeyp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.yupanov.resumeyp.databinding.FragmentLargePhotoBinding

class LargePhotoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentLargePhotoBinding.inflate(inflater)
        binding.ivLargePhoto.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}