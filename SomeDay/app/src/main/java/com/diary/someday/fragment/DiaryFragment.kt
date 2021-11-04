package com.diary.someday.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.diary.someday.R
import com.diary.someday.databinding.FragmentDiaryBinding
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.diary.someday.viewModel.DiaryViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import androidx.navigation.fragment.findNavController
import com.diary.someday.Data.request.DiaryRequest
import com.diary.someday.Data.request.Tag
import com.diary.someday.Data.request.UpdateDiaryRequest
import com.diary.someday.db.model.Diary

class DiaryFragment : Fragment() {

    private lateinit var binding: FragmentDiaryBinding

    private lateinit var postId: String
    private val args: DiaryFragmentArgs by navArgs()
    private val viewModel: DiaryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        bindingToolbar() // Toolbar 설정
        bindingTagEditText()
        bindingSaveButton()
        bindingContextEditText()

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
            findNavController().navigate(DiaryFragmentDirections.actionDiaryFragmentToMainFragment())
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

    private fun bindingSaveButton() {
        binding.saveDiaryButton.setOnClickListener {
            val date = Date(args.date)
            val content = binding.contextEditText.text.toString()
            val tag = binding.tagEditText.text.toString()

            if (content.isEmpty() || tag.isEmpty()) {
//                showDialog()
            } else {
                viewModel.callGetDiaryWithPostId(postId)
                viewModel.diaryLiveData.observe(viewLifecycleOwner, {
                    if (it == null) {
//                        viewModel.insertDiary(Diary(createUUID(), content, date))
                        viewModel.callCreateDiary(requestDiary())
                    } else {
                        viewModel.callUpdateDiary(requestUpdateDiary())
                    }
                })
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_diaryFragment_to_mainFragment)
            }
        }
    }

    private fun bindingContextEditText() {
        viewModel.callGetDiaryWithPostId(postId)
        viewModel.diaryLiveData.observe(viewLifecycleOwner, {
            binding.contextEditText.setText(it?.post?.contents)
            for (i in 0..it!!.post.tags.size) {
                binding.tagEditText.append("#")
                binding.tagEditText.append(it.post.tags[i].tag + " ")
            }
        })
    }

    private fun createUUID(): String {
        return UUID.randomUUID().toString()
    }

    private fun splitTag(): List<Tag> {
        val tagRequest = mutableListOf<Tag>()
        val split = binding.tagEditText.text.split("#")
        for (i in 0..split.size) {
            Log.d("splitTag : ", split[i])
            val tag = Tag(split[i])
            tagRequest.add(tag)
        }
        return tagRequest
    }

    private fun requestDiary(): DiaryRequest {
        val date = Date(args.date)
//        viewModel.getDiary(date)
        return DiaryRequest(
            splitTag(),
            binding.contextEditText.text.toString(),
            date,
            createUUID(),
        )
    }

    private fun requestUpdateDiary(): UpdateDiaryRequest {
        val date = Date(args.date)
        return UpdateDiaryRequest(
            postId,
            binding.contextEditText.text.toString(),
            splitTag(),
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_diary, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
//                viewModel.deleteDiary(date)
                viewModel.callDeleteDiary(postId)
                findNavController().navigate(DiaryFragmentDirections.actionDiaryFragmentToMainFragment())
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}