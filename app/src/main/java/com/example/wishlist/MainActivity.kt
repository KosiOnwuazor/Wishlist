package com.example.wishlist

import android.content.ClipData
import android.os.Bundle
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Data class for wishlist items
data class Item(val name: String, val price: String, val url: String)

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)

        adapter = ItemAdapter(mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener { showAddItemDialog() }
    }

    private fun showAddItemDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.editTextName)
        val priceInput = dialogView.findViewById<EditText>(R.id.editTextPrice)
        val urlInput = dialogView.findViewById<EditText>(R.id.editTextUrl)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add New Item")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = nameInput.text.toString()
                val price = priceInput.text.toString()
                val url = urlInput.text.toString()
                if (name.isNotEmpty() && price.isNotEmpty() && url.isNotEmpty()) {
                    adapter.addItem(Item(name, price, url))
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }
}

// Adapter for RecyclerView
class ItemAdapter(private val items: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.itemName)
        private val priceText: TextView = itemView.findViewById(R.id.itemPrice)
        private val urlText: TextView = itemView.findViewById(R.id.itemUrl)

        fun bind(item: Item) {
            nameText.text = item.name
            priceText.text = "Price: ${item.price}"
            urlText.text = "URL: ${item.url}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
}
