// https://medium.com/litslink/flutter-how-to-create-your-own-native-notification-in-android-ba2bd2a5d97

package com.example.androidcodespecific
import io.flutter.embedding.android.FlutterActivity

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

import java.util.concurrent.Executors
import android.os.Handler
import android.os.Looper
import java.util.Timer
import android.widget.Toast
import kotlin.concurrent.schedule
import io.flutter.embedding.engine.plugins.FlutterPlugin
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.AsyncTask
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class MainActivity: FlutterActivity() {
private val CHANNEL = "com.example.androidcodespecific/method"
  override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
    super.configureFlutterEngine(flutterEngine)
   

    //creating a new MethodChannel
    MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
      call, result ->
      Toast.makeText(this, "hello" , Toast.LENGTH_LONG).show()

    //   ***************************************************Show Notification AND BACKGROUND TASK*************
        // val myExecutor = Executors.newSingleThreadExecutor()
        // val myHandler = Handler(Looper.getMainLooper())



    val iconName = "ic_launcher"
    val typeDef = "mipmap"
    val smallIcon = context.resources.getIdentifier(
            iconName,
            typeDef,
            context.packageName
    )
        Timer().schedule(2000) {
          with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
    
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val service: String = Context.NOTIFICATION_SERVICE
    
                val name = "OLA JAIMITKUMAR"
                val descriptionText = "THIS IS DESCRITION"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("My_Id", name, importance).apply {
                    description = descriptionText
                }
    
                val notificationManager: NotificationManager =
                        context.getSystemService(service) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
    
            val builder = NotificationCompat.Builder(context, "My_Id")
                    .setSmallIcon(smallIcon)
                    .setContentTitle("YOU CRACKED IT FINALLY")
                    .setContentText("Hello JAIMITKUMAR PANCHAL")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    
            val someNotificationId = 1;
    
            notify(someNotificationId, builder.build())
        }
    }

    //*********** */
      if (call.method == "getValue") {
        val batteryLevel = getBatteryLevel()

        if (batteryLevel != -1) {
          result.success(batteryLevel)
        } else {
          result.error("UNAVAILABLE", "Battery level not available.", null)
        }
      } else {
        result.notImplemented()
      }
    }
  }


    private fun getBatteryLevel(): Int {
    val batteryLevel: Int
    if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
      val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
      batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    } else {
      val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
      batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    }
    return batteryLevel
  }


   
}

