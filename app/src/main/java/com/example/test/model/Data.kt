
package com.example.test.model
import java.time.LocalDateTime

data class Data(
    var id: Long?=0,
    var username: String?=null,
    var password: String?=null,
    var email: String?=null,
    var role: String?=null, //?
    var updateAt: LocalDateTime?=null,
    var createdAt: LocalDateTime?=null
)