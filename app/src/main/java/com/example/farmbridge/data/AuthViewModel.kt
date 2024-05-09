package com.example.farmbridge.data

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.example.farmbridge.models.User
import com.example.farmbridge.navigation.ROUTE_HOME
import com.example.farmbridge.navigation.ROUTE_LOGIN
import com.example.farmbridge.navigation.ROUTE_REGISTER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuthViewModel(var navController: NavController, var context: Context){

    var mAuth: FirebaseAuth
    val progress: ProgressDialog

    init {
        mAuth= FirebaseAuth.getInstance()
        progress= ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("PLease Wait.....")
    }
    fun signup(
        firstname:String,
        lastname:String,
        email:String,
        pass:String,
        confirmpass:String
    ){
        // progress.show()
        if (firstname.isBlank()|| lastname.isBlank()|| email.isBlank() || pass.isBlank() ||confirmpass.isBlank()){
            //progress.dismiss()
            Toast.makeText(context,"Please email and password cannot be blank", Toast.LENGTH_LONG).show()
            return
        }else if (pass != confirmpass){
            Toast.makeText(context,"Password do not match", Toast.LENGTH_LONG).show()
            return
        }else{
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                if (it.isSuccessful){
                    val userId = System.currentTimeMillis().toString()
                    val userdata= User(firstname, lastname, email,pass,mAuth.currentUser!!.uid)
                    val regRef= FirebaseDatabase.getInstance().getReference()
                        .child("Users/$userId")
                    regRef.setValue(userdata).addOnCompleteListener {

                        if (it.isSuccessful){
                            Toast.makeText(context,"Registered Successfully", Toast.LENGTH_LONG).show()
                            navController.navigate("$ROUTE_HOME/$userId")

                        }else{
                            Toast.makeText(context,"${it.exception!!.message}", Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_LOGIN)
                        }
                    }
                }else{
                    navController.navigate(ROUTE_REGISTER)
                }

            } }

    }

    private fun getUserIdByEmail(email: String, callback: (String?) -> Unit) {
        val ref = FirebaseDatabase.getInstance().getReference().child("Users")
        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val userId = snap.key // Assuming the client ID is the key of the client node
                        callback(userId)
                        return
                    }
                }
                callback(null) // Client ID not found
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Client", "Error fetching user ID: ${error.message}")
                callback(null) // Handle the error by returning null
            }
        })
    }
    fun login(
        email: String,
        pass: String
    ){
        progress.show()
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
            progress.dismiss()
            getUserIdByEmail(email) { userId ->
                if (userId != null) {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Successfully Logged in", Toast.LENGTH_LONG).show()
                        navController.navigate("$ROUTE_HOME/$userId")
//                navController.navigate(ROUTE_REGISTER)TO TAKE YOU TO A DIIFFERNT PAGE
                    } else {
                        Toast.makeText(context, "${it.exception!!.message}", Toast.LENGTH_LONG)
                            .show()
                        navController.navigate(ROUTE_LOGIN)
                    }
                } else {
                    Toast.makeText(context, "User is null", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    fun logout(){
        mAuth.signOut()
        navController.navigate(ROUTE_LOGIN)
    }
    fun isloggedin():Boolean{
        return mAuth.currentUser !=null
    }

}