package com.applex.kotlin_basic.features.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.applex.kotlin_basic.R
import com.applex.kotlin_basic.application.KotlinBasicApplication
import com.applex.kotlin_basic.databinding.FragmentBookDetailsBinding
import com.applex.kotlin_basic.features.di.BooksComponent
import com.applex.kotlin_basic.features.di.DaggerBooksComponent
import com.applex.kotlin_basic.features.viewModel.BooksViewModel

class BookDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBookDetailsBinding
    private lateinit var booksViewModel: BooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_book_details, container, false)

        booksViewModel = ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)

        val booksComponent: BooksComponent = DaggerBooksComponent.builder()
            .applicationComponent(
                (requireActivity().application as KotlinBasicApplication)
                    .getApplicationComponent()
            )
            .build()

        booksComponent.inject(this)
        booksComponent.inject(booksViewModel)

        requireActivity().actionBar?.setDisplayShowHomeEnabled(true)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        requireActivity().actionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        when {
            booksViewModel.bookName?.isNotEmpty() == true -> binding.bookName.text =
                booksViewModel.bookName
            else -> binding.bookNameLayout.visibility = View.GONE
        }

        when {
            booksViewModel.author?.isNotEmpty() == true -> binding.author.text =
                booksViewModel.author
            else -> binding.authorLayout.visibility = View.GONE
        }

        when {
            booksViewModel.category?.isNotEmpty() == true -> binding.category.text =
                booksViewModel.category
            else -> binding.categoryLayout.visibility = View.GONE
        }

        when {
            booksViewModel.publishDate?.isNotEmpty() == true -> binding.publishDate.text =
                booksViewModel.publishDate
            else -> binding.publishDateLayout.visibility = View.GONE
        }

        when {
            booksViewModel.pageCount != -1 -> binding.pageCount.text =
                booksViewModel.pageCount.toString()
            else -> binding.pageCountLayout.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> requireActivity().supportFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}