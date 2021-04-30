package com.example.shoestoreproject_kotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
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
        // prevent nav gesture if on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, _: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                // TODO: Only show menu on 'list' screen
                Timber.i("Toolbar visibility: %s", binding.toolbar.isVisible.toString())
//                 binding.toolbar.menu.findItem(R.id.loginFragment).isVisible = false
            }
        }

        // Get viewModel
        viewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return NavigationUI.onNavDestinationSelected(item, binding.fragmentNavHost.findNavController())
                || super.onOptionsItemSelected(item)
    }
}