
package com.example.test.model
import java.time.LocalDateTime

data class Data(
    var id: Long,
    var username: String,
    var password: String,
    var email: String?,
    var role: String, //?
    var updateAt: LocalDateTime?,
    var createdAt: LocalDateTime?
)