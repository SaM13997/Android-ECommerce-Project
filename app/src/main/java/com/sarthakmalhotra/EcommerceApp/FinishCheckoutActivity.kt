package com.sarthakmalhotra.EcommerceApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class FinishCheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_checkout)

        // Waiting for 2 seconds before redirecting to the ProductActivity/the main shopping activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}