package org.freeflow.cellsolutions

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class CellSolutions : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}