package com.example.mad3zoov2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapterForAnimals(private val names: List<String>,
                                      private val highlighted: List<Int>):
    RecyclerView.Adapter<CustomRecyclerAdapterForAnimals.MyViewHolder>()
{
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val layoutItem: ConstraintLayout = itemView.findViewById(R.id.layoutItem)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewNumTitle: TextView = itemView.findViewById(R.id.textViewNumTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.textViewName.text = names[position]
        holder.textViewNumTitle.visibility = View.INVISIBLE
        for (i in highlighted)
        {
            if (position == i)
            {
                holder.layoutItem.setBackgroundColor(Color.GRAY)
            }
        }
    }

    override fun getItemCount() = names.size
}