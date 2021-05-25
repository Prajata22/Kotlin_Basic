package com.applex.kotlin_basic.features.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.applex.kotlin_basic.R
import com.applex.kotlin_basic.databinding.LayoutItemsBinding
import com.applex.kotlin_basic.features.models.BookDetailsModel
import com.applex.kotlin_basic.features.views.activities.BookDetailsActivity

class BooksAdapter(
    private val context: Context,
    private val list: ArrayList<BookDetailsModel>
) : RecyclerView.Adapter<BooksAdapter.ProgrammingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammingViewHolder =
        ProgrammingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_items,
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ProgrammingViewHolder, position: Int) {
        val currentItem: BookDetailsModel = list[position]
        when {
            currentItem.book_name?.isNotEmpty() == true -> holder.binding.bookName.text =
                currentItem.book_name
            else -> holder.itemView.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, BookDetailsActivity::class.java))
        }
    }

    override fun getItemCount(): Int = list.size

    class ProgrammingViewHolder(val binding: LayoutItemsBinding) :
        RecyclerView.ViewHolder(binding.root)
}