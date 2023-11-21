package models

data class Member(
    var memberId: Int = 0,
    var memberName: String,
    var memberContact: String,
    var memberAddress: String,
    var isMemberVIP: Boolean = false // vip 혜택은 자전거 한번에 3개가능, vip아니면 한번에 한개
    )