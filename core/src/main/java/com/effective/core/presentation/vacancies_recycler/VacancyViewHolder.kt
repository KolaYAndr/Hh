package com.effective.core.presentation.vacancies_recycler

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.effective.core.R
import com.effective.core.domain.model.Vacancy


class VacancyViewHolder(view: View, onLikeClick: (Int) -> Unit, onNavigate: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val resources = view.resources
    private val lookingNowTV: TextView = view.findViewById(R.id.vacancyLookingNow)
    private val likeButton: ImageButton = view.findViewById(R.id.likeImageButton)
    private val titleTV: TextView = view.findViewById(R.id.vacancyTitle)
    private val cityTV: TextView = view.findViewById(R.id.vacancyCity)
    private val companyNameTV: TextView = view.findViewById(R.id.vacancyCompanyName)
    private val salaryTV: TextView = view.findViewById(R.id.vacancySalary)
    private val experienceTV: TextView = view.findViewById(R.id.vacancyExperience)
    private val postedAtTV: TextView = view.findViewById(R.id.vacancyPostedAt)

    init {
        likeButton.setOnClickListener { onLikeClick(adapterPosition) }
        itemView.setOnClickListener { onNavigate(adapterPosition) }
    }

    fun onBind(vacancy: Vacancy) {
        //проставляются ненулевые поля
        titleTV.text = vacancy.title
        cityTV.text = vacancy.address.town
        companyNameTV.text = vacancy.company
        experienceTV.text = vacancy.experience.previewText
        postedAtTV.text = resources.getString(
            R.string.pusblished_at,
            vacancy.publishedDate
        )

        //проставляются нулевые или вариантивные поля с проверкой
        if (vacancy.lookingNumber != null) {
            lookingNowTV.text = resources.getQuantityString(
                R.plurals.people_looking_now,
                vacancy.lookingNumber,
                vacancy.lookingNumber
            )
            lookingNowTV.visibility = View.VISIBLE
        } else {
            lookingNowTV.visibility = View.GONE
        }

        if (vacancy.isFavorite) {
            likeButton.setImageResource(R.drawable.like_filled)
        } else likeButton.setImageResource(R.drawable.like_outlined)

        if (vacancy.salary.short != null) {
            salaryTV.text = vacancy.salary.short
            salaryTV.visibility = View.VISIBLE
        } else {
            salaryTV.visibility = View.GONE
        }
    }
}
