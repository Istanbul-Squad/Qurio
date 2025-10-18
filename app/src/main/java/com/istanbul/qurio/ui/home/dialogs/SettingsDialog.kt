package com.istanbul.qurio.ui.home.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import com.istanbul.qurio.databinding.DialogSettingsBinding

class SettingsDialog(
    context: Context,
    private val initialMusicVolume: Int,
    private val initialSoundVolume: Int,
    private val onSave: (Int, Int) -> Unit,
    private val onDiscard: () -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogSettingsBinding

    private var soundLevel = initialSoundVolume
    private var musicLevel = initialMusicVolume

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        setupUI()
    }

    private fun setupUI() = with(binding) {

        customSoundSeekbar.progress = soundLevel
        customMusicSeekbar.progress = musicLevel

        customSoundSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) soundLevel = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        customMusicSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) musicLevel = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnSoundPlus.setOnClickListener {
            soundLevel = (soundLevel + 10).coerceAtMost(100)
            customSoundSeekbar.progress = soundLevel
        }

        btnSoundMinus.setOnClickListener {
            soundLevel = (soundLevel - 10).coerceAtLeast(0)
            customSoundSeekbar.progress = soundLevel
        }

        btnMusicPlus.setOnClickListener {
            musicLevel = (musicLevel + 10).coerceAtMost(100)
            customMusicSeekbar.progress = musicLevel
        }

        btnMusicMinus.setOnClickListener {
            musicLevel = (musicLevel - 10).coerceAtLeast(0)
            customMusicSeekbar.progress = musicLevel
        }

        btnSaveChanges.setOnClickListener {
            onSave(soundLevel, musicLevel)
            dismiss()
        }

        btnDiscard.setOnClickListener {
            onDiscard()
            dismiss()
        }

        closeShape.setOnClickListener { dismiss() }
    }
}