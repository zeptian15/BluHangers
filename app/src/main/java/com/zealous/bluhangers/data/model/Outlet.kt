package com.zealous.bluhangers.data.model

import android.net.Uri
import com.google.firebase.database.Exclude

data class Outlet(
    @get:Exclude
    var id: String? = null,
    var name: String? = null,
    var address: String? = null,
    var phone: String? = null,
    var image: String? = null,
    var isDeleted: Boolean = false
){
    override fun equals(other: Any?): Boolean {
        return if(other is Outlet){
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        return result
    }

}