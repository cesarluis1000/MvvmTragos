package com.example.mvvmtragos.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.util.LogTime
import com.example.mvvmtragos.R
import com.example.mvvmtragos.data.datasource.DrinkDataSourceImpl
import com.example.mvvmtragos.data.db.AppDatabase
import com.example.mvvmtragos.data.model.Drink
import com.example.mvvmtragos.data.model.asDrinkEntity
import com.example.mvvmtragos.data.repository.DrinkRepositoryImpl
import com.example.mvvmtragos.databinding.FragmentDetailBinding
import com.example.mvvmtragos.ui.factory.DrinksFactory
import com.example.mvvmtragos.ui.viewmodel.DrinkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var drink: Drink

    private val drinkViewModel: DrinkViewModel by viewModels()

    companion object {
        private val TAG: String? = DetailFragment::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            drink = it.getParcelable("drink")!!
            Log.d(TAG, drink.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(drink.image).into(binding.ivDrink)
        binding.tvName.text = drink.name
        binding.tvDescription.text = drink.description
        if (drink.hasAlcohol.equals("Non_Alcoholic")){
            binding.tvHasAlcohol.text = "Bebida sin alcohol"
        }else{
            binding.tvHasAlcohol.text = "Bebida con alcohol"
        }

        binding.fabFavorite.setOnClickListener {
            drinkViewModel.saveDrink(drink.asDrinkEntity())
            Toast.makeText(requireContext(),"Se guardo el Cockteal a Favoritos", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}