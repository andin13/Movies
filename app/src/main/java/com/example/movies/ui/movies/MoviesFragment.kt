package com.example.movies.ui.movies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.movies.appComponent
import com.example.movies.data.entities.Movie
import com.example.movies.data.services.NetworkService
import com.example.movies.databinding.FragmentMoviesBinding
import com.example.movies.ui.movies.itemMovie.MoviesAdapter
import com.example.movies.utils.hideKeyboard
import kotlinx.coroutines.flow.collectLatest
import okhttp3.OkHttpClient
import javax.inject.Inject


class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    @Inject
    lateinit var viewModelFactory:ViewModelProvider.Factory

    private val viewModel: MoviesViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel.onCreate()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

//        viewModel.isLoading.observe(viewLifecycleOwner){
//            binding.progressBar.isVisible = it
//        }

        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest {
                (binding.recyclerView.adapter as MoviesAdapter).submitData(it)
            }
        }


//        viewModel.isEmpty.observe(viewLifecycleOwner) {
//            binding.recyclerView.isVisible = !it
//            binding.nullResultAlert.isVisible = it
//        }

        viewModel.viewAction.observe(viewLifecycleOwner) {
            it.invoke {
                when(it){
                    is MoviesViewActions.ShowToast -> showToast(it.message)
                    is MoviesViewActions.ToMovieScreen -> toMovieScreen(it.movie)
                    is MoviesViewActions.HideKeyboard -> hideKeyboard()
                }
            }
        }

    }

    private fun initView(){
        with(binding){
            recyclerView.adapter = MoviesAdapter { viewModel.clickItem(it) }
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    //viewModel.setBySearch(p0?:"")
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    viewModel.setBySearch(p0?:"")
                    return true
                }

            })


            typeCheckbox.setOnCheckedChangeListener { compoundButton, b ->
                viewModel.setType(b)
            }
            orderCheckbox.setOnCheckedChangeListener { compoundButton, b ->
                viewModel.setOrder(b)
            }
        }
    }

    private fun toMovieScreen(movie: Movie){
        /*val fragment = MovieFragment.newInstance(movie)
        parentFragmentManager
            .beginTransaction()
            .addToBackStack("tag")
            .replace(R.id.fragment_container_view, fragment)
            .commit()*/
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieFragment(movie)
        findNavController().navigate(action)
    }


    private fun showToast(toastText: String) {
        Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
    }

}