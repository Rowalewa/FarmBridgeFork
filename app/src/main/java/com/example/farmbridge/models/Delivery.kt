package com.example.farmbridge.models


class Delivery {
    var userId: String = ""
    var productId: String = ""
    var location: String = ""

    constructor(
        userId: String,
        productId: String,
        location: String
    ){
        this.userId = userId
        this.productId = productId
        this.location = location
    }
    constructor()
}