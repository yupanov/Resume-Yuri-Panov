package com.github.yupanov.resumeyp.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.FragmentContactsBinding

class ContactsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View {

        val binding = FragmentContactsBinding.inflate(inflater)
        binding.containerAddress.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:-34.621,-58.4")
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.containerPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:" + resources.getString(R.string.phone_text))
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.containerEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.email_text)))
                putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.email_subject))
                putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.email_body))
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.containerLinkedin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/"
                    + resources.getString(R.string.yupanov)))
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.containerGithub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/"
                    + resources.getString(R.string.yupanov)))
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.containerContacts.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}