package com.example.calorycalckotlinedition.util

import android.content.Context
import android.net.Uri
import androidx.appcompat.widget.SearchView
import androidx.core.net.toFile
import com.example.calorycalckotlinedition.data.Product
import android.widget.Toast
import java.io.*

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit){
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}


@Suppress("BlockingMethodInNonBlockingContext")
suspend fun getProductsFromCVS(uri: Uri, context: Context): ArrayList<Product>? {
    if(uri==null)
    {
        Toast.makeText(context,"uri is null",Toast.LENGTH_SHORT).show()
        return null
    }

    if(!(uri.toString().endsWith("txt",true) || uri.toString().endsWith("csv",true)))
        return null

    val inputStream: InputStream = context.contentResolver.openInputStream(uri)

    val br = BufferedReader(InputStreamReader(inputStream))

    val products =  ArrayList<Product>()

    var line = br.readLine()
    while (line != null){
        val args = customSplitSpecific(line)

        if (args[1].isBlank())
            args[1] = "N/A"
        //Product(String name, String brand, float KCal, float fats, float carbs, float fibers, float sugars, float proteins)
        val product = Product(args[0],args[1],
            args[2].toFloat(),args[7].toFloat(),args[4].toFloat(),
            args[6].toFloat(),args[5].toFloat(),args[3].toFloat())

        products.add(product)

        line = br.readLine()
    }
    return products
}

fun customSplitSpecific(s: String): ArrayList<String> {
    val words = ArrayList<String>()
    var notInsideComma = true
    var start = 0
    for (i in 0 until s.length - 1) {
        if ((s[i] == ','||s[i] == ';') && notInsideComma) {
            words.add(s.substring(start, i))
            start = i + 1
        } else if (s[i] == '"') notInsideComma = !notInsideComma
    }
    words.add(s.substring(start))
    return words
}