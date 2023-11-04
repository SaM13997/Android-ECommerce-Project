package com.sarthakmalhotra.chatapp

import ProductAdapter
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ListFragment: Fragment(R.layout.fragment_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private val db = FirebaseFirestore.getInstance()
    private val productsCollection = db.collection("products")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = ProductAdapter()
        recyclerView.adapter = adapter

        loadProductsFromFirestore()

        return view
    }

    private fun loadProductsFromFirestore() {
        productsCollection.orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val products = ArrayList<Product>()

                for (document in querySnapshot.documents) {
                    val product = document.toObject(Product::class.java)
                    product?.let { products.add(it) }
                }

                adapter.submitList(products)
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }
}