package com.example.new_medical_application.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.new_medical_application.R
import com.example.new_medical_application.data.local.SharedPreferencesHelper
import com.example.new_medical_application.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.new_medical_application.service.ValueCollectorService
import com.example.new_medical_application.service.ValueGeneratorService

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        startServices()
    }

    override fun onStart() {
        super.onStart()
        clearWelcomeState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupNavigation() {
        initializeNavigationComponents()
        navigationView.setNavigationItemSelectedListener {
            setNavigationListener(it)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        when (item.itemId) {
            R.id.action_login -> {
                showLogoutConfirmationDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val menuItem = menu?.findItem(R.id.action_login)
        menuItem?.isVisible = navController.currentDestination?.id != R.id.loginFragment
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun showNavigationDrawer(navId: Int) {
        binding.navigationView.visibility = View.VISIBLE
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true)
        binding.navigationView.menu.findItem(navId).isChecked = true
    }

    fun hideNavigationDrawer() {
        binding.navigationView.visibility = View.GONE
        supportActionBar?.setDisplayHomeAsUpEnabled(false);
        supportActionBar?.setHomeButtonEnabled(false)
    }

    fun checkItemAfterNavigation(navId: Int) {
        binding.navigationView.menu.findItem(navId).isChecked = true
    }

    private fun startServices() {
        startForegroundService(Intent(this, ValueGeneratorService::class.java))
        startForegroundService(Intent(this, ValueCollectorService::class.java))
    }

    private fun setNavigationListener(item: MenuItem) {
        drawerLayout.open()
        when (item.itemId) {
            R.id.nav_home -> navController.navigate(R.id.mainMenuFragment)
            R.id.nav_values -> navController.navigate(R.id.enterDailyValuesFragment)
            R.id.nav_physiological_data -> navController.navigate(R.id.physiologicalDataFragment)
            R.id.nav_contacts -> navController.navigate(R.id.emergencyContactsFragment)
            R.id.nav_caregiver -> navController.navigate(R.id.caretakerFragment)
            R.id.nav_medical_topics -> navController.navigate(R.id.medicalTopicsFragment)
        }
        item.isChecked = true
        drawerLayout.closeDrawers()
    }

    private fun initializeNavigationComponents() {
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView
        navController =
            (supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment).navController
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        navController.addOnDestinationChangedListener { _, _, _ ->
            invalidateOptionsMenu()
        }
    }

    private fun clearWelcomeState() {
        sharedPreferencesHelper.setWelcomeMessageFlag(false)
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                sharedPreferencesHelper.setRememberMeFlag(false)
                navController.navigate(R.id.loginFragment)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}