package com.example.farmbridge.models

class User {
    var firstname: String =""
    var lastname: String =""
    var email: String =""
    var pass:String =""
    var userid:String =""

    constructor(firstname:String,
                lastname:String,
                email:String,
                pass:String,
                userid:String){
        this.firstname=firstname
        this.lastname=lastname
        this.email=email
        this.pass=pass
        this.userid=userid
    }
    constructor()

}
