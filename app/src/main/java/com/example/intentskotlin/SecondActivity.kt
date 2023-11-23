package com.example.intentskotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.view.View
import com.example.intentskotlin.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.button.setOnClickListener(this)
        val extras = intent.extras
        //recupera el valor asociado a “usuario”
        val str = extras!!.getString("usuario")
        //recupera el valor asociado a “edad”
        val edad = extras!!.getInt("edad")
        binding.textView.text = "Hola: $str, $edad"
    }

    override fun onClick(view: View) {
        if (view === binding.button) {
            val intent = Intent()
            intent.putExtra(MainActivity.RESULTADO, binding.editText.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}