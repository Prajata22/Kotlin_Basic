package com.applex.kotlin_basic.features.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.applex.kotlin_basic.R
import com.applex.kotlin_basic.application.KotlinBasicApplication
import com.applex.kotlin_basic.databinding.ActivityMainBinding
import com.applex.kotlin_basic.features.di.BooksComponent
import com.applex.kotlin_basic.features.di.DaggerBooksComponent
import com.applex.kotlin_basic.features.models.BookDetailsModel
import com.applex.kotlin_basic.features.viewModel.BooksViewModel
import com.applex.kotlin_basic.utils.CommonUtils

import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var commonUtils: CommonUtils

    private val booksModelList : ArrayList<BookDetailsModel> = ArrayList()
    private lateinit var booksViewModel : BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        booksViewModel = ViewModelProvider(this).get(BooksViewModel::class.java)

        val booksComponent : BooksComponent = DaggerBooksComponent.builder()
            .applicationComponent(
                (application as KotlinBasicApplication)
                    .getApplicationComponent()
            )
            .build()

        booksComponent.inject(this)
        booksComponent.inject(booksViewModel)

        setSupportActionBar(binding.toolbar)

        commonUtils.settingImage(R.drawable.no_data, binding.noData)
        commonUtils.settingImage(R.drawable.error, binding.error)

        Handler(Looper.getMainLooper()).postDelayed({ buildRecyclerView() }, 1000)

        binding.swipeRefresh
            .setColorSchemeColors(
                ContextCompat.getColor(this, R.color.purple_500),
                ContextCompat.getColor(this, R.color.purple_700)
            )

        binding.swipeRefresh.setOnRefreshListener {
            booksModelList.clear()
            Handler(Looper.getMainLooper()).postDelayed({ buildRecyclerView() }, 1000)
        }
    }

    private fun buildRecyclerView() {
        booksViewModel.getBooksList().observe(this, object)
    }
}