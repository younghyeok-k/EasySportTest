package com.example.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SportViewPagerAdapter(val itemClicked: (SportModel) -> Unit) :
    ListAdapter<SportModel, SportViewPagerAdapter.ItemViewHolder>(differ) {

    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(sportModel: SportModel) {
            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val priceTextView = view.findViewById<TextView>(R.id.priceTextView)
            val thumbnailImageView = view.findViewById<ImageView>(R.id.thumbnailImageView)

            titleTextView.text = sportModel.title
            priceTextView.text = sportModel.price

            view.setOnClickListener {
                itemClicked(sportModel)
            }

            Glide
                .with(thumbnailImageView.context)
                .load(sportModel.imgUrl)
                .into(thumbnailImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater.inflate(R.layout.item_house_detail_viewpager, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<SportModel>() {
            override fun areItemsTheSame(oldItem: SportModel, newItem: SportModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SportModel, newItem: SportModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}