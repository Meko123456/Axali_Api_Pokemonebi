package com.merabk.axaliapipokemonebi.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.merabk.axaliapipokemonebi.databinding.FragmentDetailsBinding
import com.merabk.axaliapipokemonebi.util.collectFlow
import com.merabk.axaliapipokemonebi.util.errorExt
import com.merabk.axaliapipokemonebi.util.loadImageWithGlide
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
        setListeners()
    }

    private fun setListeners() = with(binding) {
        tvStateView.errorView?.findViewById<Button>(com.merabk.axaliapipokemonebi.R.id.btn_refresh)
            ?.setOnClickListener {
                viewModel.getPokemonDetails(args.movieId.lowercase())
            }
    }

    private fun collectDataState() = with(binding) {
        collectFlow(viewModel.getPokemonDetails) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    tvStateView.showLoading()
                }

                is DataState.Success -> {
                    val data = dataState.data
                    ivImage.loadImageWithGlide(data.sprites.front_default)
                    toolbar.title = data.name.uppercase()
                    progressHp.text = data.stats[0]
                    progressAttack.text = data.stats[1]
                    progressDefence.text = data.stats[2]
                    progressSpecialAtk.text = data.stats[3]
                    progressSpecialDef.text = data.stats[4]
                    progressSpeed.text = data.stats[5]
                    categoryList.text = data.typeItem.toString()
                    tvStateView.showContent()

                }

                is DataState.Error -> {
                    val errorMessage = dataState.message
                    tvStateView.showError()
                    Log.d("errorMessage", "collectAllMoviesData: $errorMessage")
                    errorExt(errorMessage, binding.tvStateView)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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