package com.example.calorycalckotlinedition.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calorycalckotlinedition.R

class ProductListAdapter(private val isSelectable: Boolean = false): RecyclerView.Adapter<ProductViewHolder>() {
    private var products: List<Product> = ArrayList()
    private var selectedPos = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val holder = ProductViewHolder.create(parent)
        if(isSelectable)
        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPos)
            selectedPos = holder.layoutPosition
            notifyItemChanged(selectedPos)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.itemView.isSelected = selectedPos == position
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(products:List<Product>){
        this.products = products
        notifyDataSetChanged()
    }

    fun getNoteAt(pos: Int): Product {
        return products[pos]
    }

    fun getSelectedProduct(): Product?{
        if(selectedPos!=RecyclerView.NO_POSITION)
            return products[selectedPos]
        return null
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
