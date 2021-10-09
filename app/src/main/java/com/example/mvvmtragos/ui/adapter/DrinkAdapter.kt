package com.example.mvvmtragos.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmtragos.data.model.Drink
import com.example.mvvmtragos.databinding.ItemDrinkBinding
import com.example.mvvmtragos.utils.BaseViewHolder

class DrinkAdapter(
    private val context: Context,
    private val drinksList: MutableList<Drink>,
    private val itemClickLister: OnDrinkClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnDrinkClickListener{
        fun onDrinkClick(drink: Drink, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemDrinkBinding.inflate(LayoutInflater.from(context),parent,false)
        return DrinkViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return drinksList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is DrinkViewHolder -> holder.bind(drinksList[position],position)
        }
    }

    fun removeAt(position: Int){
        drinksList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position,drinksList.size)
        notifyDataSetChanged()
    }

    inner class DrinkViewHolder(val binding: ItemDrinkBinding): BaseViewHolder<Drink>(binding.root){
        override fun bind(item: Drink, position: Int) = with(binding) {
            Glide.with(context).load(item.image).centerCrop().into(ivDrink)
            tvName.text = item.name
            tvDescription.text = item.description
            root.setOnClickListener {
                itemClickLister.onDrinkClick(item,position)
            }
        }
    }
}