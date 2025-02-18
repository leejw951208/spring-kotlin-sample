package demo.kotlinboilerplate.member.domain

import demo.kotlinboilerplate.member.persistence.entity.MemberEntity

class Member(
    val id: Long?,
    var email: String,
    var password: String,
    var name: String,
) {
}