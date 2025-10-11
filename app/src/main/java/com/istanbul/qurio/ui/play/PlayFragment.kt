package com.istanbul.qurio.ui.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.istanbul.qurio.QurioApplication
import com.istanbul.qurio.databinding.FragmentPlayBinding
import com.istanbul.qurio.ui.base.BaseFragment

class PlayFragment: BaseFragment<FragmentPlayBinding>(FragmentPlayBinding::inflate), PlayView {
    private lateinit var playPresenter: PlayPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as QurioApplication).appComponent.inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playPresenter = PlayPresenter(this)
        playPresenter.getCoins()

    }

    override fun updateCoinsNumber(number: Int) {
        binding.textNumberOfCoins.text = number.toString()
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }
}