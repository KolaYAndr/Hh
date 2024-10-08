package com.effective.hh

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.effective.hh.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val favouritesViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setBottomNav()
    }

    private fun setBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.mainBNV, navController)
        with(binding.mainBNV) {
            labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
            setBackgroundColor(
                ContextCompat.getColor(
                    context, com.effective.core.R.color.basic_black
                )
            )

            itemActiveIndicatorColor = null
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.search_screen -> {
                        navController.navigate(com.effective.main.R.id.main_navigation)
                    }

                    R.id.favourites_screen -> {
                        navController.navigate(com.effective.favourites.R.id.favourites_navigation)
                    }

                    else -> {
                        val request =
                            NavDeepLinkRequest.Builder.fromUri("android-app://com.effective.placeholder/PlaceholderFragment".toUri())
                                .build()
                        navController.navigate(request)
                    }
                }
                return@setOnItemSelectedListener true
            }

            lifecycleScope.launch {
                favouritesViewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collectLatest { state ->
                        val size = state.likedVacancies
                        with(binding.mainBNV.getOrCreateBadge(R.id.favourites_screen)) {
                            backgroundColor = ContextCompat.getColor(
                                context,
                                com.effective.core.R.color.special_red
                            )
                            badgeTextColor = ContextCompat.getColor(context, R.color.white)
                            if (size > 0) {
                                isVisible = true
                                number = size
                            } else {
                                isVisible = false
                            }
                        }
                    }
            }
        }
    }
}