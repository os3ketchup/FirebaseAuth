package com.example.firebaseauth

import com.google.firebase.auth.FirebaseAuth

object MyInfo {
     lateinit var mAuth: FirebaseAuth
     var phoneNumber = ""
     var code = ""
     var mVerificationId = ""
     var callback = ""
}