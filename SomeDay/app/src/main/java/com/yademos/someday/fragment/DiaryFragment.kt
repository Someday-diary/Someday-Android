package com.yademos.someday.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.app.AlertDialog
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.yademos.someday.R
import com.yademos.someday.databinding.FragmentDiaryBinding
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.yademos.someday.viewModel.DiaryViewModel
import org.koin.android.viewmodel.compat.ViewModelCompat.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DiaryFragment : Fragment() {

    private lateinit var binding: FragmentDiaryBinding
    private val args: DiaryFragmentArgs by navArgs()
    private val viewModel: DiaryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        bindingToolbar()
        bindingTagEditText()
        bindingSaveButton()

        return binding.root
    }

    private fun bindingToolbar() {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarTitle.text = String.format("%s년 %s월 %s일", args.year, args.month, args.day)

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
//                Log.d("TEST", ""+("" + c[binding.tagEditText.length() - 1]).equals(" "))
                if (("" + c[binding.tagEditText.length() - 1]).equals(" ")) {
                    binding.tagEditText.append("#")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun bindingSaveButton() {
        binding.saveDiaryButton.setOnClickListener {
            val content = binding.contextEditText.text.toString()
            val tag = binding.tagEditText.text.toString()

            if (content.isEmpty()) {
//                showDialog()
            } else {
                val date = Date(args.date)
                viewModel.insert(date, content)
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_diaryFragment_to_mainFragment)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_diary, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}