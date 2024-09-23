package com.yefelson.moneycash2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // Tasas de cambio fijas (puedes modificar estos valores)
    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "GBP" to 0.75,
        "JPY" to 110.0,
        "MXN" to 20.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextAmount: EditText = findViewById(R.id.edittext_amount)
        val spinnerBaseCurrency: Spinner = findViewById(R.id.spinner_base_currency)
        val buttonGetRates: Button = findViewById(R.id.button_get_rates)
        val tableConversionRates: TableLayout = findViewById(R.id.table_conversion_rates)

        buttonGetRates.setOnClickListener {
            val amountString = editTextAmount.text.toString()
            if (amountString.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa un monto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountString.toDouble()
            val baseCurrency = spinnerBaseCurrency.selectedItem.toString()

            // Limpiar la tabla antes de mostrar nuevos resultados
            tableConversionRates.removeViews(1, tableConversionRates.childCount - 1)

            exchangeRates.forEach { (currency, rate) ->
                val convertedAmount = amount * rate
                val tableRow = layoutInflater.inflate(R.layout.table_row_conversion, null) as TableRow

                tableRow.findViewById<TextView>(R.id.textview_currency_1).text = currency
                tableRow.findViewById<TextView>(R.id.textview_amount_1).text = String.format("%.2f", convertedAmount)

                tableConversionRates.addView(tableRow)
            }
        }
    }
}