package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    var CurrentImageUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadMeme()


    }


    private fun LoadMeme(){

        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonobjectRequest = JsonObjectRequest(

            Request.Method.GET, url,null,
            { response ->
                // Display the first 500 characters of the response string.
              CurrentImageUrl=response.getString("url")
                val memeimageView=findViewById<ImageView>(R.id.memeimageView)

                val  progressbar=findViewById<ProgressBar>(R.id.progressbar)
                progressbar.visibility=View.VISIBLE
                Glide.with(this).load(CurrentImageUrl).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false;
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false;
                    }

                }).into(memeimageView)
            },


            {
                Toast.makeText(this, "Something is wrong", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonobjectRequest)

    }

    fun ShareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"$CurrentImageUrl")
        val chooser=Intent.createChooser(intent,"send this meme using")
        startActivity(chooser)
    }
    fun NextMeme(view: View) {
        LoadMeme()

    }
}