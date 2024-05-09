package com.example.farmbridge.models

class Product{
    var productName: String=""
    var productQuantity: String=""
    var productPrice: String=""
    var productImageUrl: String = ""
    var productId: String=""

    constructor(
        productName: String,
        productQuantity: String,
        productPrice: String,
        productImageUrl: String,
        productId: String
    ){
        this.productName = productName
        this.productQuantity = productQuantity
        this.productPrice = productPrice
        this.productImageUrl = productImageUrl
        this.productId = productId

    }
    constructor()
}