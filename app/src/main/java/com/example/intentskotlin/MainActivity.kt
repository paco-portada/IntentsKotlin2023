package com.example.intentskotlin

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.intentskotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    var code = 1

    companion object {
        const val RESULTADO = "resultado"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)

        launcher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                binding.editText.setText("Respuesta: " + data!!.getStringExtra(RESULTADO))
            }
        }
    }

    override fun onClick(view: View) {
        lateinit var intent: Intent

        if (view === binding.button1) {
            //ACTION_VIEW. Abrir el navegador con una URL indicada como dato
            val link = "https://kotlin.org"

            openWebPage(link)
        }
        if (view === binding.button2) {
            //ACTION_VIEW. Muestra dirección en mapa indicada como dato
            // (El emulador o AVD en el que se pruebe debe incluir las API de Google)
            val dir = "Calle+Larios+Malaga"
            showMap(Uri.parse("geo:0,0?q=$dir"))
        }
        if (view === binding.button3) {
            // ACTION_DIAL.Marca el número de teléfono indicado como dato.
            val phoneNumber:String = "951297929"

            call(phoneNumber)
        }
        if (view === binding.button4) {
            intent = Intent(this, SecondActivity::class.java)
            val extras = Bundle()
            extras.putString("usuario", binding.editText.text.toString())
            extras.putInt("edad", 27)
            intent.putExtras(extras)
            startActivity(intent)
        }
        if (view === binding.button5) {
            intent = Intent(this, SecondActivity::class.java)
            val extras = Bundle()
            extras.putString("usuario", binding.editText.text.toString())
            extras.putInt("edad", 27)
            intent.putExtras(extras)
            // startActivityForResult(intent, code)
            launcher.launch(intent)
        }
    }
    /*
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == code && resultCode == RESULT_OK) {
                val res = data!!.extras!!.getString(RESULTADO)

                binding.editText.setText("Respuesta: " + res)
            }
        }
     */


// Functions

    fun openWebPage(url: String) {

        val webPage: Uri = Uri.parse(url)
/*
        val intent = Intent(Intent.ACTION_VIEW, webPage)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Error al lanzar el intent del navegador", Toast.LENGTH_SHORT).show()
        }
*/
        try {
            val browserIntent = Intent()
                .setAction(ACTION_VIEW)
                .addCategory(CATEGORY_BROWSABLE)
                .setData(webPage)
            startActivity(browserIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this,"Error al lanzar el intent del navegador " + e.message.toString(),Toast.LENGTH_SHORT)
                .show()
            Log.e("Error", e.message.toString())
        }

    }
    fun showMap(geoLocation: Uri) {
        try {
            val intent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(geoLocation)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "Error al lanzar el intent del mapa " + e.message.toString(),
                Toast.LENGTH_SHORT
            )
                .show()
            Log.e("Error", e.message.toString())
        }
    }

    fun call(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(dialIntent)
        } else {
            Toast.makeText(this, "Error al lanzar el intent del teléfono", Toast.LENGTH_SHORT)
                .show()
        }
    }

















}