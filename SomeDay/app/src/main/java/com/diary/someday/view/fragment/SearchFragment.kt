package com.diary.someday.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.diary.someday.databinding.FragmentSearchBinding
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.diary.someday.view.adapter.data.SearchDate
import com.diary.someday.view.adapter.data.SearchMonth
import com.diary.someday.R
import com.diary.someday.view.adapter.RecyclerViewRecentSearchAdapter
import com.diary.someday.view.adapter.RecyclerViewMonthSearchAdapter
import com.diary.someday.di.application.Application
import com.diary.someday.model.db.Search
import com.diary.someday.model.network.dto.response.DiaryListResponse
import com.diary.someday.view.decoration.RecyclerViewDecoration
import com.diary.someday.viewModel.DiaryViewModel
import com.diary.someday.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: DiaryViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var recyclerViewAdapter: RecyclerViewMonthSearchAdapter
    private lateinit var searchRecyclerviewAdapter: RecyclerViewRecentSearchAdapter
    private lateinit var listData: MutableList<SearchMonth>
    private var imm: InputMethodManager?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        listData = mutableListOf()

        initToolbar()
        initSearchRecyclerView()
        initRecyclerView()
        bindingSearchEditText()
        deleteAll()

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

    private fun bindingSearchEditText() {
        binding.searchEditText.apply {
            setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(binding.searchEditText.text.toString())
                    return@OnEditorActionListener true
                }
                false
            })
            setOnClickListener {
                binding.recentSearchLayout.visibility = View.VISIBLE
                binding.searchResultLayout.visibility = View.GONE
                binding.errorMsg.visibility = View.GONE
            }
        }
    }

    private fun deleteAll() {
        binding.recentDeleteAll.setOnClickListener {
            searchViewModel.deleteAll()
            searchRecyclerviewAdapter.notifyDataSetChanged()
        }
    }

    private fun search(searches: String) {
        keyboardDown()
        binding.recentSearchLayout.visibility = View.GONE
        searchViewModel.insert(Search(searches))
        viewModel.callGetDiaryWithTag(listOf(searches))
        viewModel.diaryListLiveData.observe(viewLifecycleOwner, {
            if (it?.posts != null) {
                binding.searchTag.text = "#" + searches
                binding.searchResultLayout.visibility = View.VISIBLE
                binding.errorMsg.visibility = View.GONE
                setRecyclerView(it)
            } else {
                binding.searchResultLayout.visibility = View.GONE
                binding.errorMsg.visibility = View.VISIBLE
            }
        })
    }

    private fun keyboardDown() {
        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    private fun initSearchRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(40)
        binding.recentSearchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            searchRecyclerviewAdapter = RecyclerViewRecentSearchAdapter()
            adapter = searchRecyclerviewAdapter
            addItemDecoration(decoration)
        }
        searchViewModel.getAll()
        searchViewModel.searchObserve().observe(viewLifecycleOwner, {
            if (it != null) {
                searchRecyclerviewAdapter.setData(it)
            }
        })
        searchRecyclerviewAdapter.setOnItemClickListener {
            val searches = searchRecyclerviewAdapter.getData()
            when (it) {
                1 -> {
                    searchViewModel.delete(Search(searches))
                    searchRecyclerviewAdapter.notifyDataSetChanged()
                }
                2 -> {
                    search(searches)
                }
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

    private fun setRecyclerView(diary: DiaryListResponse) {
        val monthList = mutableListOf<SearchMonth>()
        var dateList = mutableListOf<SearchDate>()

        val diaryList = diary.posts!!.sortedBy { it.date }
        Log.d("diaryList", diaryList.toString())

        val split = diaryList[0].date.split("-")
        var dateSplit: List<String> = listOf()
        var cursor = split[1]

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
            findNavController().popBackStack()
        }

        binding .searchClearButton.setOnClickListener {
            binding.searchEditText.setText("")
        }
    }


}