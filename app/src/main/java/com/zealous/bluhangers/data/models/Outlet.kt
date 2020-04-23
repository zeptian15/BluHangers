package com.zealous.bluhangers.data.models

import com.google.firebase.database.Exclude

data class Outlet(
    @get:Exclude
    var id: Int? = null,
    var name: String? = null,
    var address: String? = null,
    var phone: String? = null,
    var photo: String? = null
)