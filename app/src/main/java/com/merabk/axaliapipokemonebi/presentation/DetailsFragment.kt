package com.merabk.axaliapipokemonebi.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.merabk.axaliapipokemonebi.databinding.FragmentDetailsBinding
import com.merabk.axaliapipokemonebi.util.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val args by navArgs<DetailsFragmentArgs>()

    private val viewModel by viewModels<MainPageViewModel>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectDataState()
    }

    @SuppressLint("SetTextI18n")
    private fun collectDataState() {
        collectFlow(viewModel.getPokemonDetails) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                }

                is DataState.Success -> {
                    val data = dataState.data
                    binding.apply {
                        textView.text = data.toString()
                    }

                }

                is DataState.Error -> {
                    val errorMessage = dataState.message
                    Log.d("errorMessage", "collectAllMoviesData: $errorMessage")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        viewModel.getPokemonDetails(args.movieId.lowercase())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}