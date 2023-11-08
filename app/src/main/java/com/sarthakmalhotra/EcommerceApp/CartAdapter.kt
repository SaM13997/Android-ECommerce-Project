package com.sarthakmalhotra.EcommerceApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CartAdapter(private val cartItems: List<Product>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartProductName: TextView = itemView.findViewById(R.id.cartItemNameTextView)
        val cartProductPrice: TextView = itemView.findViewById(R.id.cartItemPriceTextView)
        val cartProductImage: ImageView = itemView.findViewById(R.id.cartItemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.cartProductName.text = item.name
        holder.cartProductPrice.text = "$${item.price.toString()}"
        val storeRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(item.image)
        Glide.with(holder.itemView).load(storeRef).into(holder.cartProductImage)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}
