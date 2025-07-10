package com.davidrajchenberg.taboolademo

import android.app.Application
import com.taboola.android.TBLPublisherInfo
import com.taboola.android.Taboola
import com.davidrajchenberg.taboolademo.utils.TaboolaConstants

class TaboolaDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Set Taboola SDK with the mock publisher ID
        val publisherInfo = TBLPublisherInfo(TaboolaConstants.PUBLISHER_ID)

        // Initialize Taboola SDK
        Taboola.init(publisherInfo)
    }
}