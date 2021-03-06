package com.applex.kotlin_basic.features.views.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.applex.kotlin_basic.R
import com.applex.kotlin_basic.databinding.ItemBooksListBinding
import com.applex.kotlin_basic.features.models.BookDetailsModel
import com.applex.kotlin_basic.features.viewModel.BooksViewModel
import com.applex.kotlin_basic.features.views.fragments.BookDetailsFragment

/**
 * Created by Prajata on 25 May 2021
 */
class BooksAdapter(
    private val context: Activity,
    private val list: ArrayList<BookDetailsModel>,
    private val booksViewModel: BooksViewModel
) : RecyclerView.Adapter<BooksAdapter.ProgrammingViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgrammingViewHolder = ProgrammingViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_books_list,
            parent, false
        )
    )

    override fun onBindViewHolder(
        holder: ProgrammingViewHolder,
        position: Int
    ) {
        val currentItem: BookDetailsModel = list[position]
        when {
            !currentItem.book_name.isNullOrEmpty() -> holder.binding.bookName.text =
                currentItem.book_name
            else -> holder.itemView.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            booksViewModel.bookName = currentItem.book_name
            booksViewModel.author = currentItem.author
            booksViewModel.category = currentItem.category
            booksViewModel.publishDate = currentItem.publish_date
            booksViewModel.pageCount = currentItem.page_count

            val fragmentTransaction: FragmentTransaction =
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragmentContainer, BookDetailsFragment(), "book_details")
            fragmentTransaction.addToBackStack("book_details")
            fragmentTransaction.commit()
        }
    }

    override fun getItemCount(): Int = list.size

    class ProgrammingViewHolder(val binding: ItemBooksListBinding) :
        RecyclerView.ViewHolder(binding.root)
}