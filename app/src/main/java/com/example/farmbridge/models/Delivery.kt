package com.example.farmbridge.models


class Delivery {
    var userid: String = ""
    var location: String = ""
    var deliveryId: String = ""

    constructor(
        userid: String,
        location: String,
        deliveryId: String
    ){
        this.userid = userid
        this.location = location
        this.deliveryId = deliveryId
    }
    constructor()
}