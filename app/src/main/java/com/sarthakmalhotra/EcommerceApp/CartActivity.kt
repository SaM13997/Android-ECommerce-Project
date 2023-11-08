package com.sarthakmalhotra.EcommerceApp

import CartViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarthakmalhotra.EcommerceApp.Product
import com.sarthakmalhotra.EcommerceApp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var totalPriceTextView: TextView
    private lateinit var totalQuantityTextView: TextView
    private lateinit var checkoutbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        totalQuantityTextView = findViewById(R.id.totalQuantityTextView)
        checkoutbtn = findViewById(R.id.checkoutBtn)

        checkoutbtn.setOnClickListener(){
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("cartData",cartViewModel.getCartItems().value?.toTypedArray())
            startActivity(intent)
        }
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        for (item in intent.getParcelableArrayExtra("cartData")!!) {
            cartViewModel.addToCart(item as Product)
        }
        cartRecyclerView = findViewById(R.id.cartRecyclerView)

        val cartAdapter = CartAdapter(cartViewModel.getCartItems().value ?: emptyList())
        cartRecyclerView.adapter = cartAdapter

        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        updateTotalPriceAndQuantity()


    }
    private fun updateTotalPriceAndQuantity() {
        val cartItems = cartViewModel.getCartItems().value
        if (cartItems != null) {
            val totalQuantity = cartItems.size
            val totalPrice = cartItems.sumOf { it.price }

            totalPriceTextView.text = "$$totalPrice"
            totalQuantityTextView.text = "Total Quantity: $totalQuantity"
        }
    }
}
