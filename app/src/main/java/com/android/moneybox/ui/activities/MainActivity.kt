package com.android.moneybox.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.moneybox.R
import com.android.moneybox.MoneyBoxApplication
import com.android.moneybox.presentation.MoneyBoxMviViewModel
import javax.inject.Inject

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MoneyBoxMviViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MoneyBoxApplication.application.appComponent.inject(this)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MoneyBoxMviViewModel::class.java)
    }

}
