package com.example.movieappdicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieappdicoding.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import getJsonDataFromAsset
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Movie>()
    private val listSearch = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(v: String?): Boolean {
                onSearch(v)

                binding.searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(v: String?): Boolean {
                onSearch(v)
                return false
            }
        })

        binding.rvMovies.setHasFixedSize(true)
        list.addAll(getAllMovieData())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.about_page -> {
                val intent = Intent(binding.root.context, ProfileActivity::class.java)
                startActivity(intent)
            }
        }

        return false
    }

    private fun getAllMovieData(): ArrayList<Movie> {
        val jsonFileString = getJsonDataFromAsset(applicationContext, "movies.json")
        val gson = Gson()
        val listMovieType = object : TypeToken<ArrayList<Movie>>() {}.type

        return gson.fromJson(jsonFileString, listMovieType)
    }

    private fun showRecyclerList() {
        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        val listMovieAdapter = ListMovieAdapter(list, ::goToMovieDetail)
        binding.rvMovies.adapter = listMovieAdapter
    }

    private fun goToMovieDetail(movie: Movie){
        val moveIntent = Intent(this, DetailMovieActivity::class.java)
        moveIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie)
        startActivity(moveIntent)
    }

    private fun onSearch(query: String?) {
        listSearch.clear()

        if(query == null || query.isEmpty()) {
            val listMovieAdapter = ListMovieAdapter(list, ::goToMovieDetail)
            binding.rvMovies.adapter = listMovieAdapter
            return
        }

        val search = list.filter { movie -> movie.title.contains(query, ignoreCase = true) }

        if(search.isNotEmpty()) {
            listSearch.addAll(search)
            val listMovieAdapter = ListMovieAdapter(listSearch, ::goToMovieDetail)
            binding.rvMovies.adapter = listMovieAdapter
        } else {
            val listMovieAdapter = ListMovieAdapter(list, ::goToMovieDetail)
            binding.rvMovies.adapter = listMovieAdapter
        }

    }
}