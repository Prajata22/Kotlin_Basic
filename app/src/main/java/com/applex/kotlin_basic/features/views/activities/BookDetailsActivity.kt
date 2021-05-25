package com.applex.kotlin_basic.features.views.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.applex.kotlin_basic.R
import com.applex.kotlin_basic.application.KotlinBasicApplication
import com.applex.kotlin_basic.databinding.ActivityBookBinding
import com.applex.kotlin_basic.features.di.BooksComponent
import com.applex.kotlin_basic.features.di.DaggerBooksComponent
import com.applex.kotlin_basic.features.viewModel.BooksViewModel

class BookDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityBookBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_book)

        val booksViewModel: BooksViewModel = ViewModelProvider(this).get(BooksViewModel::class.java)

        val booksComponent: BooksComponent = DaggerBooksComponent.builder()
            .applicationComponent(
                (application as KotlinBasicApplication)
                    .getApplicationComponent()
            )
            .build()

        booksComponent.inject(this)
        booksComponent.inject(booksViewModel)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)

        when {
            booksViewModel.bookName.isNotEmpty() -> binding.bookName.text = booksViewModel.bookName
            else -> binding.bookNameLayout.visibility = View.GONE
        }

        when {
            booksViewModel.author.isNotEmpty() -> binding.author.text = booksViewModel.author
            else -> binding.authorLayout.visibility = View.GONE
        }

        when {
            booksViewModel.category.isNotEmpty() -> binding.category.text = booksViewModel.category
            else -> binding.categoryLayout.visibility = View.GONE
        }

        when {
            booksViewModel.publishDate.isNotEmpty() -> binding.publishDate.text =
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
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}