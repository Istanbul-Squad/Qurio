package com.istanbul.qurio.ui.onboarding.view

import android.R.attr.direction
import android.R.attr.height
import android.R.attr.startColor
import android.R.attr.width
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.istanbul.qurio.databinding.FragmentOnboardingBinding
import com.istanbul.qurio.ui.base.BaseFragment
import com.istanbul.qurio.ui.onboarding.model.OnboardingPage
import com.istanbul.qurio.ui.onboarding.presenter.OnboardingPresenter
import androidx.core.graphics.toColorInt

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate)
    , OnboardingView{

        private lateinit var onboardingPresenter: OnboardingPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSwipeTextColor(
            binding.swipToPlayText,
            "#FFFFFF".toColorInt(),
            "#30ABE8".toColorInt()
        )

        onboardingPresenter = OnboardingPresenter()
        onboardingPresenter.attachView(this)
        onboardingPresenter.start()

        binding.nextPageIcon.setOnClickListener {
            onboardingPresenter.onNextClick()
        }

        binding.previousPageIcon.setOnClickListener {
            onboardingPresenter.onPreviousClick()
        }



    }

    private fun setSwipeTextColor(textView : TextView, startColor : Int, endColor : Int){
        val height = textView.textSize

        val (x0, y0, x1, y1) =  arrayOf(0f,height, 0f, 0f)

        val textShader = LinearGradient(
            x0, y0, x1,y1,
            intArrayOf(startColor, endColor),
            null,
            Shader.TileMode.CLAMP
        )

        binding.swipToPlayText.paint.shader = textShader
        binding.swipToPlayText.invalidate()

        /*
        arrayOf(width, 0f, 0f, 0f) --> right to left
        arrayOf(0f, 0f, width, 0f) --> left to right
        arrayOf(0f, 0f, 0f,height) --> top to bottom
        arrayOf(0f,height, 0f, 0f) --> bottom to top
         */
    }



    override fun showPage(page: OnboardingPage) {
        binding.title.text = page.title
        binding.description.text = page.description
        binding.onboardingImage.setImageResource(page.imageRes)
    }

    override fun navigateToHome() {
        TODO("Not yet implemented")
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun onDestroyView() {
        super.onDestroyView()
        onboardingPresenter.detachView()
    }

}