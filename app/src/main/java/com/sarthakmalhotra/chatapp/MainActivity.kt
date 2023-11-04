package com.sarthakmalhotra.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sarthakmalhotra.chatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(ListFragment())

        binding.accountBtn.setOnClickListener(){
            replaceFragment(AccountFragment())
        }
        binding.cartBtn.setOnClickListener(){
            replaceFragment(CartFragment())
        }
        binding.homeBtn.setOnClickListener(){
            replaceFragment(ListFragment())
        }
    }
    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }
}