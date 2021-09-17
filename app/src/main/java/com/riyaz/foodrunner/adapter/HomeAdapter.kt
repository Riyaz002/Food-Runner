package com.riyaz.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riyaz.foodrunner.R
import com.riyaz.foodrunner.module.restaurant

class HomeAdapter( var context: Context, var list: ArrayList<restaurant>): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val name = view.findViewById<TextView>(R.id.txtNameInHome)
        val price = view.findViewById<TextView>(R.id.txtPriceInHome)
        val isFavourite = view.findViewById<ImageButton>(R.id.btnFavouriteInHome)
        val rating = view.findViewById<TextView>(R.id.txtRatingInHome)
        val image = view.findViewById<ImageView>(R.id.imgRestaurantImageInHome)
        val row = view.findViewById<RelativeLayout>(R.id.recyclerRow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_acitvity_recycler_view_single_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return list.size
    }
}