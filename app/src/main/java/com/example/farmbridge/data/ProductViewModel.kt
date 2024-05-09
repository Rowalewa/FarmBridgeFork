package com.example.myapplication.data

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.view.Gravity
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.farmbridge.data.AuthViewModel
import com.example.farmbridge.models.Delivery
import com.example.farmbridge.models.Product
import com.example.farmbridge.navigation.ROUTE_ADD_PRODUCT
import com.example.farmbridge.navigation.ROUTE_LOGIN
import com.example.farmbridge.navigation.ROUTE_VIEW_PRODUCT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ProductViewModel(var navController: NavHostController, var context: Context) {
    var authRepository: AuthViewModel
    var progress: ProgressDialog

    init {
        authRepository = AuthViewModel(navController, context)
        if (!authRepository.isloggedin()) {
            navController.navigate(ROUTE_LOGIN)
        }
        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
    }


    fun saveProduct(
        productName: String,
        productQuantity: String,
        productPrice: String,
        filePath: Uri,
    ) {
        val productId = System.currentTimeMillis().toString()
        val storageReference = FirebaseStorage.getInstance().getReference().child("Products/$productId")
        storageReference.putFile(filePath).addOnCompleteListener{
            progress.show()
            if (
                productName.isBlank()|| productQuantity.isBlank()|| productPrice.isBlank()
            ) {
                progress.dismiss()
                Toast.makeText(context, "Fill all the fields please", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_ADD_PRODUCT)
            } else if (it.isSuccessful){
                progress.dismiss()
                // Proceed to store other data into the db
                storageReference.downloadUrl.addOnSuccessListener {
                    val roomImageUrl = it.toString()
                    val houseData = Product(
                        productName,
                        productQuantity,
                        productPrice,
                        roomImageUrl,
                        productId
                    )
                    val dbRef = FirebaseDatabase.getInstance().getReference().child("Products/$productId")
                    dbRef.setValue(houseData)
                    val toast = Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    navController.navigate(ROUTE_ADD_PRODUCT)
                }
            } else {
                progress.dismiss()
                Toast.makeText(context, "ERROR: ${it.exception!!.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun viewProduct(
        product: MutableState<Product>,
        products: SnapshotStateList<Product>
    ): SnapshotStateList<Product> {
        val ref = FirebaseDatabase.getInstance().getReference().child("Products")

        //progress.show()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //progress.dismiss()
                products.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Product::class.java)
                    product.value = value!!
                    products.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }

    fun deleteProduct(productId: String) {
        var delRef = FirebaseDatabase.getInstance().getReference()
            .child("Products/$productId")
        progress.show()
        delRef.removeValue().addOnCompleteListener {
            progress.dismiss()
            if (it.isSuccessful) {
                Toast.makeText(context, "Product deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateProduct(
        productName: String,
        productQuantity: String,
        productPrice: String,
        productId: String,
        filePath: Uri?
    ) {
        val storageReference = FirebaseStorage.getInstance().getReference().child("Products/$productId")

        val updateData = Product(
            productName,
            productQuantity,
            productPrice,
            "",
            productId
        )

        // Update book details in Firebase Realtime Database
        if (filePath != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference().child("Products/$productId")
            dbRef.setValue(updateData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // If an image file is provided, update the image in Firebase Storage
                    filePath.let { fileUri ->
                        storageReference.putFile(fileUri).addOnCompleteListener { storageTask ->
                            if (storageTask.isSuccessful) {
                                storageReference.downloadUrl.addOnSuccessListener { uri ->
                                    val imageUrl = uri.toString()
                                    updateData.productImageUrl = imageUrl
                                    dbRef.setValue(updateData) // Update the book entry with the image URL
                                }
                            } else {
                                Toast.makeText(context, "Upload Failure", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    // Show success message
                    Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                    navController.navigate(ROUTE_VIEW_PRODUCT)
                } else {
                    // Handle database update error
                    Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else{
            val dbRef = FirebaseDatabase.getInstance().getReference().child("Rooms/$productId")
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val product = snapshot.getValue(Product::class.java)
                    val existingImageUrl = product?.productImageUrl ?: ""

                    val updateData = Product(
                        productName,
                        productQuantity,
                        productPrice,
                        existingImageUrl,
                        productId
                    )

                    dbRef.setValue(updateData).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Show success message
                            Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                            navController.navigate(ROUTE_VIEW_PRODUCT)
                        } else {
                            // Handle database update error
                            Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                    Toast.makeText(context, "ERROR: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
            )
            Toast.makeText(context, "Success with Image Retained", Toast.LENGTH_LONG).show()
        }
    }
    fun makeDelivery(
        userId: String,
        productId: String,
        location: String
    ) {
        val deliveryData = Delivery(userId, productId, location)
        val deliveryRef = FirebaseDatabase.getInstance().getReference().child("Delivery").child(userId)
//        progress.show()
        deliveryRef.setValue(deliveryData).addOnCompleteListener {
//            progress.dismiss()
            if (it.isSuccessful) {
                Toast.makeText(context, "Delivery in a few minutes", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(context, "ERROR: ${it.exception!!.message}", Toast.LENGTH_SHORT)
                    .show()


            }
        }
    }}


