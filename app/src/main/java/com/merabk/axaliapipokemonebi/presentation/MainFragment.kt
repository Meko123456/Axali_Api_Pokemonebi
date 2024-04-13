package com.merabk.axaliapipokemonebi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.merabk.axaliapipokemonebi.databinding.FragmentMainBinding
import com.merabk.axaliapipokemonebi.util.collectFlow
import com.merabk.axaliapipokemonebi.util.errorExt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow


@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.MovieItemClickListener {

    private val viewModel by viewModels<MainPageViewModel>()
    private var mainAdapter: MainAdapter = MainAdapter(this)

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        collectData()
    }

    private fun setListeners() = with(binding) {
        tvStateView.errorView?.findViewById<Button>(com.merabk.axaliapipokemonebi.R.id.btn_refresh)
            ?.setOnClickListener {
                viewModel.getPokemonList()
            }
    }

    private fun collectData() = with(viewModel) {
        collectAllMoviesData(allPokemonData)
    }

    private fun collectAllMoviesData(allMoviesData: SharedFlow<DataState<List<PokedexListEntry>>>) {
        collectFlow(allMoviesData) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    binding.tvStateView.showLoading()
                }

                is DataState.Success -> {
                    val data = dataState.data
                    mainAdapter.submitList(data)
                    binding.recyclerViewTv.adapter = mainAdapter
                    binding.tvStateView.showContent()
                }

                is DataState.Error -> {
                    val errorMessage = dataState.message
                    binding.tvStateView.showError()
                    errorExt(errorMessage, binding.tvStateView)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieItemClicked(movieId: String) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(movieId)
        findNavController().navigate(action)
    }
}