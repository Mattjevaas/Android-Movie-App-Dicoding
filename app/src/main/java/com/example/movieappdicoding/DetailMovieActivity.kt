package com.example.movieappdicoding

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.movieappdicoding.databinding.ActivityDetailMovieBinding

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var binding: ActivityDetailMovieBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Movie Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        }

        if(movieData != null) {
            Glide.with(binding.root.context)
                .load(movieData.images.first())
                .centerCrop()
                .placeholder(R.drawable.img_placeholder_svg)
                .into(binding.imageViewMovie)

            binding.textTitle.text = movieData.title
            binding.textReleased.text = "Release On: ${movieData.released}"
            binding.textDirector.text = "Director: ${movieData.director}"
            binding.textGenre.text = "Genre: ${movieData.genre}"
            binding.textPlot.text = movieData.plot
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}