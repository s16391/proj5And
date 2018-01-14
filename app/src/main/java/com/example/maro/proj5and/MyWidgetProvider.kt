package com.example.maro.proj5and

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract.Helpers.update
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.widget.RemoteViews
import android.content.ComponentName
import android.appwidget.AppWidgetProvider
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.util.*
import android.support.v4.content.ContextCompat.startActivity




/**
 * Created by maro on 14.01.2018.
 */

var WIDGET_BUTTON_WWW = "com.example.maro.proj5and.WIDGET_BUTTON"
var WIDGET_BUTTON_NEXT = "com.example.maro.proj5and.WIDGET_BUTTON_NEXT"
var WIDGET_BUTTON_PLAY = "com.example.maro.proj5and.WIDGET_BUTTON_PLAY"
var WIDGET_BUTTON_STOP = "com.example.maro.proj5and.WIDGET_BUTTON_STOP"
var WIDGET_BUTTON_CHANGE_IMAGE = "com.example.maro.proj5and.WIDGET_BUTTON_CHANGE_IMAGE"


class MyWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {

        // Get all ids
        val thisWidget = ComponentName(context,
                MyWidgetProvider::class.java)
        val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
        for (widgetId in allWidgetIds) {
            // create some random data
            val number = Random().nextInt(100)

            val remoteViews = RemoteViews(context.packageName,
                    R.layout.widget_layout)
            Log.w("WidgetExample", number.toString())
            // Set the text
            remoteViews.setTextViewText(R.id.update, number.toString())

            val intent = Intent(context, MyWidgetProvider::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
            val pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val intentWBW = Intent(context, MyWidgetProvider::class.java)
            intentWBW.setAction(WIDGET_BUTTON_WWW)
            val pendingIntentWBW = PendingIntent.getBroadcast(context,
                    0, intentWBW, PendingIntent.FLAG_UPDATE_CURRENT)

            val intentWBN = Intent(context, MyWidgetProvider::class.java)
            intentWBN.setAction(WIDGET_BUTTON_NEXT)
            val pendingIntentWBN = PendingIntent.getBroadcast(context,
                    0, intentWBN, PendingIntent.FLAG_UPDATE_CURRENT)

            val intentWBP = Intent(context, BackgroundSoundService::class.java)
//            intentWBP.setAction(WIDGET_BUTTON_PLAY)
            val pendingIntentWBP = PendingIntent.getService(context,
                    0, intentWBP, 0)

            val intentWBS = Intent(context, MyWidgetProvider::class.java)
            intentWBS.setAction(WIDGET_BUTTON_STOP)
            val pendingIntentWBS = PendingIntent.getBroadcast(context,
                    0, intentWBS, PendingIntent.FLAG_UPDATE_CURRENT)

            val intentWBC = Intent(context, MyWidgetProvider::class.java)
            intentWBC.setAction(WIDGET_BUTTON_CHANGE_IMAGE)
            val pendingIntentWBC = PendingIntent.getBroadcast(context,
                    0, intentWBC, PendingIntent.FLAG_UPDATE_CURRENT)

            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent)
            remoteViews.setOnClickPendingIntent(R.id.btn_www, pendingIntentWBW)
            remoteViews.setOnClickPendingIntent(R.id.btn_play, pendingIntentWBP)
            remoteViews.setOnClickPendingIntent(R.id.btn_next, pendingIntentWBN)
            remoteViews.setOnClickPendingIntent(R.id.btn_stop, pendingIntentWBS)
            remoteViews.setOnClickPendingIntent(R.id.btn_changeImage, pendingIntentWBC)

            appWidgetManager.updateAppWidget(widgetId, remoteViews)
        }

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (WIDGET_BUTTON_WWW.equals(intent!!.getAction())) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            context!!.startActivity(browserIntent)
        }
        if (WIDGET_BUTTON_PLAY.equals(intent!!.getAction())) {

            val svc = Intent(context, BackgroundSoundService::class.java)
            context!!.startService(svc)
        }
        if (WIDGET_BUTTON_NEXT.equals(intent!!.getAction())) {


        }
        if (WIDGET_BUTTON_STOP.equals(intent!!.getAction())) {
            val svc = Intent(context, BackgroundSoundService::class.java)
            context!!.stopService(svc)
        }
        if (WIDGET_BUTTON_CHANGE_IMAGE.equals(intent!!.getAction())) {
            val remoteViews = RemoteViews(context!!.getPackageName(),
                    R.layout.widget_layout)
            val id = context.getResources().getIdentifier("yourpackagename:drawable/ic_tukan.png", null, null)
            remoteViews.setImageViewResource(R.id.iv_Widget, id)
            pushWidgetUpdate(context!!.getApplicationContext(),
                    remoteViews);
        }
    }
}

fun pushWidgetUpdate(context: Context, remoteViews: RemoteViews) {
    val myWidget = ComponentName(context,
            MyWidgetProvider::class.java)
    val manager = AppWidgetManager.getInstance(context)
    manager.updateAppWidget(myWidget, remoteViews)
}