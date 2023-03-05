package com.example.calculogasolina

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun validarCampos(pAlcool: String, pGasolina: String): Boolean {
            return pAlcool.isNotEmpty() && pGasolina.isNotEmpty()
        }

        val editPrecoEtanol: EditText = findViewById(R.id.editEtanol)
        val editPrecoGasolina: EditText = findViewById(R.id.editGasolina)
        val txtResultado: TextView = findViewById(R.id.txtresultado)
        val btnCalcular = findViewById<Button>(R.id.button)

        btnCalcular.setOnClickListener {
            val camposValidados =
                validarCampos(editPrecoEtanol.text.toString(), editPrecoGasolina.text.toString())
            val convertGasolina = editPrecoGasolina.text.toString().toDoubleOrNull()
            val convertAlcool = editPrecoEtanol.text.toString().toDoubleOrNull()

            if (camposValidados && convertGasolina != null && convertAlcool != null) {
                val resultado = convertAlcool / convertGasolina
                if (resultado <= 0.7) {
                    txtResultado.setText(R.string.abastecer_alcool)
                } else {
                    txtResultado.setText(R.string.abastecer_com_gasolina)
                }
            } else {
                txtResultado.setText(R.string.erro_campos_vazios)
            }

            // esconde o teclado
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        // adiciona o listener para esconder o teclado quando clicar fora do campo de texto
        findViewById<View>(android.R.id.content).setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                return false
            }
        })

        // adiciona a chamada performClick() para a ação de clique no botão
        btnCalcular.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                v.performClick()
            }
            false
        }
    }
}
