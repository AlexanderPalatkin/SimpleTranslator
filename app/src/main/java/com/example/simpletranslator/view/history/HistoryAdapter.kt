package com.example.simpletranslator.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletranslator.R
import com.example.simpletranslator.databinding.ActivityMainRecyclerviewItemBinding
import com.example.model.data.DataModel

class HistoryAdapter :
    RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: DataModel) {
            ActivityMainRecyclerviewItemBinding.bind(itemView).apply {
                headerTextviewRecyclerItem.text = data.text
                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context, "on click: ${data.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}