package com.effective.core.presentation.vacancies_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.effective.core.R
import com.effective.core.domain.model.Vacancy

class VacanciesAdapter :
    RecyclerView.Adapter<VacancyViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Vacancy>() {
        override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean =
            oldItem.title == newItem.title && oldItem.company == newItem.company
    }

    private val differ = AsyncListDiffer(this, callback)

    fun submitList(vacancies: List<Vacancy>) {
        differ.submitList(vacancies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.onBind(item)
    }
}