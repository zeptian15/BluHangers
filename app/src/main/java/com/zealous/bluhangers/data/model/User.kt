package com.zealous.bluhangers.data.model

import com.google.firebase.database.Exclude

data class User (
    @get:Exclude
    var id: String? = null,
    var fullName: String? = null,
    var nickName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var id_outlet: String? = null,
    var role: String? = null
)