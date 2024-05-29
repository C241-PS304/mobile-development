package com.bangkit2024.facetrack.ui.activities.reset

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityResetBinding

class ResetActivity : AppCompatActivity() {
    private lateinit var resetBinding: ActivityResetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetBinding = ActivityResetBinding.inflate(layoutInflater)
        setContentView(resetBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Reset Password"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}