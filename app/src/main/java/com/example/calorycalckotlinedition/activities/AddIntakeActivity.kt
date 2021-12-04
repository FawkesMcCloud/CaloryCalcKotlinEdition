package com.example.calorycalckotlinedition.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorycalckotlinedition.DataBaseApp
import com.example.calorycalckotlinedition.R
import com.example.calorycalckotlinedition.data.ProductListAdapter
import com.example.calorycalckotlinedition.util.onQueryTextChanged
import com.example.calorycalckotlinedition.viewModels.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddIntakeActivity: AppCompatActivity(R.layout.add_intake) {
    private lateinit var addIntake : Button
    private lateinit var gramsText: EditText
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private var today = Calendar.getInstance(TimeZone.getDefault()).time

    val productVM: ProductVM by viewModels {
        ProductVMFactory((this.application as DataBaseApp).productRepo)
    }

    val historyVM: HistoryVM by viewModels {
        HistoryVMFactory((this.application as DataBaseApp).historyRepo)
    }


    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        today = simpleDateFormat.parse(simpleDateFormat.format(today))

        recyclerView = findViewById(R.id.rv_intake)
        addIntake = findViewById(R.id.intakeOKButton)
        gramsText = findViewById(R.id.intakeGrams)

        val adapter = ProductListAdapter(true)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context,layoutManager.orientation))

        productVM.products.observe(this) { product ->
            product?.let { adapter.setProducts(it) }
        }
        historyVM.setDateFilter(today)

        gramsText.doAfterTextChanged {
            if(it.toString().isBlank())
                gramsText.setText("0")
        }

        addIntake.setOnClickListener {
            val product = adapter.getSelectedProduct()
            val record = historyVM.recordByDate.value

            if(product!=null && record!=null){
                val todays = historyVM.recordByDate.value!!
                try{
                    val grams = gramsText.text.toString().toFloat()
                    todays.deltaSugars += product.sugars/100 * grams
                    todays.deltaProteins += product.proteins/100 * grams
                    todays.deltaFibers += product.fibers/100 * grams
                    todays.deltaCarbs += product.carbs/100*grams
                    todays.deltaFats += product.fats/100*grams
                    todays.deltaKCal += product.kCal/100*grams

                    historyVM.update(record)
                    historyVM.recordByDate.notifyObserver()
                }catch (ex: Exception){
                    Toast.makeText(this,"Couldn't add intake",Toast.LENGTH_LONG).show()
                }
                Toast.makeText(this,"Intake added!",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.intake_menu, menu)

        val search = menu?.findItem(R.id.action_searchIntake)
        val searchView = search?.actionView as SearchView
        searchView.onQueryTextChanged {
            productVM.changeFilter(it)
        }

        return true
    }

}