package com.istanbul.qurio.ui.onboarding.presenter

import com.istanbul.qurio.R
import com.istanbul.qurio.ui.base.BasePresenter
import com.istanbul.qurio.model.OnboardingPage
import com.istanbul.qurio.ui.onboarding.view.OnboardingView

class OnboardingPresenter : BasePresenter<OnboardingView>() {
    private var currentIndex = 0

    private val pages = getOnboardingData()


    fun start() {
        showCurrentPage()
    }

    private fun showCurrentPage() {
        val page = pages[currentIndex]
        view?.showPage(page)
    }

    fun onNextClick() {
        if (currentIndex < pages.size - 1) {
            currentIndex++
            showCurrentPage()
        } else {
            view?.navigateToHome()
        }
    }

    fun onPreviousClick() {
        if (currentIndex > 0) {
            currentIndex--
            showCurrentPage()
        }
    }
}

private fun getOnboardingData(): List<OnboardingPage> {
    return listOf(
        OnboardingPage(
            R.drawable.onboarding1,
           R.string.onboarding_title_1,
            R.string.onboarding_desc_1
        ),
        OnboardingPage(
            R.drawable.onboarding2,
            R.string.onboarding_title_2,
            R.string.onboarding_desc_2
        ),
        OnboardingPage(
            R.drawable.onboarding3,
            R.string.onboarding_title_3,
            R.string.onboarding_desc_3
        ),
        OnboardingPage(
            R.drawable.onboarding4,
            R.string.onboarding_title_4,
            R.string.onboarding_desc_4
        )
    )
}