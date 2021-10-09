package com.example.mvvmtragos.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmtragos.R
import com.example.mvvmtragos.data.datasource.DrinkDataSourceImpl
import com.example.mvvmtragos.data.db.AppDatabase
import com.example.mvvmtragos.data.model.Drink
import com.example.mvvmtragos.data.network.Resource
import com.example.mvvmtragos.data.repository.DrinkRepositoryImpl
import com.example.mvvmtragos.databinding.FragmentMainBinding
import com.example.mvvmtragos.ui.adapter.DrinkAdapter
import com.example.mvvmtragos.ui.factory.DrinksFactory
import com.example.mvvmtragos.ui.viewmodel.DrinkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), DrinkAdapter.OnDrinkClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var drinkMainAdapter: DrinkAdapter

    private val drinkViewModel: DrinkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupRecyclerView()
        setupObservers()
        binding.fabFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favoritesFragment)
        }
    }

    private fun setupSearchView(){
        binding.svDrink.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(drinkName: String?): Boolean {
                drinkViewModel.setDrink(drinkName!!)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setupRecyclerView(){
        binding.rvDrinks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDrinks.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
    }

    private fun setupObservers() {
        drinkViewModel.fetchDrinksList.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.pbDrinks.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.pbDrinks.visibility = View.GONE
                    if(result.data != null){
                        drinkMainAdapter = DrinkAdapter(requireContext(), result.data as MutableList<Drink>,this)
                        binding.rvDrinks.adapter = drinkMainAdapter
                    }else{
                        Toast.makeText(requireContext(),"No existe resultado para ${binding.svDrink.query.toString()}",Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Failure -> {
                    binding.pbDrinks.visibility = View.GONE
                    Toast.makeText(requireContext(),"Ocurrio un error ${result.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onDrinkClick(drink: Drink, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("drink",drink)
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment,bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}