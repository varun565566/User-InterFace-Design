package eu.tutorials.registrationapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.registrationapp.RegistrationFragmentDirections
import com.example.registrationapp.RegistrationViewModel
import com.google.android.material.textfield.TextInputLayout
import eu.tutorials.registrationapp.databinding.FragmentRegistrationBinding
import kotlin.getValue

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe ViewModel data
        viewModel.name.observe(viewLifecycleOwner) { name ->
            if (binding.etName.text.toString() != name) binding.etName.setText(name)
            updateError(binding.tilName, !viewModel.isNameValid.value!! && name.isNotEmpty())
        }
        viewModel.email.observe(viewLifecycleOwner) { email ->
            if (binding.etEmail.text.toString() != email) binding.etEmail.setText(email)
            updateError(binding.tilEmail, !viewModel.isEmailValid.value!! && email.isNotEmpty())
        }
        viewModel.password.observe(viewLifecycleOwner) { password ->
            if (binding.etPassword.text.toString() != password) binding.etPassword.setText(password)
            updateError(
                binding.tilPassword,
                !viewModel.isPasswordValid.value!! && password.isNotEmpty()
            )
        }
        viewModel.isFormValid.observe(viewLifecycleOwner) { isValid ->
            binding.btnRegister.isEnabled = isValid
        }

        // Input listeners for real-time updates
        binding.etName.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateName(s.toString())
            }
        })
        binding.etEmail.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateEmail(s.toString())
            }
        })
        binding.etPassword.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updatePassword(s.toString())
            }
        })

        binding.btnRegister.setOnClickListener {
            if (viewModel.isFormValid.value == true) {
                val userData = viewModel.getUserData()!! // Safe call as form is valid
                val action = RegistrationFragmentDirections.actionRegistrationToConfirmation(
                    userData.first, userData.second
                )
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please fill valid data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun updateError(inputLayout: TextInputLayout, showError: Boolean) {
        if (showError) {
            when (inputLayout) {
                binding.tilName -> inputLayout.error = "Name is required"
                binding.tilEmail -> inputLayout.error = "Please enter a valid email"
                binding.tilPassword -> inputLayout.error = "Password must be at least 6 characters"
            }
        } else {
            inputLayout.error = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Helper for text changes (add this as an inner class or separate file)
abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
}