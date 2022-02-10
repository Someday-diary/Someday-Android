package com.diary.someday.view.fragment

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.diary.someday.R
import com.diary.someday.databinding.FragmentDiaryBinding
import androidx.navigation.fragment.navArgs
import com.diary.someday.viewModel.DiaryViewModel
import java.util.*
import androidx.navigation.fragment.findNavController
import com.diary.someday.model.network.dto.request.diary.DiaryRequest
import com.diary.someday.model.network.dto.request.diary.Tag
import com.diary.someday.model.network.dto.request.diary.UpdateDiaryRequest
import com.diary.someday.di.application.Application
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DiaryFragment : Fragment() {

    private lateinit var binding: FragmentDiaryBinding

    private val args: DiaryFragmentArgs by navArgs()
    private val viewModel: DiaryViewModel by viewModel()
    private var postId = ""
    private var editState = false

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

    override fun onStart() {
        super.onStart()
        changeModeColor(Application.themeSettingColor.getThemeTypeColor())
    }

    private fun changeModeColor(number: Int) {
        when (number) {
            1 -> {
                binding.saveDiaryButton.setBackgroundResource(R.drawable.ic_login_button_abled_green)
                binding.tagEditText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green3
                    )
                )
                binding.tagEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green1
                    )
                )
                binding.contextEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green2
                    )
                )
                binding.tagEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green1
                    ), PorterDuff.Mode.SRC_ATOP
                )
                binding.contextEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green3
                    ), PorterDuff.Mode.SRC_ATOP
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    binding.contextEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_green)
                    binding.tagEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_green)
                }
            }
            2 -> {
                binding.saveDiaryButton.setBackgroundResource(R.drawable.ic_login_button_abled_blue)
                binding.tagEditText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue3
                    )
                )
                binding.tagEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue1
                    )
                )
                binding.contextEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue2
                    )
                )
                binding.tagEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue1
                    ), PorterDuff.Mode.SRC_ATOP
                )
                binding.contextEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue3
                    ), PorterDuff.Mode.SRC_ATOP
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    binding.contextEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_blue)
                    binding.tagEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_blue)
                }
            }
            3 -> {
                binding.saveDiaryButton.setBackgroundResource(R.drawable.ic_login_button_abled_purple)
                binding.tagEditText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple3
                    )
                )
                binding.tagEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple1
                    )
                )
                binding.contextEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple2
                    )
                )
                binding.tagEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple1
                    ), PorterDuff.Mode.SRC_ATOP
                )
                binding.contextEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple3
                    ), PorterDuff.Mode.SRC_ATOP
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    binding.contextEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_purple)
                    binding.tagEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_purple)
                }
            }
            4 -> {
                binding.saveDiaryButton.setBackgroundResource(R.drawable.ic_login_button_abled_yellow)
                binding.tagEditText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow3
                    )
                )
                binding.tagEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow1
                    )
                )
                binding.contextEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow2
                    )
                )
                binding.tagEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow1
                    ), PorterDuff.Mode.SRC_ATOP
                )
                binding.contextEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow3
                    ), PorterDuff.Mode.SRC_ATOP
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    binding.contextEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_yellow)
                    binding.tagEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_yellow)
                }
            }
            5 -> {
                binding.saveDiaryButton.setBackgroundResource(R.drawable.ic_login_button_abled_red)
                binding.tagEditText.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red3
                    )
                )
                binding.tagEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red1
                    )
                )
                binding.contextEditText.setHintTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red2
                    )
                )
                binding.tagEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red1
                    ), PorterDuff.Mode.SRC_ATOP
                )
                binding.tagEditText.background.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red3
                    ), PorterDuff.Mode.SRC_ATOP
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    binding.contextEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_red)
                    binding.tagEditText.textCursorDrawable = ContextCompat.getDrawable(activity as Context,R.drawable.ic_custom_cursor_red)
                }
            }
        }
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
            findNavController().popBackStack()
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
                if  (c.isEmpty()) {
                    binding.tagEditText.append("#")
                }
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

            if (content.isEmpty() || tag.isEmpty() || tag == "#") {
                DiaryDialogFragment().show(
                    parentFragmentManager, "DiaryDialog"
                )
            } else {
                if (!editState) {
                    viewModel.callCreateDiary(requestDiary())
                    Toast.makeText(requireContext(), "일기가 정상적으로 저장되었습니다!", Toast.LENGTH_SHORT)
                        .show()
//                    Navigation.findNavController(binding.root)
//                        .navigate(R.id.action_diaryFragment_to_mainFragment)
                    findNavController().popBackStack()
                } else {
                    viewModel.callUpdateDiary(postId, requestUpdateDiary())
                    Toast.makeText(requireContext(), "일기가 정상적으로 수정되었습니다!", Toast.LENGTH_SHORT)
                        .show()
//                    Navigation.findNavController(binding.root)
//                        .navigate(R.id.action_diaryFragment_to_mainFragment)
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun bindingContextEditText() {
        viewModel.callGetDateDiary(args.year.toInt(), args.month.toInt(), args.day.toInt())
        viewModel.dateDiaryLiveData.observe(viewLifecycleOwner, {
            if (it != null) {
                postId = it.post.post_id
                binding.contextEditText.setText(it.post.contents)
                binding.tagEditText.append("#")
                for (element in it.post.tags) {
                    binding.tagEditText.append(element.tag_name + " ")
                    editState = true
                }
            }
        })
    }

    private fun createUUID(): String {
        return UUID.randomUUID().toString()
    }

    private fun splitTag(): List<Tag> {
        val tagList = mutableListOf<Tag>()
        val split = binding.tagEditText.text.split("#")
        for (i in 1 until split.size) {
            val str = split[i].replace(" ", "")
            if (str == "") {
                continue
            }
            tagList.add(Tag(str))
        }
        return tagList
    }

    private fun requestDiary(): DiaryRequest {
//        viewModel.getDiary(date)
        return DiaryRequest(
            splitTag(),
            binding.contextEditText.text.toString(),
            ChangeDateFormat(args.date),
            createUUID(),
        )
    }

    private fun ChangeDateFormat(date: Long): String {
        val myDate = Date(date)
        val df = SimpleDateFormat("yyyy-MM-dd")
        return df.format(myDate)
    }

    private fun requestUpdateDiary(): UpdateDiaryRequest {
        return UpdateDiaryRequest(
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
                Toast.makeText(requireContext(), "일기가 정상적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}