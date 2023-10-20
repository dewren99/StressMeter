package com.example.stressmeter.managers

import android.content.Context
import android.media.MediaPlayer
import com.example.stressmeter.R

class MediaPlayerManager {
    companion object {
        private lateinit var _mediaPlayer: MediaPlayer
        fun init(context: Context) {
            _mediaPlayer = MediaPlayer.create(context, R.raw.notif_sound)

            _mediaPlayer.setOnCompletionListener { mp ->
                mp.start()
            }
            _mediaPlayer.start()
        }

        fun release() {
            _mediaPlayer.release()
        }

        fun stop() {
            _mediaPlayer.stop()
        }
    }
}