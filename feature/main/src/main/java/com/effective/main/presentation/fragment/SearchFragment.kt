package com.effective.main.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.effective.core.presentation.vacancies_recycler.VacanciesAdapter
import com.effective.main.R
import com.effective.main.databinding.FragmentMainBinding
import com.effective.core.presentation.vacancies_recycler.ItemClickListener
import com.effective.core.presentation.vacancies_recycler.ItemDecorationExceptLast
import com.effective.main.presentation.recycler.offer.OffersAdapter
import com.effective.main.presentation.view_model.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var vacanciesAdapter: VacanciesAdapter
    private val offersAdapter = OffersAdapter()

    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpButtons()
        initAdapters()
        collectUiState()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpButtons() {
        binding.moreVacanciesButton.setOnClickListener {
            searchViewModel.showMore()
        }
        binding.backIB.setOnClickListener {
            searchViewModel.showLess()
        }
    }

    private fun initAdapters() {
        vacanciesAdapter = VacanciesAdapter()
        with(binding.vacanciesRV) {
            adapter = vacanciesAdapter
            setHasFixedSize(false)
            val dimen = resources.getDimension(com.effective.core.R.dimen.usual_400)
            addItemDecoration(ItemDecorationExceptLast(bottomOffset = dimen.toInt()))

            addOnItemTouchListener(ItemClickListener(context, this) {
                navigateToDetail()
            })
        }
        with(binding.recommendationRecycler) {
            adapter = offersAdapter
            setHasFixedSize(false)
            val dimen = resources.getDimension(com.effective.core.R.dimen.usual_200)
            addItemDecoration(ItemDecorationExceptLast(rightOffset = dimen.toInt()))
        }
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                searchViewModel.uiState.collectLatest { state ->
                    if (state.offers.isEmpty()) {
                        binding.recommendationRecycler.visibility = View.GONE
                    } else {
                        binding.recommendationRecycler.visibility = View.VISIBLE
                        offersAdapter.submitList(state.offers)
                    }

                    if (state.showAllVacancies) {
                        binding.onMoreLinearLayout.visibility = View.VISIBLE
                        binding.moreVacanciesTV.text = resources.getQuantityString(
                            com.effective.core.R.plurals.vacancies,
                            state.vacancies.size,
                            state.vacancies.size
                        )
                        binding.moreVacanciesButton.visibility = View.GONE
                        binding.backIB.visibility = View.VISIBLE

                        if (state.vacancies.isNotEmpty()) {
                            vacanciesAdapter.submitList(state.vacancies)
                        }
                    } else {
                        binding.moreVacanciesButton.text = resources.getQuantityString(
                            R.plurals.more_vacancies,
                            state.vacancies.size - 2,
                            state.vacancies.size - 2
                        )
                        binding.onMoreLinearLayout.visibility = View.GONE
                        binding.backIB.visibility = View.GONE

                        if (state.vacancies.isNotEmpty()) {
                            vacanciesAdapter.submitList(state.vacancies.subList(0, 2))
                            binding.moreVacanciesButton.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetail() {
        findNavController().navigate(R.id.action_mainFragment_to_placeholderFragment)
    }
}