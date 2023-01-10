package com.example.homeworkapp.fragment

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.SignInUserForApp.Companion.USER_ID_TAG
import com.example.homeworkapp.activity.MainActivity
import com.example.homeworkapp.databinding.FragmentUserAuthBinding
import com.example.homeworkapp.db.DatabaseHandler
import com.example.homeworkapp.db.entity.SettingsEntity
import com.example.homeworkapp.db.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AuthorizationFragment: Fragment(R.layout.fragment_user_auth) {

    private var binding: FragmentUserAuthBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserAuthBinding.bind(view)
        (requireActivity() as? MainActivity)?.changeBtnNavVisibility(false)

        binding?.apply {
//          setTextWatchers()
            btnSingIn.setOnClickListener {
                val userLogin = editAuthLogin.text.toString()
                val userPassword = editAuthPassword.text.toString()

                if (userLogin.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(context,
                        "Fields cannot be empty!",
                        Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                if (userLogin.matches(Regex("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"))) {

                    lifecycleScope.launch(Dispatchers.IO) {
                        val user = DatabaseHandler.roomDatabase?.getUserDao()
                            ?.findByLoginAndPassword(userLogin, userPassword)
                        withContext(Dispatchers.Main) {
                            if (user == null) {
                                Toast.makeText(context,
                                    "Incorrect login or password!",
                                    Toast.LENGTH_SHORT)
                                    .show()
                                return@withContext
                            }
                            val idUser = user.id
                            val pref =
                                activity?.getPreferences(Context.MODE_PRIVATE) ?: return@withContext
                            with(pref.edit()) {
                                putInt(USER_ID_TAG, idUser)
                                commit()
                            }
                            findNavController().navigate(R.id.action_authorizationFragment_to_personalAccFragment)
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

            btnRegNewUser.setOnClickListener {
                findNavController().navigate(R.id.action_authorizationFragment_to_newUserRegFragment)
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}