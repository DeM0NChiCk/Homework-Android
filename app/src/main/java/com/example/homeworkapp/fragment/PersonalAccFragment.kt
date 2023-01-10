package com.example.homeworkapp.fragment

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
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
import com.example.homeworkapp.activity.MainActivity
import com.example.homeworkapp.databinding.FragmentPersonalAccountBinding
import com.example.homeworkapp.db.DatabaseHandler
import com.example.homeworkapp.db.entity.UserEntity
import com.example.homeworkapp.db.model.UpdateUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonalAccFragment: Fragment(R.layout.fragment_personal_account) {
    private var binding: FragmentPersonalAccountBinding? = null

    var idUser = -1;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalAccountBinding.bind(view)

        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
        idUser = pref?.getInt(USER_ID_TAG, -1) ?: -1

        if (idUser == -1) {
            findNavController().navigate(R.id.action_personalAccFragment_to_authorizationFragment)
        }

        (requireActivity() as? MainActivity)?.changeBtnNavVisibility(true)

        var user: UserEntity? = null

        lifecycleScope.launch(Dispatchers.IO) {
            user = DatabaseHandler.roomDatabase?.getUserDao()?.findById(idUser)
            binding?.apply {
                tvNameUser.text = getString(R.string.name_person, user!!.userName)
                tvLoginAcc.text = getString(R.string.login_acc, user!!.userLogin)
            }
        }

        binding?.apply {
            btnChange.setOnClickListener {
                changeNewUserLogin(true)
            }
            btnSave.setOnClickListener {
                val newUserLogin = editNewUserLogin.text.toString()

                if (newUserLogin == user?.userLogin) {
                    Toast.makeText(context, "Change your login", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (newUserLogin.matches(Regex("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"))) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        try {
                            DatabaseHandler.roomDatabase?.getUserDao()
                                ?.updateUserLogin(UpdateUserModel(newUserLogin, user!!.id))
                        } catch (ex: SQLiteConstraintException) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context,
                                    "Login already taken!",
                                    Toast.LENGTH_SHORT)
                                    .show()
                            }
                            return@launch
                        }
                        withContext(Dispatchers.Main) {
                            tvLoginAcc.text = getString(R.string.login_acc, newUserLogin)
                            Toast.makeText(context, "Login changed", Toast.LENGTH_SHORT).show()
                            changeNewUserLogin(false)
                        }
                    }
                } else {
                    Toast.makeText(context,
                        "Invalid login",
                        Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
            }
        }
        accExit(R.id.action_personalAccFragment_to_authorizationFragment)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun changeNewUserLogin(isVisible: Boolean){
        binding?.apply {
            textNewUserLogin.visibility = if (isVisible) View.VISIBLE else View.GONE
            btnSave.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
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