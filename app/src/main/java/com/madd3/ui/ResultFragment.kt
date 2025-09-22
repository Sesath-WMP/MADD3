package com.madd3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.madd3.databinding.FragmentResultBinding
import java.text.DecimalFormat
import kotlin.math.round

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val a = args.numberA.toDouble()
        val b = args.numberB.toDouble()
        val op = args.operation


        val result = when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> a + b
        }

        // Format up to 3 decimals (no trailing zeros beyond needed)
        val df = DecimalFormat("0.###")
        val resultText = df.format(result)

        val opSymbol = when (op) {
            "+" -> "+"
            "-" -> "−"
            "*" -> "×"
            "/" -> "÷"
            else -> "+"
        }

        binding.tvExpression.text = "${df.format(a)} $opSymbol ${df.format(b)} ="
        binding.tvResult.text = resultText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
