package com.example.mad3zoov2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapterForZoos(private val names: List<String>, private val num: List<Int>) :
    RecyclerView.Adapter<CustomRecyclerAdapterForZoos.MyViewHolder>()
{
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewNum: TextView = itemView.findViewById(R.id.textViewNum)
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
        holder.textViewNum.text = num[position].toString()
    }

    override fun getItemCount() = names.size
}