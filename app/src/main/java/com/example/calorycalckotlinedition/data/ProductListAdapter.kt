package com.example.calorycalckotlinedition.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorycalckotlinedition.R

class ProductListAdapter: ListAdapter<Product, ProductViewHolder>(ProductComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
}

class ProductComparator: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.name == newItem.name && oldItem.brand == newItem.brand && oldItem.Proteins == newItem.Proteins &&
                oldItem.Fats == newItem.Fats && oldItem.KCal == newItem.KCal && oldItem.Sugars == newItem.Sugars &&
                oldItem.Fibers == newItem.Fibers
    }

}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val recordItemName: TextView = itemView.findViewById(R.id.tv_ProductName)
    private val recordItemBrand: TextView = itemView.findViewById(R.id.tv_ProductBrand)
    private val recordItemInfo: TextView = itemView.findViewById(R.id.tv_ProductInfo)

    fun bind(record: Product){
        recordItemBrand.text = record.brand
        recordItemName.text = record.name
        recordItemInfo.text =   String.format("KCal: %.2f\nCarbs: %.2f\nProteins: %.2f\nFats: %.2f\nSugars: %.2f\nFibers: %.2f",
                                record.KCal, record.Carbs, record.Proteins, record.Proteins, record.Fats, record.Sugars, record.Fibers)
    }

    companion object{
        fun create(parent: ViewGroup):ProductViewHolder{
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
            return ProductViewHolder(view)
        }
    }
}
