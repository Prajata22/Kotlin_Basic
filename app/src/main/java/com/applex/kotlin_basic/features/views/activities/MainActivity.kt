package com.applex.kotlin_basic.features.views.activities

import android.os.Bundle
import android.view.Menu

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider

import com.applex.kotlin_basic.R
import com.applex.kotlin_basic.application.KotlinBasicApplication
import com.applex.kotlin_basic.features.di.BooksComponent
import com.applex.kotlin_basic.features.di.DaggerBooksComponent
import com.applex.kotlin_basic.features.viewModel.BooksViewModel
import com.applex.kotlin_basic.features.views.fragments.BooksListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val booksViewModel: BooksViewModel = ViewModelProvider(this).get(BooksViewModel::class.java)

        val booksComponent: BooksComponent = DaggerBooksComponent.builder()
            .applicationComponent(
                (application as KotlinBasicApplication)
                    .getApplicationComponent()
            )
            .build()

        booksComponent.inject(this)
        booksComponent.inject(booksViewModel)

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, BooksListFragment(), "books_list")
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}