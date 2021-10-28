package com.yademos.someday.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.yademos.someday.R
import com.yademos.someday.databinding.FragmentDiaryBinding
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.yademos.someday.viewModel.DiaryViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.yademos.someday.db.model.Diary
import com.yademos.someday.viewModel.MainViewModel

class DiaryFragment : Fragment() {

    private lateinit var binding: FragmentDiaryBinding
//    private val args: DiaryFragmentArgs by navArgs()
    private val viewModel: DiaryViewModel by viewModel()
    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
//        val date = Date(args.date)
        val date = mViewModel.date.value?.let { Date(it) }
        val year = mViewModel.year.value
        val month = mViewModel.month.value
        val day = mViewModel.day.value

        setHasOptionsMenu(true)
        bindingToolbar(year, month, day) // Toolbar 설정
        bindingTagEditText()
        bindingSaveButton(date)
        bindingContextEditText(date)

        return binding.root
    }

    private fun bindingToolbar(year: String?, month: String?, day: String?) {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarTitle.text = String.format("%s년 %s월 %s일", year, month, day)

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_diaryFragment_to_mainFragment)
        }
    }

    private fun bindingTagEditText() {
        binding.tagEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && binding.tagEditText.text.toString().isEmpty()) {
                binding.tagEditText.append("#")
            }
        }

        binding.tagEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val c = binding.tagEditText.text.toString()
                if (("" + c[binding.tagEditText.length() - 1]).equals(" ")) {
                    binding.tagEditText.append("#")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun bindingSaveButton(date: Date?) {
        binding.saveDiaryButton.setOnClickListener {
            val content = binding.contextEditText.text.toString()
            val tag = binding.tagEditText.text.toString()

            if (content.isEmpty()) {
//                showDialog()
            } else {
                viewModel.insertDiary(Diary(createUUID(), content, date))
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_diaryFragment_to_mainFragment)
            }
        }
    }

    private fun bindingContextEditText(date: Date?) {
        if (date != null) {
            viewModel.getDiary(date).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    binding.contextEditText.setText(it.contents)
                }
            })
        }
    }

    private fun createUUID() : String{
        return UUID.randomUUID().toString()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_diary, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                val date = mViewModel.date.value?.let { Date(it) }
                if (date != null) {
                    viewModel.deleteDiary(date)
                }
                findNavController().navigate(DiaryFragmentDirections.actionDiaryFragmentToMainFragment())
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}