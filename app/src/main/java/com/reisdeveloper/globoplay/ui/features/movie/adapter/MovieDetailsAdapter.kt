package com.reisdeveloper.globoplay.ui.features.movie.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.reisdeveloper.globoplay.ui.features.movie.details.MovieMoreDetailsFragment
import com.reisdeveloper.globoplay.ui.features.movie.watchtoo.WatchTooFragment

class MovieDetailsAdapter(fragment: Fragment, private val movieId: String) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        if(position == 1) {
            WatchTooFragment.newInstance(movieId)
        } else {
            MovieMoreDetailsFragment.newInstance(movieId)
        }

}