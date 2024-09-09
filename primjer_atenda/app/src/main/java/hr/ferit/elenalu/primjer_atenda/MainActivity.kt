package hr.ferit.elenalu.primjer_atenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()



        Handler(Looper.getMainLooper()).postDelayed({
            val homeFragment= MainFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_activity,homeFragment)
            transaction.commit()
        },3000)
    }
    }