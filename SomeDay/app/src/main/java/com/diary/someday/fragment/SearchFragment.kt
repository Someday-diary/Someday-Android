package com.diary.someday.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.diary.someday.databinding.FragmentSearchBinding
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.diary.someday.Data.SearchDate
import com.diary.someday.Data.SearchMonth
import com.diary.someday.Data.response.DiaryListResponse
import com.diary.someday.Data.response.DiaryResponse
import com.diary.someday.R
import com.diary.someday.adapter.RecyclerViewMonthSearchAdapter
import com.diary.someday.application.Application
import com.diary.someday.decoration.RecyclerViewDecoration
import com.diary.someday.viewModel.DiaryViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: DiaryViewModel by viewModels()
    private lateinit var recyclerViewAdapter: RecyclerViewMonthSearchAdapter
    private lateinit var listData: MutableList<SearchMonth>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        listData = mutableListOf()

        initToolbar()
        initRecyclerView()

        binding.searchEditText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                setRecyclerView()
                return@OnEditorActionListener true
            }
            false
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        changeModeColor(Application.themeSettingColor.getThemeTypeColor())
    }

    private fun changeModeColor(number: Int) {
        when (number) {
            1 -> {
                binding.searchBackButton.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_back_green
                )
                binding.searchEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.custom_search_edittext_green
                )
                binding.searchTag.setTextColor(ContextCompat.getColor(requireContext(), R.color.green3))
            }
            2 -> {
                binding.searchBackButton.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_back_blue
                )
                binding.searchEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.custom_search_edittext_blue
                )
                binding.searchTag.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue3))
            }
            3 -> {
                binding.searchBackButton.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_back_purple
                )
                binding.searchEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.custom_search_edittext_purple
                )
                binding.searchTag.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple3))
            }
            4 -> {
                binding.searchBackButton.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_back_yellow
                )
                binding.searchEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.custom_search_edittext_yellow
                )
                binding.searchTag.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow3))
            }
            5 -> {
                binding.searchBackButton.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.ic_back_red
                )
                binding.searchEditText.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.custom_search_edittext_red
                )
                binding.searchTag.setTextColor(ContextCompat.getColor(requireContext(), R.color.red3))
            }
        }
    }

    private fun initRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(40)
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewMonthSearchAdapter(context)
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
    }

    private fun setRecyclerView() {
        viewModel.callGetDiaryWithTag(listOf(binding.searchEditText.text.toString()))
        viewModel.diaryListLiveData.observe(viewLifecycleOwner, { diary ->
            val monthList = mutableListOf<SearchMonth>()
            var dateList = mutableListOf<SearchDate>()
            if (diary?.posts != null) {
                binding.errorMsg.visibility = View.GONE

                val diaryList = diary.posts.sortedBy { it.date }
                Log.d("diaryList", diaryList.toString())

                val split = diaryList[0].date.split("-")
                var dateSplit: List<String> = listOf()
                var cursor = split[1]

                binding.searchTag.text = "#" + binding.searchEditText.text
                binding.searchTag.visibility = View.VISIBLE
                binding.searchRecyclerView.visibility = View.VISIBLE

                for (i in diaryList.indices) {
                    dateSplit = diaryList[i].date.split("-")
                    if (dateSplit[1] == cursor) {
                        dateList.add(SearchDate(dateSplit[2], diaryList[i].contents, diaryList[i].tags))
                        Log.d("dateList", dateList.toString())
                    } else {
                        monthList.add(SearchMonth(dateSplit[0], cursor, dateList))
                        Log.d("monthList", monthList.toString())
                        cursor = dateSplit[1]
                        dateList = mutableListOf()
                        Log.d("dateList", dateList.toString())
                        dateList.add(SearchDate(dateSplit[2], diaryList[i].contents, diaryList[i].tags))
                        Log.d("dateList", dateList.toString())
                    }
                }
                monthList.add(SearchMonth(dateSplit[0], cursor, dateList))
                listData.addAll(monthList)
                recyclerViewAdapter.setData(listData, Application.themeSettingColor.getThemeTypeColor())
                Log.d("listData", listData.toString())
                listData.clear()
                Log.d("monthList", monthList.toString())

            } else {
                binding.searchTag.visibility = View.GONE
                binding.searchRecyclerView.visibility = View.GONE
                binding.errorMsg.visibility = View.VISIBLE
            }
        })
    }

    private fun initToolbar() {
        val activity = activity as AppCompatActivity

        activity.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(false)
            }
        }

        binding.searchBackButton.setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToMainFragment())
        }

        binding .searchClearButton.setOnClickListener {
            binding.searchEditText.setText("")
        }
    }


}