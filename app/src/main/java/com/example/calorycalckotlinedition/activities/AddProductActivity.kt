package com.example.calorycalckotlinedition.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.calorycalckotlinedition.DataBaseApp
import com.example.calorycalckotlinedition.R
import com.example.calorycalckotlinedition.data.Product
import com.example.calorycalckotlinedition.viewModels.ProductVM
import com.example.calorycalckotlinedition.viewModels.ProductVMFactory

class AddProductActivity : AppCompatActivity(R.layout.adding_product) {

    private lateinit var prodName: EditText
    private lateinit var prodBrand: EditText
    private lateinit var prodKcals: EditText
    private lateinit var prodProteins: EditText
    private lateinit var prodSugars: EditText
    private lateinit var prodFats: EditText
    private lateinit var prodFibers: EditText
    private lateinit var prodCarbs: EditText

    private lateinit var button: Button

    private var product: Product? = null
    private val productVM: ProductVM by viewModels{
        ProductVMFactory((this.application as DataBaseApp).productRepo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVars()
        initListeners()
    }

    private fun initVars() {
        prodName = findViewById(R.id.et_prod_name)
        prodBrand = findViewById(R.id.et_prod_brand)

        prodCarbs = findViewById(R.id.et_prod_carbs)
        prodKcals = findViewById(R.id.et_product_kcals)
        prodFibers = findViewById(R.id.et_prod_fibers)
        prodFats = findViewById(R.id.et_prod_fats)
        prodSugars = findViewById(R.id.et_prod_sugars)
        prodProteins = findViewById(R.id.et_prod_proteins)

        button = findViewById(R.id.addProductToDBButton)
    }

    private fun initListeners() {

        prodCarbs.doAfterTextChanged {
            if(prodCarbs.text.toString() == "")
                prodCarbs.setText("0.0")
        }
        prodKcals.doAfterTextChanged {
            if(prodKcals.text.toString() == "")
                prodKcals.setText("0.0")
        }
        prodFibers.doAfterTextChanged {
            if(prodFibers.text.toString() == "")
                prodFibers.setText("0.0")
        }
        prodFats.doAfterTextChanged {
            if(prodFats.text.toString() == "")
                prodFats.setText("0.0")
        }
        prodSugars.doAfterTextChanged {
            if(prodSugars.text.toString() == "")
                prodSugars.setText("0.0")
        }
        prodProteins.doAfterTextChanged {
            if(prodProteins.text.toString() == "")
                prodProteins.setText("0.0")
        }


        button.setOnClickListener {
            if (prodName.text.toString().isEmpty()){
                Toast.makeText(this,"Name can't be empty",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val brand = prodBrand.text.toString().ifEmpty { "N/A" }
            val name = prodName.text.toString()

            var kcals = 0.0f
            var carbs = 0.0f
            var sugars = 0.0f
            var fats = 0.0f
            var fibers = 0.0f
            var proteins = 0.0f

            try {
                kcals = prodKcals.text.toString().toFloat()
                carbs = prodCarbs.text.toString().toFloat()
                sugars = prodSugars.text.toString().toFloat()
                fats = prodFats.text.toString().toFloat()
                fibers = prodFibers.text.toString().toFloat()
                proteins = prodProteins.text.toString().toFloat()
            }catch (e: Exception){
                Toast.makeText(this, String.format("Product '%s' has not been added",prodName.text.toString()),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if((kcals + carbs + sugars + fats + fibers + proteins >= 0.1f)){
                product = Product(name,brand,kcals,fats,carbs,fibers,sugars,proteins)
                productVM.insert(product!!)
                Toast.makeText(this, String.format("Product '%s' added",prodName.text.toString()),Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, String.format("Product '%s' has not been added\nAll near zero values",prodName.text.toString()),Toast.LENGTH_SHORT).show()
            }
        }
    }


}