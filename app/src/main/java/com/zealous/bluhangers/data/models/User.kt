package com.zealous.bluhangers.data.models

import com.google.firebase.database.Exclude

data class User (
    @get:Exclude
    var id: String? = null,
    var fullName: String? = null,
    var nickName: String? = null,
    var email: String? = null,
    var id_outlet: Int? = null,
    var role: String? = null
)