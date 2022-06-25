package com.example.movies.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.movies.R

class SplashFragment : Fragment() {

    val viewModel: SplashViewModel by viewModels()

    val navController by lazy { Navigation.findNavController(requireActivity(), R.id.fragment_container_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.entryPoint()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewAction.observe(viewLifecycleOwner) {
            when(it) {
                is SplashViewActions.GoToMoviesScreen -> {
                    val direction = SplashFragmentDirections.actionSplashFragmentToMoviesFragment()
                    navController.navigate(direction)
                }
                is SplashViewActions.GoToUpdateScreen -> {
                    val direction = SplashFragmentDirections.actionSplashFragmentToUpdateFragment()
                    navController.navigate(direction)
                }
            }
        }
    }

}