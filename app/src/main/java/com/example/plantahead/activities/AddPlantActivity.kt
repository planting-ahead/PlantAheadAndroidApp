package com.example.plantahead.activities


import android.app.LoaderManager
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.plantahead.R
import com.example.plantahead.models.Plant
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.synthetic.main.activity_addplant.*
import kotlinx.android.synthetic.main.activity_signup.*

class AddPlantActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_addplant)
        // Set up the login form.
        // populateAutoComplete()
//        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
//            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                attemptSave()
//                return@OnEditorActionListener true
//            }
//            false
//        })

        addplat_button.setOnClickListener{attemptSave()}
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadFinished(p0: Loader<Cursor>?, p1: Cursor?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoaderReset(p0: Loader<Cursor>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun attemptSave() {
        // call an endpoint
        val plantName = plant_name.text.toString()
        val plantDescription = description.text.toString()
        writeNewPlant(plantName, plantDescription)
    }

    private lateinit var database: DatabaseReference
    private fun writeNewPlant(name:String, description:String){
        database = FirebaseDatabase.getInstance().reference
        val plant = Plant(name, " ", description, 0, 0, 0)
        val value = database.child("plants").push().setValue(plant)
    }
}