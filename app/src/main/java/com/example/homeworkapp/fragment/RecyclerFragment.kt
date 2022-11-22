package com.example.homeworkapp.fragment

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.activity.MainActivity
import com.example.homeworkapp.databinding.FragmentRecyclerBinding
import com.example.homeworkapp.dialog.SheetDialogSort
import com.example.homeworkapp.recycler.adapter.CitiesAdapter
import com.example.homeworkapp.recycler.data.repository.CitiesRepository
import com.example.homeworkapp.recycler.sort.SortingMode

class RecyclerFragment: Fragment(R.layout.fragment_recycler) {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!

    private var sortingMode = SortingMode.BY_ID_ASC


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecyclerBinding.bind(view)

        (activity as MainActivity).supportActionBar?.title = getString(R.string.cities)

        childFragmentManager.setFragmentResultListener(
            SheetDialogSort.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            onSortDialogResult(bundle)
        }
        with(binding){
            rwCities.adapter = CitiesAdapter(CitiesRepository().getCities())
        }
        setupMenu()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    private fun onSortDialogResult(bundle: Bundle) {
        sortingMode =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(SheetDialogSort.SORTING_MODE_KEY, SortingMode::class.java)
                    ?: SortingMode.BY_ID_ASC
            } else {
                bundle.getSerializable(SheetDialogSort.SORTING_MODE_KEY) as? SortingMode
                    ?: SortingMode.BY_ID_ASC
            }

        val adapter = binding.rwCities.adapter as? CitiesAdapter ?: return
        adapter.reorderList(sortingMode)
    }

    private fun setupMenu() {
        val menuHost = requireActivity() as MenuHost

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.main_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.cities_change_sort -> {
                            SheetDialogSort.newInstance(sortingMode)
                                .show(childFragmentManager, SheetDialogSort.TAG)
                            true
                        }
                        R.id.cities_open_scanner -> {
                            findNavController().navigate(R.id.action_recyclerFragment_to_scannerFragment)
                            true
                        }
                        else -> false
                    }
                }
            }, viewLifecycleOwner
        )
    }
}