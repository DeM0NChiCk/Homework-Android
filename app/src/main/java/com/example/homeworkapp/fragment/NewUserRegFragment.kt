package com.example.homeworkapp.fragment

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.activity.MainActivity
import com.example.homeworkapp.databinding.FragmentNewUserRegBinding
import com.example.homeworkapp.db.DatabaseHandler
import com.example.homeworkapp.db.entity.SettingsEntity
import com.example.homeworkapp.db.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewUserRegFragment : Fragment(R.layout.fragment_new_user_reg) {

    private var binding: FragmentNewUserRegBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewUserRegBinding.bind(view)
        (requireActivity() as? MainActivity)?.changeBtnNavVisibility(false)

        binding?.apply {
            btnRegNewUser.setOnClickListener {

                val userName = editNewUserName.text.toString()
                val userLogin = editNewUserLogin.text.toString()
                val userPassword = editNewUserPassword.text.toString()
                val userCheckPassword = editNewUserCheckPassword.text.toString()

                if (userLogin.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(context,
                        "Fields cannot be empty!",
                        Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (userLogin.matches(Regex("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"))) {
                    if (userPassword == userCheckPassword) {

                        lifecycleScope.launch(Dispatchers.IO) {
                            val isAddUser = try {
                                DatabaseHandler.roomDatabase?.getUserDao()
                                    ?.addUser(UserEntity(userLogin, userPassword, userName))
                                withContext(Dispatchers.IO) {
                                    val user = DatabaseHandler.roomDatabase?.getUserDao()
                                        ?.findByLoginAndPassword(userLogin, userPassword)
                                    DatabaseHandler.roomDatabase?.getSettingsDao()
                                        ?.addSettingsUser(SettingsEntity(user!!.id,
                                            settings1 = true,
                                            settings2 = true,
                                            settings3 = true))
                                }
                                true
                            } catch (ex: SQLiteConstraintException) {
                                false
                            }

                            withContext(Dispatchers.Main) {
                                if (!isAddUser) {
                                    Toast.makeText(context,
                                        "Login already taken!",
                                        Toast.LENGTH_SHORT)
                                        .show()
                                    return@withContext
                                }

                                findNavController().navigate(R.id.action_newUserRegFragment_to_authorizationFragment)
                                return@withContext
                            }
                        }
                    } else {
                        Toast.makeText(context,
                            "Passwords do not match",
                            Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
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

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
