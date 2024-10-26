package com.effective.main.presentation.fragment

import android.content.Intent
import android.net.Uri
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
import com.effective.core.presentation.vacancies_recycler.CustomItemDecorationExceptLast
import com.effective.main.presentation.recycler.offer.OffersAdapter
import com.effective.main.presentation.view_model.SearchUiState
import com.effective.main.presentation.view_model.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var vacanciesAdapter: VacanciesAdapter
    private val offersAdapter = OffersAdapter { offer ->
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(offer.link))
        startActivity(intent)
    }

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
        vacanciesAdapter = VacanciesAdapter { vacancy ->
            searchViewModel.pressLike(vacancy)
        }
        with(binding.vacanciesRV) {
            adapter = vacanciesAdapter
            setHasFixedSize(false)
            val dimen = resources.getDimension(com.effective.core.R.dimen.usual_400)
            addItemDecoration(CustomItemDecorationExceptLast(vertical = dimen.toInt()))
//            addOnItemTouchListener(ItemClickListener(context, this) {
//                navigateToDetail()
//            })
        }
        with(binding.recommendationRecycler) {
            adapter = offersAdapter
            setHasFixedSize(false)
            val dimen = resources.getDimension(com.effective.core.R.dimen.usual_200)
            addItemDecoration(CustomItemDecorationExceptLast(horizontal = dimen.toInt()))
        }
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                searchViewModel.uiState.collectLatest { state ->
                    if (state.errorMessage != null) {
                        showErrorState(state.errorMessage)
                    } else {
                        showWorkingState(state)

                        if (state.showAllVacancies) {
                            showSecondFlow(state)
                        } else {
                            showFirstFlow(state)
                        }
                    }
                }
            }
        }
    }

    private fun showWorkingState(state: SearchUiState) {
        binding.errorMessage.visibility = View.GONE
        binding.moreVacanciesTV.visibility = View.VISIBLE
        binding.searchFilterLV.visibility = View.VISIBLE
        binding.filterIB.visibility = View.VISIBLE
        binding.searchInput.visibility = View.VISIBLE
        binding.vacanciesForYou.visibility = View.VISIBLE
        binding.searchFilterLV.visibility = View.VISIBLE

        if (state.offers.isEmpty()) {
            binding.recommendationRecycler.visibility = View.GONE
        } else {
            binding.recommendationRecycler.visibility = View.VISIBLE
            offersAdapter.submitList(state.offers)
        }
    }

    private fun showFirstFlow(state: SearchUiState) {
        binding.onMoreLinearLayout.visibility = View.GONE
        binding.backIB.visibility = View.GONE

        if (state.vacancies.isNotEmpty()) {
            vacanciesAdapter.submitList(state.vacancies.subList(0, 2))
            binding.vacanciesRV.visibility = View.VISIBLE
            binding.moreVacanciesButton.text = resources.getQuantityString(
                R.plurals.more_vacancies,
                state.vacancies.size - 2,
                state.vacancies.size - 2
            )
            binding.moreVacanciesButton.visibility = View.VISIBLE
        }
    }

    private fun showSecondFlow(state: SearchUiState) {
        binding.onMoreLinearLayout.visibility = View.VISIBLE
        binding.backIB.visibility = View.VISIBLE
        binding.moreVacanciesButton.visibility = View.GONE

        if (state.vacancies.isNotEmpty()) {
            binding.moreVacanciesTV.text = resources.getQuantityString(
                com.effective.core.R.plurals.vacancies,
                state.vacancies.size,
                state.vacancies.size
            )
            vacanciesAdapter.submitList(state.vacancies)
            binding.vacanciesRV.invalidateItemDecorations()
            binding.vacanciesRV.visibility = View.VISIBLE
        }
    }

    private fun showErrorState(errorMessage: String) {
        binding.moreVacanciesTV.visibility = View.GONE
        binding.filterIB.visibility = View.GONE
        binding.searchInput.visibility = View.GONE
        binding.moreVacanciesButton.visibility = View.GONE
        binding.vacanciesForYou.visibility = View.GONE
        binding.vacanciesRV.visibility = View.GONE
        binding.searchFilterLV.visibility = View.GONE
        binding.errorMessage.text = errorMessage
        binding.errorMessage.visibility = View.VISIBLE
    }

    private fun navigateToDetail() {
        findNavController().navigate(R.id.action_mainFragment_to_placeholderFragment)
    }
}
