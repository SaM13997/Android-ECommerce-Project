package com.sarthakmalhotra.EcommerceApp

import CartViewModel
import ProductAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.sarthakmalhotra.EcommerceApp.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private val productList = ArrayList<Product>()
    private lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().getReference("products")

        val cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        if (intent.hasExtra("cartData")) {
            val cartData = intent.getParcelableArrayExtra("cartData")
            if (cartData != null) {
                for (item in cartData) {
                    cartViewModel.addToCart(item as Product)
                }
            }
        }
        recyclerView = binding.listRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(productList,cartViewModel)
        recyclerView.adapter = adapter
        cartViewModel.getCartItems().observe(this, Observer<List<Product>> {
            Log.i("cartItems", "Cart Items: ${it.size}")
        })
        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
        }
        binding.cartBtn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("cartData",cartViewModel.getCartItems().value?.toTypedArray() ?: emptyArray())
            startActivity(intent)
        }

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                productList.clear()

                for (postSnapshot in dataSnapshot.children) {
                    val product = postSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}
