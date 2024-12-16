package com.ciphero.questa.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ciphero.questa.databinding.ActivityMainBinding
import com.ciphero.questa.model.Offerwall
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.utils.AnimatorManager.setAnimateLoadingInSplash
import com.ciphero.questa.utils.DecoratorNavigationUI
import com.ciphero.questa.utils.DecoratorNavigationUI.observeStartActivityInSplash
import com.ciphero.questa.utils.NetworkCheck
import com.ciphero.questa.utils.PreferencesManager.setStatusOfferwall
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val initOfferWall by lazy { OfferwallRunResponse(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        if (NetworkCheck.checkAccessInternet(this)) {
            setAnimateLoadingInSplash(15000L, this@MainActivity, binding)
            lifecycleScope.launch {
                responseGetBestOffers()
            }
            observeStartActivityInSplash(this)
        } else {
            setAnimateLoadingInSplash(3000L, this@MainActivity, binding)
            lifecycleScope.launch {
                delay(3000L)
                observeStartActivityInSplash(this@MainActivity)
                finish()
            }
        }
    }

    @SuppressLint("BinaryOperationInTimber")
    private fun responseGetBestOffers() {
        lifecycleScope.launch {
            val offers = initOfferWall.getOffers()

            if (offers.isNotEmpty()) {
                handleOffersAvailable(offers)
            } else {
                handleNoOffers()
            }
        }
    }

    private fun handleOffersAvailable(offers: List<Offerwall>) {
        setStatusOfferwall(this, true)
        val intent = Intent(this, MenuActivity::class.java).apply {
            putParcelableArrayListExtra("listBestOffers", ArrayList(offers))
        }
        startActivity(intent)
        Timber.tag("OfferwallRunResponse").d("Parse Response: %s", offers)
        finish()
    }

    private fun handleNoOffers() {
        setStatusOfferwall(this, false)
        lifecycleScope.launch {
            observeStartActivityInSplash(this@MainActivity)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}