import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sarthakmalhotra.EcommerceApp.Product
import com.sarthakmalhotra.EcommerceApp.ProductDetailsActivity
import com.sarthakmalhotra.EcommerceApp.R

class ProductAdapter(private val productList: List<Product>,private val cartViewModel: CartViewModel) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.productName)
        val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
        val productImageView: ImageView = itemView.findViewById(R.id.productImage)
        val addToCartButton: Button = itemView.findViewById(R.id.addToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = productList[position]
        holder.nameTextView.text = product.name
        holder.priceTextView.text = product.price.toString()
        val storeRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(product.image)
        Glide.with(holder.productImageView.context).load(storeRef).into(holder.productImageView)

        holder.addToCartButton.setOnClickListener {
            cartViewModel.addToCart(product)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
            intent.putExtra("product", product)
            intent.putExtra("cartData", cartViewModel.getCartItems().value?.toTypedArray())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
