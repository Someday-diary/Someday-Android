package com.diary.someday.fragment

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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.diary.someday.Data.SearchDate
import com.diary.someday.Data.SearchMonth
import com.diary.someday.Data.response.DiaryListResponse
import com.diary.someday.Data.response.DiaryResponse
import com.diary.someday.adapter.RecyclerViewMonthSearchAdapter
import com.diary.someday.viewModel.DiaryViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: DiaryViewModel by viewModels()
    private lateinit var recyclerViewAdapter: RecyclerViewMonthSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

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

    private fun initRecyclerView() {
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewMonthSearchAdapter(context)
            adapter = recyclerViewAdapter
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
                var cursor = diaryList[0].date.month + 1

                binding.searchTag.text = "#" + binding.searchEditText.text
                binding.searchTag.visibility = View.VISIBLE
                binding.searchRecyclerView.visibility = View.VISIBLE

                for (i in diaryList.indices) {
                    if (diaryList[i].date.month + 1 == cursor) {
                        dateList.add(SearchDate(diaryList[i].date.day, diaryList[i].contents, diaryList[i].tags))
                    } else {
                        monthList.add(SearchMonth(diaryList[i].date.year.toString(), cursor.toString(), dateList))
                        cursor = diaryList[i].date.month + 1
                        dateList.add(SearchDate(diaryList[i].date.day, diaryList[i].contents, diaryList[i].tags))
                        dateList = mutableListOf()
                    }
                }
                recyclerViewAdapter.setData(monthList)
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
    }

}