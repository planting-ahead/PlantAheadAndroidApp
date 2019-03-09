package com.example.plantahead.activities

import android.app.LoaderManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import com.example.plantahead.R
import com.example.plantahead.models.Plant
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        var alarmState = false
        var currentPlant : Plant? = null

        val CHANNEL_ID = "test_channel"
        val localContext = this
        createNotificationChannel()

        // load plants
        val firebaseDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("plantTriggers")
        val listener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                // check alarm state
                val sizeOfArray = p0.children.count() - 1
                val string = p0.children.elementAt(sizeOfArray).child("Command").value.toString()
                val listOfValues = string.split(",")
                System.out.println(string)
                val temperature = listOfValues.last().toDouble()

                if (currentPlant != null) {
                    val currTemperature = currentPlant
                    if (!alarmState && currTemperature != null && temperature > 21.0){
                        var builder = NotificationCompat.Builder(localContext, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("Temperature too High")
                            .setContentText("Please water your plant")
                            .setStyle(NotificationCompat.BigTextStyle()
                                .bigText("Please water your plant"))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                        with(NotificationManagerCompat.from(localContext)) {
                            // notificationId is a unique int for each notification that you must define

                            notify(System.currentTimeMillis().toInt(), builder.build())
                        }
                        alarmState = true
                    }
                }

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        val firebasePlantReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("plants")

        val valueListener = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                // load plant data
                val plant = p0.children.elementAt(p0.children.count() - 1).getValue(Plant::class.java)
                System.out.println(plant.toString())
                currentPlant = plant
                plant_name.text = plant?.name
                plant_description.text = plant?.description
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        fab.setOnClickListener { launchPlantActivity(localContext) }

        firebasePlantReference.addValueEventListener(valueListener)
        firebaseDatabaseReference.addChildEventListener(listener)
    }

    private fun launchPlantActivity(context: Context) {
        startActivity(Intent(context, AddPlantActivity::class.java))
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val CHANNEL_ID = "test_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
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

}