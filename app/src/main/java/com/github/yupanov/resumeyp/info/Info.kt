package com.github.yupanov.resumeyp.info

import android.content.res.Resources
import com.github.yupanov.resumeyp.R

data class Info(val title: String, val description: String, val icon: String)

class infoBase(val resources: Resources) {
    private var base: List<Info>? = null

    fun getInstance(): List<Info> {
        if (base == null) {
            base = listOf(
                Info(
                    resources.getString(R.string.summary_title),
                    resources.getString(R.string.summary_text),
                    "ic_summary"
                ),
                Info(
                    resources.getString(R.string.skills_title),
                    resources.getString(R.string.skills_text),
                    "ic_skills"
                ),
                Info(
                    resources.getString(R.string.education_title),
                    resources.getString(R.string.education_text),
                    "ic_education"
                ),
                Info(
                    resources.getString(R.string.experience_title),
                    resources.getString(R.string.experience_text),
                    "ic_experience"
                ),
                Info(
                    resources.getString(R.string.hobby_title),
                    resources.getString(R.string.hobby_text),
                    "ic_hobby"
                )
            )
        }
        return base!!
    }
}

