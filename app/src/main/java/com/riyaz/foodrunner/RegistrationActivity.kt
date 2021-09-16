package com.riyaz.foodrunner

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Method
import java.util.*
import java.util.jar.Attributes

class RegistrationActivity : AppCompatActivity() {

    lateinit var userName: EditText
    lateinit var userEmail: EditText
    lateinit var userAddress: EditText
    lateinit var userMobileNumber: EditText
    lateinit var userPassword: EditText
    lateinit var userConfirmedPassword: EditText
    lateinit var btnRegister: Button
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        toolbar = findViewById(R.id.toolBarInRegistration)
        setupToolBar()

        userName = findViewById(R.id.txtNameInRegistration)
        userEmail = findViewById(R.id.txtEmailInRegistration)
        userAddress = findViewById(R.id.txtAddressInRegistration)
        userMobileNumber = findViewById(R.id.txtMobileNumberInRegistration)
        userPassword = findViewById(R.id.txtPasswordInRegistration)
        userConfirmedPassword = findViewById(R.id.txtConfirmPasswordInRegistration)
        btnRegister = findViewById(R.id.btnRegistration)
        sharedPreferences = getPreferences(MODE_PRIVATE)

        btnRegister.setOnClickListener {
            if (userPassword.text.toString() != userConfirmedPassword.text.toString()) {
                Toast.makeText(this, "Confirmed Password is different", Toast.LENGTH_SHORT).show()
            } else {
                var jsonRequestQueue = Volley.newRequestQueue(this)
                var url = "http://13.235.250.119/v2/register/fetch_result"
                var jsonParam = JSONObject()
                jsonParam.put("name", userName.text.toString())
                jsonParam.put("email", userEmail.text.toString())
                jsonParam.put("mobile_number", userMobileNumber.text.toString())
                jsonParam.put("address", userAddress.text.toString())
                jsonParam.put("password", userPassword.text.toString())

                var jsonObjectRequest =
                    object : JsonObjectRequest(Method.POST, url, jsonParam, Response.Listener {
                        try {
                            var success = it.getBoolean("success")
                            if (success) {

                                val jsonDataObject = it.getJSONObject("data")
                                sharedPreferences.edit()
                                    .putString("name", jsonDataObject.getString("name")).apply()
                                sharedPreferences.edit()
                                    .putString("email", jsonDataObject.getString("email")).apply()
                                sharedPreferences.edit().putString(
                                    "mobile_number",
                                    jsonDataObject.getString("mobile_number")
                                ).apply()
                                sharedPreferences.edit()
                                    .putString("address", jsonDataObject.getString("address"))
                                    .apply()
                                Toast.makeText(
                                    this,
                                    "you have been successfully registered",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this, MainActivity::class.java)
                                startActivity(intent)

                            } else {
                                Toast.makeText(this, "Json error occurred", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } catch (e: JSONException) {
                            Toast.makeText(this, "volley error", Toast.LENGTH_SHORT).show()
                        }
                    }, Response.ErrorListener {

                    }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "4818f9259d6efa"
                            return headers
                        }
                    }
            }

        }

    }
    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)

    }
}