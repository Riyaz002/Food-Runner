package com.riyaz.foodrunner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.riyaz.foodrunner.util.ConnectionManager
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap
import com.google.gson.JsonObject as JsonObject1

class LoginActivity : AppCompatActivity() {
    lateinit var enteredNumber: EditText
    lateinit var enteredPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnforgotPassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        enteredNumber = findViewById(R.id.etNumber)
        enteredPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnforgotPassword = findViewById(R.id.btnForgotPassword)

        btnLogin.setOnClickListener {
            val number: String = enteredNumber.text.toString()
            val password: String = enteredPassword.text.toString()

            if (ConnectionManager().connectivityManager(this as Context)){

                val jsonRequestQueue = Volley.newRequestQueue(this@LoginActivity)
                val jsonParam = JSONObject()
                jsonParam.put("mobile_number", number)
                jsonParam.put("password", password)

                val url = "http://13.235.250.119/v2/login/fetch_result"

                val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, jsonParam, Response.Listener {
                    val success = it.getBoolean("success")
                    if (success){
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else{
                        Toast.makeText(this, "Json error", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(this, "Volley error", Toast.LENGTH_SHORT).show()
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        headers["Content-type"] = "application/json"
                        headers["token"] = "4818f9259d6efa"

                        return headers
                    }
                }
                jsonRequestQueue.add(jsonObjectRequest)
            }
        }

        btnforgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}