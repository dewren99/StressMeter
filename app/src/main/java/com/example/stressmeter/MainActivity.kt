package com.example.stressmeter

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stressmeter.databinding.ActivityMainBinding
import com.example.stressmeter.managers.CsvFileManager
import com.example.stressmeter.managers.MediaPlayerManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private var _renderCount = 0

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CsvFileManager.init(this)
        MediaPlayerManager.init(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_results
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, _, _ ->
            if (_renderCount == 0) {
                _renderCount++
                return@addOnDestinationChangedListener
            }
            MediaPlayerManager.stop()
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            MediaPlayerManager.stop()
            onNavigationItemSelected(menuItem)
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun onNavigationItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> navController.navigate(R.id.nav_home)
            R.id.nav_results -> navController.navigate(R.id.nav_results)
            else -> throw NoSuchFieldException("Cannot find navigation item id: ${menuItem.itemId}")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        MediaPlayerManager.release()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        MediaPlayerManager.stop()
        super.onSaveInstanceState(savedInstanceState)
    }

    companion object {
        const val DEBUG = false
    }
}