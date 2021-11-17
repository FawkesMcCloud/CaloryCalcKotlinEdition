package com.example.calorycalckotlinedition.appUI

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.calorycalckotlinedition.DataBaseApp
import com.example.calorycalckotlinedition.R
import com.example.calorycalckotlinedition.activities.AddProductActivity
import com.example.calorycalckotlinedition.data.ProductListAdapter
import com.example.calorycalckotlinedition.util.onQueryTextChanged
import com.example.calorycalckotlinedition.viewModels.ProductVM
import com.example.calorycalckotlinedition.viewModels.ProductVMFactory

class ProductsFragment : Fragment(R.layout.fragment_products){

    val productVM: ProductVM by activityViewModels {
        ProductVMFactory((requireActivity().application as DataBaseApp).productRepo)
    }

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        productVM.import(it, requireActivity().applicationContext)
    }

    private lateinit var recyclerView : RecyclerView

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            productVM.changeFilter(it)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_addProduct -> {
                val intent = Intent(this.context, AddProductActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_importCSV -> {
                getContent.launch("*/*")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewProductsNames)
        val adapter = ProductListAdapter()
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = layoutManager
        val itemDecorator = DividerItemDecoration(recyclerView.context,layoutManager.orientation)
        recyclerView.addItemDecoration(itemDecorator)

        productVM.products.observe(viewLifecycleOwner) {
            product -> product?.let { adapter.submitList(it) }
        }
        recyclerView.setHasFixedSize(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

    }

}