package org.freeflow.cellsolutions.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

fun isUserSignedIn() = FirebaseAuth.getInstance().currentUser != null

fun fetchDBCurrentUser(): DatabaseReference? {
    val mFirebaseDB = FirebaseDatabase.getInstance()
    val mFirebaseAuth = FirebaseAuth.getInstance()

    return if (isUserSignedIn()) mFirebaseDB.getReference("users").child(mFirebaseAuth.currentUser!!.uid) else null
}