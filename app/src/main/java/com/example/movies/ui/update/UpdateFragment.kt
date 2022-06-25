package com.example.movies.ui.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.movies.R
import com.example.movies.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    lateinit var binding: FragmentUpdateBinding

    val navController by lazy { Navigation.findNavController(requireActivity(), R.id.fragment_container_view) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            updateBtn.setOnClickListener{
                Toast.makeText(requireContext(), "Приложение ещё не опубликовано", Toast.LENGTH_LONG).show()
            }
            notNowBtn.setOnClickListener{
                val direction = UpdateFragmentDirections.actionUpdateFragmentToMoviesFragment()
                navController.navigate(direction)
            }
        }
    }

}