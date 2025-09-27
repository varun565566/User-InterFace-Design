package eu.tutorials.registrationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import eu.tutorials.registrationapp.databinding.FragmentConfirmationBinding

class ConfirmationFragment : Fragment() {
    private var _binding: FragmentConfirmationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfirmationBinding.inflate(inflater, container, false)

        val name = arguments?.getString("name") ?: ""
        val email = arguments?.getString("email") ?: ""
        binding.tvWelcome.text = "Welcome, $name!"
        binding.tvEmail.text = "Your email: $email"
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}