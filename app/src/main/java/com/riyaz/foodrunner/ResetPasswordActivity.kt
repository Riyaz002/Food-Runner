package com.riyaz.foodrunner

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var mobileNumber: EditText
    lateinit var password: EditText
    lateinit var otp: EditText
    lateinit var btnSubmit: Button
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        mobileNumber = findViewById(R.id.txtMobileNumberInResetPassword)
        password = findViewById(R.id.txtPasswordInResetPassword)
        otp = findViewById(R.id.txtOTP)
        btnSubmit = findViewById(R.id.btnSubmitInForgotPassword)
        sharedPreferences = getSharedPreferences("loginInfo" ,MODE_PRIVATE)
        btnSubmit.setOnClickListener {
            try {
                var jsonRequestQueue = Volley.newRequestQueue(this)
                val url = "XYZ"
                var jsonParam = JSONObject()
                jsonParam.put("mobile_number", mobileNumber.text.toString())
                jsonParam.put("password", password.text.toString())
                jsonParam.put("otp", otp.text.toString())

                var jsonObjectRequest = object: JsonObjectRequest(Method.POST, url, jsonParam, Response.Listener {
                    val jsonDataObject = it.getJSONObject("data")

                    if (jsonDataObject.getBoolean("success")){

                        sharedPreferences.edit().putString("password", jsonParam.getString("password"))

                        val intent = Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }, Response.ErrorListener {  }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        headers["Content-type"] = "application/json"
                        headers["token"] = "XYZ"
                        return headers
                    }
                }

                jsonRequestQueue.add(jsonObjectRequest)

            } catch (e: JSONException) {
                Toast.makeText(this, "Volley error occurred", Toast.LENGTH_SHORT).show()
            }

        }
    }
}