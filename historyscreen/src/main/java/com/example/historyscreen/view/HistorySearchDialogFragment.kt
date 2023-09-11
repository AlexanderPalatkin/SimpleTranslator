package com.example.historyscreen.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.historyscreen.databinding.HistorySearchDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HistorySearchDialogFragment : BottomSheetDialogFragment() {

    private var _binding: HistorySearchDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private var onHistorySearchClickListener: OnHistorySearchClickListener? = null

    private val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.historySearchEditText.text != null &&
                binding.historySearchEditText.text.toString().isNotEmpty()
            ) {
                binding.historySearchButtonTextview.isEnabled = true
                binding.historySearchClearTextImageview.visibility = View.VISIBLE
            } else {
                binding.historySearchButtonTextview.isEnabled = false
                binding.historySearchClearTextImageview.visibility = View.GONE
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    private val onHistorySearchButtonClickListener =
        View.OnClickListener {
            onHistorySearchClickListener?.onClick(binding.historySearchEditText.text.toString())
            dismiss()
        }

    internal fun setOnHistorySearchClickListener(listener: OnHistorySearchClickListener) {
        onHistorySearchClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistorySearchDialogFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.historySearchButtonTextview.setOnClickListener(onHistorySearchButtonClickListener)
        binding.historySearchEditText.addTextChangedListener(textWatcher)
        addOnClearClickListener()
    }

    override fun onDestroyView() {
        onHistorySearchClickListener = null
        binding.historySearchEditText.removeTextChangedListener(textWatcher)

        super.onDestroyView()
    }

    private fun addOnClearClickListener() {
        binding.historySearchClearTextImageview.setOnClickListener {
            binding.historySearchEditText.setText("")
            binding.historySearchButtonTextview.isEnabled = false
        }
    }

    interface OnHistorySearchClickListener {

        fun onClick(searchWord: String)
    }

    companion object {

        fun newInstance(): HistorySearchDialogFragment {
            return HistorySearchDialogFragment()
        }
    }
}