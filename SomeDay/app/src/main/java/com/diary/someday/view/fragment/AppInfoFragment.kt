package com.diary.someday.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diary.someday.databinding.FragmentAppInfoBinding
import android.content.pm.PackageManager

import androidx.navigation.fragment.findNavController


class AppInfoFragment : Fragment() {

    private lateinit var binding: FragmentAppInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingToolbar()
        getAppCode()
    }

    private fun getAppCode() {
        var versionName = ""
        try {
            val pInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            versionName = pInfo.versionName + ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        binding.appInfoCode.text = "앱 버전 : " + versionName.toString()
    }

    private fun bindingToolbar() {
        setHasOptionsMenu(true)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}