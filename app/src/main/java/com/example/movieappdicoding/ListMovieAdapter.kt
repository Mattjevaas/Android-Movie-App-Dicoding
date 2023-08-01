package com.example.movieappdicoding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieappdicoding.databinding.ItemRowMovieBinding

class ListMovieAdapter(private val listMovie: ArrayList<Movie>, private val onClick: (Movie) -> Unit) : RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>() {

    inner class ListViewHolder(var binding: ItemRowMovieBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bind(movie: Movie) {
            itemView.setOnClickListener {
                onClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListMovieAdapter.ListViewHolder {
        val binding = ItemRowMovieBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListMovieAdapter.ListViewHolder, position: Int) {
        val movieData = listMovie[position]
        print(movieData)

        Glide.with(holder.itemView.context)
            .load(movieData.images.first())
            .centerCrop()
            .placeholder(R.drawable.img_placeholder_svg)
            .into(holder.binding.imgItemPhoto)
        holder.binding.itemName.text = movieData.title
        holder.binding.itemDescription.text = movieData.plot

        holder.bind(movieData)
    }

    override fun getItemCount() = listMovie.size


}