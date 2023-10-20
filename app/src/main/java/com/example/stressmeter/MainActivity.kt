package com.example.stressmeter

import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.example.stressmeter.databinding.ActivityMainBinding
import com.example.stressmeter.managers.CsvFileManager
import com.example.stressmeter.managers.MediaPlayerManager

class MainActivity : AppCompatActivity() {


    private var _isFirstNavigation = true;

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CsvFileManager.init(this)


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

        MediaPlayerManager.init(applicationContext)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (_isFirstNavigation) {
                _isFirstNavigation = false
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item, navController
        ) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        MediaPlayerManager.release()
    }

    private fun onNavigationItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> navController.navigate(R.id.nav_home)
            R.id.nav_results -> navController.navigate(R.id.nav_results)
            else -> throw NoSuchFieldException("Cannot find navigation item id: ${menuItem.itemId}")
        }
    }
}