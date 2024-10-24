package com.effective.favourites.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.effective.core.presentation.vacancies_recycler.ItemClickListener
import com.effective.core.presentation.vacancies_recycler.ItemDecorationExceptLast
import com.effective.core.presentation.vacancies_recycler.VacanciesAdapter
import com.effective.favourites.R
import com.effective.favourites.databinding.FragmentFavouritesBinding
import com.effective.favourites.presentation.view_model.FavouritesUiState
import com.effective.favourites.presentation.view_model.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var vacanciesAdapter: VacanciesAdapter

    private val favouritesViewModel by viewModels<FavouritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        collectState()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initAdapter() {
        vacanciesAdapter = VacanciesAdapter()
        with(binding.favouritesRV) {
            adapter = vacanciesAdapter
            setHasFixedSize(false)
            val dimen = resources.getDimension(com.effective.core.R.dimen.usual_400)
            addItemDecoration(ItemDecorationExceptLast(bottomOffset = dimen.toInt()))

            addOnItemTouchListener(ItemClickListener(context, this) {
                navigateToDetail()
            })
        }
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                favouritesViewModel.uiState.collectLatest { state ->
                    if (state.errorMessage != null) {
                        showErrorState(state.errorMessage)
                    } else {
                        showWorkingState(state)
                    }
                }
            }
        }
    }

    private fun showWorkingState(state: FavouritesUiState) {
        binding.errorTV.visibility = View.GONE
        binding.titleTV.visibility = View.VISIBLE
        binding.favouritesRV.visibility = View.VISIBLE
        binding.favouriteVacanciesTV.visibility = View.VISIBLE

        if (state.likedVacancies.isNotEmpty()) {
            vacanciesAdapter.submitList(state.likedVacancies)
            binding.favouriteVacanciesTV.text = resources.getQuantityString(
                com.effective.core.R.plurals.vacancies,
                state.likedVacancies.size,
                state.likedVacancies.size
            )
        }
    }

    private fun showErrorState(errorMessage: String) {
        binding.errorTV.text = errorMessage
        binding.errorTV.visibility = View.VISIBLE
        binding.titleTV.visibility = View.GONE
        binding.favouritesRV.visibility = View.GONE
        binding.favouriteVacanciesTV.visibility = View.GONE
    }

    private fun navigateToDetail() {
        findNavController().navigate(R.id.action_favouritesFragment_to_placeholderFragment)
    }
}