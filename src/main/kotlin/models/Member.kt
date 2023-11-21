package models

data class Member(
    var memberId: Int = 0,
    var memberName: String,
    var memberContact: String,
    var memberAddress: String,
    var isMemberVIP: Boolean = false
    )