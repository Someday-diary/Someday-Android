package com.yademos.someday.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.yademos.someday.R
import com.yademos.someday.databinding.FragmentDiaryBinding
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs

class DiaryFragment : Fragment() {

    private lateinit var binding: FragmentDiaryBinding
    private val args: DiaryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        initToolbar()

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_diaryFragment_to_mainFragment)
        }

        binding.tagEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && binding.tagEditText.text.toString().isEmpty()) {
                binding.tagEditText.append("#")
            }
        }

        binding.tagEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val c = binding.tagEditText.text.toString()
//                Log.d("TEST", ""+("" + c[binding.tagEditText.length() - 1]).equals(" "))
                if (("" + c[binding.tagEditText.length() - 1]).equals(" ")) {
                    binding.tagEditText.append("#")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        return binding.root
    }

    private fun initToolbar() {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarTitle.text = String.format("%s년 %s월 %s일", args.year, args.month, args.day)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_diary, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_write -> {
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}