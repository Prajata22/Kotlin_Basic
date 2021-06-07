package com.applex.kotlin_basic.features.views.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.applex.kotlin_basic.R
import com.applex.kotlin_basic.application.KotlinBasicApplication
import com.applex.kotlin_basic.databinding.ActivityMainBinding
import com.applex.kotlin_basic.features.di.BooksComponent
import com.applex.kotlin_basic.features.di.DaggerBooksComponent
import com.applex.kotlin_basic.features.viewModel.BooksViewModel
import com.applex.kotlin_basic.features.views.fragments.BooksListFragment
import com.applex.kotlin_basic.utils.CommonUtils
import javax.inject.Inject

/**
 * Created by Prajata on 25 May 2021
 */
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var commonUtils: CommonUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

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

        supportFragmentManager.addOnBackStackChangedListener {
            when (supportFragmentManager.backStackEntryCount) {
                0 -> commonUtils.setToolbar(binding.toolbar, "Kotlin Basic", this)
            }
        }

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, BooksListFragment(), "books_list")
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}