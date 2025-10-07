package com.istanbul.qurio.ui.onboarding.presenter

import com.istanbul.qurio.R
import com.istanbul.qurio.ui.base.BasePresenter
import com.istanbul.qurio.ui.onboarding.model.OnboardingPage
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
            "Welcome to Qurio",
            "Welcome to the world of Qurio, where questions spark curiosity and prizes await the smartest. Ready to begin the challenge?"
        ),
        OnboardingPage(
            R.drawable.onboarding2,
            "Choose your character",
            "Each hero has their own unique style!\n" +
                    "Choose from unique characters and start your adventure in your own style."
        ),
        OnboardingPage(
            R.drawable.onboarding3,
            "Challenge and win",
            "Answer quickly, earn points, and share with your friends!\n" +
                    "Each trivia category is a new experience."
        ),
        OnboardingPage(
            R.drawable.onboarding4,
            "Collect them all!",
            "Unlock characters, earn badges, and climb the leaderboards. Qurio is merciless, but you can handle it."
        )
    )
}