package com.example.mvvmtragos.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmtragos.data.datasource.DrinkDataSourceImpl
import com.example.mvvmtragos.data.db.AppDatabase
import com.example.mvvmtragos.data.db.DrinksDao
import com.example.mvvmtragos.data.model.Drink
import com.example.mvvmtragos.data.model.asDrinkEntity
import com.example.mvvmtragos.data.model.asDrinkList
import com.example.mvvmtragos.data.network.Resource
import com.example.mvvmtragos.data.repository.DrinkRepositoryImpl
import com.example.mvvmtragos.databinding.FragmentFavoritesBinding
import com.example.mvvmtragos.ui.adapter.DrinkAdapter
import com.example.mvvmtragos.ui.factory.DrinksFactory
import com.example.mvvmtragos.ui.viewmodel.DrinkViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment(), DrinkAdapter.OnDrinkClickListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var drinkFavoritesAdapter: DrinkAdapter

    private val drinkViewModel: DrinkViewModel by viewModels()

    companion object {
        private val TAG: String? = FavoritesFragment::class.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.rvDrinksFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDrinksFavorites.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
    }

    private fun setupObservers() {
        drinkViewModel.getTragosFavoritos().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if(result.data != null){
                        drinkFavoritesAdapter = DrinkAdapter(requireContext(), result.data.asDrinkList() as MutableList<Drink>,this)
                        binding.rvDrinksFavorites.adapter = drinkFavoritesAdapter
                    }else{
                        Toast.makeText(requireContext(),"No existe favoritos",
                            Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Failure -> {

                }
            }
        })
    }

    override fun onDrinkClick(drink: Drink, position:Int) {
        Log.d(TAG, "position: $position")
        Log.d(TAG, "drinkFavorites: ${drink.asDrinkEntity()}")
        drinkViewModel.deleteDrink(drink.asDrinkEntity())
        drinkFavoritesAdapter.removeAt(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}