package com.example.shoestoreproject_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.shoestoreproject_kotlin.databinding.ActivityMainBinding
import com.example.shoestoreproject_kotlin.shoelist.ShoeViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: ShoeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        Timber.plant(Timber.DebugTree())

//        val navController = findNavController(R.id.fragment_navHost) // Use when outside onCreate
        // Use when inside onCreate
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_navHost) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        // Get viewModel
        viewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)
    }
}