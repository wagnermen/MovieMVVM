package com.example.moviesofttek.ui.movie.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.moviesofttek.R
import com.example.moviesofttek.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var username: String = ""
    private var password: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.buttonLogin.setOnClickListener {
            btnLogin()
        }
        return binding.root
    }

    private fun btnLogin() {
        username = binding.editTextUsername.editText?.text.toString()
        password = binding.editTextPassword.editText?.text.toString()
        if (username == "Admin" && password == "Password*123") {
            findNavController().navigate(R.id.action_loginFragment_to_upcomingMovieFragment)
        }else{
            Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

}