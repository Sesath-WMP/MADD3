package com.madd3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.madd3.R
import com.madd3.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // optional: instant validation clearing
        binding.etNumberA.doAfterTextChanged { binding.etNumberA.error = null }
        binding.etNumberB.doAfterTextChanged { binding.etNumberB.error = null }

        binding.btnCalculate.setOnClickListener {
            val aText = binding.etNumberA.text?.toString()?.trim()
            val bText = binding.etNumberB.text?.toString()?.trim()

            if (aText.isNullOrEmpty() || bText.isNullOrEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.msg_invalid_input), Toast.LENGTH_SHORT).show()
                if (aText.isNullOrEmpty()) binding.etNumberA.error = getString(R.string.msg_invalid_input)
                if (bText.isNullOrEmpty()) binding.etNumberB.error = getString(R.string.msg_invalid_input)
                return@setOnClickListener
            }

            val a = aText.toDoubleOrNull()
            val b = bText.toDoubleOrNull()
            if (a == null || b == null) {
                Toast.makeText(requireContext(), getString(R.string.msg_invalid_input), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedId = binding.rgOperation.checkedRadioButtonId
            val opText = view.findViewById<RadioButton>(selectedId)?.text?.toString() ?: "+"

            // Prevent divide by zero when ÷ selected
            if (selectedId == R.id.rbDivide && b == 0.0) {
                Toast.makeText(requireContext(), getString(R.string.msg_divide_by_zero), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val action = InputFragmentDirections.actionInputFragmentToResultFragment(
                numberA = a.toFloat(),
                numberB = b.toFloat(),
                operation = opFromLabel(opText)
            )

            findNavController().navigate(action)
        }
    }

    private fun opFromLabel(label: String): String {
        return when {
            label.contains("+") -> "+"
            label.contains("−") || label.contains("-") -> "-"
            label.contains("×") || label.contains("x", true) -> "*"
            label.contains("÷") || label.contains("/") -> "/"
            else -> "+"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
