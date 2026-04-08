package io.robinvanyang.example.androidsaveinstancestate

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var index: Int = 0

    private var onCreateBundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btn_recreate_activity).setOnClickListener {
            reCreateActivity()
        }

        savedInstanceState?.let {
            Log.d(TAG, "onCreate, index from bundle: ${savedInstanceState.getInt(KEY_INDEX)}")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    var onRestoreInstanceStateBundle: Bundle? = null

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        onRestoreInstanceStateBundle = savedInstanceState
        Log.d(TAG, "onRestoreInstanceState, bundle hash: ${savedInstanceState.hashCode()}")
        Log.d(
            TAG,
            "onRestoreInstanceState, index from bundle: ${savedInstanceState.getInt(KEY_INDEX)}"
        )
    }

    var onPostCreateBundle: Bundle? = null

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.d(TAG, "onPostCreate")
        savedInstanceState?.let {
            onPostCreateBundle = savedInstanceState
            Log.d(TAG, "onPostCreate, one argument bundle hash: ${savedInstanceState.hashCode()}")
            Log.d(TAG, "onPostCreate, index from bundle: ${savedInstanceState.getInt(KEY_INDEX)}")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        Log.d(TAG, "is onSaveInstanceStateBundle null: ${onSaveInstanceStateBundle == null}")
        Log.d(TAG, "is onCreateBundle null: ${onCreateBundle == null}")
        Log.d(
            TAG,
            "is onSaveInstanceStateBundle === onCreateBundle: ${onSaveInstanceStateBundle === onCreateBundle}"
        )
        Log.d(
            TAG,
            "is onCreateBundle === onRestoreInstanceStateBundle: ${onCreateBundle === onRestoreInstanceStateBundle}"
        )
        Log.d(
            TAG,
            "is onRestoreInstanceStateBundle === onPostCreateBundle: ${onRestoreInstanceStateBundle === onPostCreateBundle}"
        )
    }

    var onSaveInstanceStateBundle: Bundle? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState index: $index")

        index = findViewById<EditText>(R.id.et_index).text.toString().toInt()
        outState.putInt(KEY_INDEX, index)

        outState.let {
            onSaveInstanceStateBundle = outState
            Log.d(TAG, "onSaveInstanceState, one argument bundle hash: ${outState.hashCode()}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    private fun reCreateActivity() {

        val theme = if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val KEY_INDEX = "key_index"
    }
}