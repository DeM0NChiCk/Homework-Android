package com.example.homeworkapp.dialog

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.DialogSheetSortBinding
import com.example.homeworkapp.recycler.sort.SortingMode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class SheetDialogSort: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogSheetSortBinding.inflate(inflater, container, false)

        val sortingMode =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable(SORTING_MODE_KEY, SortingMode::class.java)
                    ?: SortingMode.BY_ID_ASC
            } else {
                arguments?.getSerializable(SORTING_MODE_KEY) as? SortingMode
                    ?: SortingMode.BY_ID_ASC
            }

        with(binding) {
            when (sortingMode) {
                SortingMode.BY_ID_ASC, SortingMode.BY_ID_DESC -> setSortingModeActive(
                    btnDialogSortById
                )
                SortingMode.BY_NAME_ASC, SortingMode.BY_NAME_DESC -> setSortingModeActive(
                    btnDialogSortByName
                )
            }

            btnDialogSortById.setOnClickListener {
                parentFragmentManager.setFragmentResult(REQUEST_KEY, Bundle().apply {
                    putSerializable(
                        SORTING_MODE_KEY,
                        if (sortingMode == SortingMode.BY_ID_ASC)
                            SortingMode.BY_ID_DESC
                        else
                            SortingMode.BY_ID_ASC
                    )
                })
                dismiss()
            }

            btnDialogSortByName.setOnClickListener {
                parentFragmentManager.setFragmentResult(REQUEST_KEY, Bundle().apply {
                    putSerializable(
                        SORTING_MODE_KEY,
                        if (sortingMode == SortingMode.BY_NAME_ASC)
                            SortingMode.BY_NAME_DESC
                        else
                            SortingMode.BY_NAME_ASC
                    )
                })
                dismiss()
            }
            return root
        }
    }

    private fun setSortingModeActive(button: MaterialButton) {
        val theme = requireActivity().theme

        button.apply {
            setBackgroundColor(resources.getColor(R.color.purple_700, theme))
            setTextColor(resources.getColor(R.color.purple_200, theme))
        }
    }

    companion object {
        const val TAG = "sort"
        const val REQUEST_KEY = "sort_rk"
        const val SORTING_MODE_KEY = "sorting_mode"

        fun newInstance(currentSortingMode: SortingMode): SheetDialogSort {
            val args = Bundle().apply {
                putSerializable(SORTING_MODE_KEY, currentSortingMode)
            }

            return SheetDialogSort().apply {
                arguments = args
            }
        }
    }
}