package com.example.stressmeter.managers

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.example.stressmeter.R

@Suppress("DEPRECATION")
class MediaPlayerManager {
    companion object {
        private lateinit var _mediaPlayer: MediaPlayer
        private var _vibrator: VibratorManager? = null
        private var _vibratorDeprecated: Vibrator? = null

        /**
         * @source: https://stackoverflow.com/a/73454580/5895675
         * Taken inspiration to handle SDK deprecations
         */
        private fun initVibrator(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                _vibrator =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                _vibrator!!.defaultVibrator.vibrate(
                    VibrationEffect.createWaveform(longArrayOf(500, 500, 500), 1)
                )
                return
            }
            _vibratorDeprecated = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                _vibratorDeprecated!!.vibrate(VibrationEffect.createWaveform(longArrayOf(500), 0))
                return
            }
            _vibratorDeprecated!!.vibrate(500L)
        }

        private fun stopVibrator() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                _vibrator?.defaultVibrator?.cancel()
                _vibrator = null
                return
            }
            _vibratorDeprecated?.cancel()
            _vibratorDeprecated = null
        }

        fun init(context: Context) {
            initVibrator(context)

            _mediaPlayer = MediaPlayer.create(context, R.raw.notif_sound)
            _mediaPlayer.setOnCompletionListener { it.start() }
            _mediaPlayer.start()
        }

        fun release() {
            _mediaPlayer.release()
        }

        fun stop() {
            stopVibrator()
            _mediaPlayer.stop()
        }
    }
}