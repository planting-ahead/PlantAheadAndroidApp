package com.example.plantahead.activities


import android.app.LoaderManager
import android.content.Loader
import android.database.Cursor
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import com.example.plantahead.R
import com.example.plantahead.models.Plant
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_addplant.*


class AddPlantActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_addplant)
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
        val humidityLevel = humidity_level.text.toString()
        val temperature = temperature_level.text.toString()
        writeNewPlant(plantName, temperature, humidityLevel, plantDescription)
    }

    private lateinit var database: DatabaseReference
    private fun writeNewPlant(name:String, temperature: String, humidityLevel: String, description:String){
        database = FirebaseDatabase.getInstance().reference
        val plant = Plant(name, "", description, "", humidityLevel, temperature, "")
        database.child("plants").push().setValue(plant)
        this.finish()
    }
}