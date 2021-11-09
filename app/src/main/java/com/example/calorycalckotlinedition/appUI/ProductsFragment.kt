package com.example.calorycalckotlinedition.appUI

import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.calorycalckotlinedition.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.EditText
import android.widget.TableLayout
import com.example.calorycalckotlinedition.activities.AddProductActivity

class ProductsFragment : Fragment(R.layout.fragment_products){

    private lateinit var addButton : FloatingActionButton
    private lateinit var brandFilter : EditText
    private lateinit var nameFilter : EditText
    private lateinit var tableLayout : TableLayout

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton = requireView().findViewById(R.id.addProductButton)
        addButton.setOnClickListener{
            val intent = Intent(this.activity, AddProductActivity::class.java)
            startActivity(intent)
        }

        brandFilter = requireView().findViewById(R.id.editTextBrand)
        nameFilter = requireView().findViewById(R.id.editTextName)
        tableLayout = requireView().findViewById(R.id.tableLayout)

        initTable()
    }

    private fun initTable(){

    }

}