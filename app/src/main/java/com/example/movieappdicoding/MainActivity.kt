package com.example.movieappdicoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieappdicoding.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import getJsonDataFromAsset

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
                return false
            }

            override fun onQueryTextChange(v: String?): Boolean {
                onSearch(v)
                return false
            }

        })
        binding.rvMovies.setHasFixedSize(true)

        list.addAll(getAllMovieData())
        print(list)
        showRecyclerList()
    }

    private fun getAllMovieData(): ArrayList<Movie> {
        val jsonFileString = getJsonDataFromAsset(applicationContext, "movies.json")
        val gson = Gson()
        val listMovieType = object : TypeToken<ArrayList<Movie>>() {}.type

        return gson.fromJson(jsonFileString, listMovieType);
    }

    private fun showRecyclerList() {
        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        val listMovieAdapter = ListMovieAdapter(list, ::goToMovieDetail)
        binding.rvMovies.adapter = listMovieAdapter
    }

    private fun goToMovieDetail(movie: Movie){

    }

    private fun onSearch(query: String?) {
        listSearch.clear()

        if(query == null || query.isEmpty()) {
            val listMovieAdapter = ListMovieAdapter(list, ::goToMovieDetail)
            binding.rvMovies.adapter = listMovieAdapter
            return
        }

        val search = list.takeWhile { movie -> movie.title.contains(query) }
        if(search.isNotEmpty()) {
            listSearch.addAll(search)
        }

        val listMovieAdapter = ListMovieAdapter(listSearch, ::goToMovieDetail)
        binding.rvMovies.adapter = listMovieAdapter
    }
}