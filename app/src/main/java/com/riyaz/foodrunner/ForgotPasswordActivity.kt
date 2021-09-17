package com.riyaz.foodrunner

import android.app.Activity
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
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var mobileNumber: EditText
    lateinit var email: EditText
    lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        mobileNumber = findViewById(R.id.txtMobileNumberInForgotPassword)
        email = findViewById(R.id.txtEmailInForgotPassword)
        btnNext = findViewById(R.id.btnNext)

        btnNext.setOnClickListener {
            try {
                var jsonRequestQueue = Volley.newRequestQueue(this)
                var url = "http://13.235.250.119/v2/forgot _password/fetch_result"
                var jsonParam = JSONObject()
                jsonParam.put("mobile_number", mobileNumber.text.toString())
                jsonParam.put("email", email.text.toString())

                var jsonObjectRequest = object: JsonObjectRequest(Method.POST, url, jsonParam, Response.Listener {

                    val jsonDataObject = it.getJSONObject("data")
                    if(jsonDataObject.getBoolean("success") && jsonDataObject.getBoolean("first_try")) {
                        val intent = Intent(this@ForgotPasswordActivity, ResetPasswordActivity::class.java)
                            startActivity(intent)

                    }else {
                        Toast.makeText(this, "Request have already been sent", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@ForgotPasswordActivity, ResetPasswordActivity::class.java)
                            startActivity(intent)
                    }

                }, Response.ErrorListener {
                    Toast.makeText(this, "Some error occurred, try again later", Toast.LENGTH_SHORT).show()
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        headers["Content-type"] = "application/json"
                        headers["token"] = "XYZ"
                        return headers
                    }

                }

                jsonRequestQueue.add(jsonObjectRequest)

            } catch (e: JSONException) {
                Toast.makeText(this, "volley error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}