package com.example.homeworkapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.SignInUserForApp.Companion.USER_ID_TAG
import com.example.homeworkapp.databinding.FragmentSettingsBinding
import com.example.homeworkapp.db.DatabaseHandler
import com.example.homeworkapp.db.entity.SettingsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private var binding: FragmentSettingsBinding? = null

    var idUser = -1;


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
        idUser = pref?.getInt(USER_ID_TAG, -1) ?: -1

        lifecycleScope.launch(Dispatchers.IO) {
            val settings = DatabaseHandler.roomDatabase?.getSettingsDao()?.findById(idUser)
            withContext(Dispatchers.Main) {
                binding?.apply {
                    checkboxOne.isChecked = settings?.settings1!!
                    checkboxTwo.isChecked = settings?.settings2!!
                    checkboxThree.isChecked = settings?.settings3!!
                }
            }
        }

        binding?.apply {
            btnSaveSettings.setOnClickListener { val newSettings = SettingsEntity(
                idUser,
                checkboxOne.isChecked,
                checkboxTwo.isChecked,
                checkboxThree.isChecked,
            )
                lifecycleScope.launch(Dispatchers.IO) {
                    DatabaseHandler.roomDatabase?.getSettingsDao()?.updateUserSettings(newSettings)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Settings saved", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        accExit(R.id.action_settingsFragment_to_authorizationFragment)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun accExit(r1: Int){
        val menuHost = requireActivity() as MenuHost

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.exit_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.mainFragment -> {
                            findNavController().navigate(r1)
                            true
                        }
                        else -> false
                    }
                }
            }, viewLifecycleOwner
        )
    }
}