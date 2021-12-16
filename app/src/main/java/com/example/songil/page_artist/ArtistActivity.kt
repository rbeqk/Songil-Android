package com.example.songil.page_artist

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.ArtistActivityBinding

class ArtistActivity : BaseActivity<ArtistActivityBinding>(R.layout.artist_activity) {

    private val artistViewModel : ArtistViewModel by lazy { ViewModelProvider(this)[ArtistViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = artistViewModel
        binding.lifecycleOwner = this
    }
}