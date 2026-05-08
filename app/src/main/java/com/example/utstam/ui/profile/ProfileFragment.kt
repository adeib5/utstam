package com.example.utstam.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.utstam.databinding.FragmentProfileBinding
import com.example.utstam.model.DataRepository
import com.example.utstam.ui.login.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()

        binding.switchAnonymous.isChecked = DataRepository.isAnonymousMode(requireContext())
        binding.switchAnonymous.setOnCheckedChangeListener { _, isChecked ->
            DataRepository.setAnonymousMode(requireContext(), isChecked)
            val message = if (isChecked) "Mode Anonim Aktif" else "Mode Anonim Nonaktif"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.btnAbout.setOnClickListener {
            Toast.makeText(requireContext(), "Speak Up v2.1.0\nAplikasi Pelaporan Bullying Kampus", Toast.LENGTH_LONG).show()
        }

        binding.btnLogout.setOnClickListener {
            DataRepository.logout(requireContext())
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun updateUI() {
        val user = DataRepository.loggedInUser
        binding.tvProfileName.text = user?.name ?: "Mahasiswa"
        binding.tvProfileEmail.text = user?.email ?: "email@kampus.id"
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}