package com.sarthakmalhotra.EcommerceApp


import CartViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class CheckoutActivity : AppCompatActivity() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var payNowButton: Button
    private lateinit var cardNumberEditText: EditText
    private lateinit var cardHolderEditText: EditText
    private lateinit var expiryDateEditText: EditText
    private lateinit var cvvEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var contactNumberEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        cardNumberEditText = findViewById(R.id.cardNumber)
        cardHolderEditText = findViewById(R.id.cardHolder)
        expiryDateEditText = findViewById(R.id.expiryDate)
        cvvEditText = findViewById(R.id.cvv)
        nameEditText = findViewById(R.id.name)
        addressEditText = findViewById(R.id.address)
        contactNumberEditText = findViewById(R.id.contactNumber)

        payNowButton = findViewById(R.id.payNowButton)

        if (intent.hasExtra("cartData")) {
            val cartData = intent.getParcelableArrayExtra("cartData")
            if (cartData != null) {
                for (item in cartData) {
                    cartViewModel.addToCart(item as Product)
                }
            }
        }

        updateTotalPrice()

        payNowButton.setOnClickListener {
            if (validateInput()) {
                // If all validations pass, proceed with the payment
                cartViewModel.clearCart()
                val intent = Intent(this, FinishCheckoutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateTotalPrice() {
        val cartItems = cartViewModel.getCartItems().value
        if (cartItems != null) {
            val totalPrice = cartItems.sumOf { it.price }
            payNowButton.text = "Pay $$totalPrice"
        }
    }

    private fun validateInput(): Boolean {
        // Card Number Validation: Check for a valid 12-digit card number
        val cardNumber = cardNumberEditText.text.toString()
        if (cardNumber.length != 12 || !cardNumber.matches(Regex("\\d{12}"))) {
            cardNumberEditText.error = "Enter a valid 12-digit card number"
            return false
        }

        // Card Holder's Name Validation: Check if it's not empty
        val cardHolder = cardHolderEditText.text.toString()
        if (cardHolder.isBlank()) {
            cardHolderEditText.error = "Card holder's name is required"
            return false
        }

        // Expiry Date Validation: Check for a valid MM/YY format
        val expiryDate = expiryDateEditText.text.toString()
        if (!expiryDate.matches(Regex("^\\d{2}\\d{2}$"))) {
            expiryDateEditText.error = "Enter a valid expiry date in MM/YY format"
            return false
        }

        // CVV Validation: Check for a valid 3-digit CVV
        val cvv = cvvEditText.text.toString()
        if (cvv.length != 3 || !cvv.matches(Regex("\\d{3}"))) {
            cvvEditText.error = "Enter a valid 3-digit CVV"
            return false
        }

        // Name Validation: Check if it's not empty
        val name = nameEditText.text.toString()
        if (name.isBlank()) {
            nameEditText.error = "Name is required"
            return false
        }

        // Address Validation: Check if it's not empty
        val address = addressEditText.text.toString()
        if (address.isBlank()) {
            addressEditText.error = "Address is required"
            return false
        }

        // Contact Number Validation: Check for a valid phone number format
        val contactNumber = contactNumberEditText.text.toString()
        if (!contactNumber.matches(Regex("^\\d{10}\$"))) {
            contactNumberEditText.error = "Enter a 10-digit phone number"
            return false
        }

        return true
    }
}
