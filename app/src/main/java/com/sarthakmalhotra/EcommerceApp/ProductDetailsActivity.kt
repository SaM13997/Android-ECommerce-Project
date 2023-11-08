package com.sarthakmalhotra.EcommerceApp

import CartViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sarthakmalhotra.EcommerceApp.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailsBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product: Product = intent.getParcelableExtra("product")!!
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        if (intent.hasExtra("cartData")) {
            val cartData = intent.getParcelableArrayExtra("cartData")
            if (cartData != null) {
                for (item in cartData) {
                    cartViewModel.addToCart(item as Product)
                }
            }
        }
        // Display the product details
        binding.itemDetailsName.text = product.name
        binding.itemDetailsPrice.text = "$${product.price.toString()}"
        binding.itemDetailsDescription.text = product.desc
        val storeRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(product.image)
        Glide.with(this)
            .load(storeRef)
            .into(binding.itemDetailsImage)

        binding.itemDetailsAddToCartButton.setOnClickListener {
            cartViewModel.addToCart(product)
        }

        binding.productDetailHomeBtn.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("cartData", cartViewModel.getCartItems().value?.toTypedArray() ?: emptyArray())
            startActivity(intent)
        }
        binding.productDetailCartBtn.setOnClickListener() {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("cartData", cartViewModel.getCartItems().value?.toTypedArray() ?: emptyArray())
            startActivity(intent)
        }

    }


}