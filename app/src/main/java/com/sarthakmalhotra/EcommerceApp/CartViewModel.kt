import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarthakmalhotra.EcommerceApp.Product


class CartViewModel : ViewModel() {
    private val cartItems = MutableLiveData<List<Product>>()

    fun getCartItems(): LiveData<List<Product>> {
        return cartItems
    }

    fun addToCart(item: Product) {
        val currentCart = cartItems.value ?: emptyList()
        cartItems.value = currentCart + item
        Log.d("cartItems", "This is a debug message ${getCartItems().value?.size}")
    }

    fun removeItemFromCart(item: Product) {
        val currentCart = cartItems.value ?: mutableListOf()
        cartItems.value = cartItems.value?.toMutableList()?.apply {
            remove(item)
        }?.toList()
        cartItems.value = currentCart
    }

    fun clearCart() {
        cartItems.value = mutableListOf() // Clearing the cart by assigning an empty list to it
    }
}
