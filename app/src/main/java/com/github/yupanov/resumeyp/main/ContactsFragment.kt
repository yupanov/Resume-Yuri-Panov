package com.github.yupanov.resumeyp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.yupanov.resumeyp.databinding.FragmentContactsBinding

class ContactsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View {

        val binding = FragmentContactsBinding.inflate(inflater)

        return binding.root
    }
}