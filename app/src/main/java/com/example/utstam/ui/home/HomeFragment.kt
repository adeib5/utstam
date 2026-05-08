package com.example.utstam.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.utstam.R
import com.example.utstam.databinding.FragmentHomeBinding
import com.example.utstam.model.DataRepository
import com.example.utstam.ui.chat.ChatActivity
import com.example.utstam.ui.education.EducationActivity
import com.example.utstam.ui.report.ReportActivity
import com.example.utstam.MainActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = DataRepository.loggedInUser
        binding.tvGreeting.text = "Halo, ${user?.name ?: "Mahasiswa"} 👋"

        val slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.menuReport.startAnimation(slideUp)
        binding.menuHistory.startAnimation(slideUp)
        binding.menuEducation.startAnimation(slideUp)
        binding.menuChat.startAnimation(slideUp)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.menuReport.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
                startActivity(Intent(requireContext(), ReportActivity::class.java))
            }.start()
        }

        binding.menuHistory.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
                (activity as? MainActivity)?.let { activity ->
                    activity.binding.bottomNavigation.selectedItemId = R.id.navigation_history
                }
            }.start()
        }

        binding.menuEducation.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
                startActivity(Intent(requireContext(), EducationActivity::class.java))
            }.start()
        }

        binding.menuChat.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
                startActivity(Intent(requireContext(), ChatActivity::class.java))
            }.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
