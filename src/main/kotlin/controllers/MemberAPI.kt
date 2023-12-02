package controllers

import models.Member

class MemberAPI {

    private var members = arrayListOf<Member>()
    private fun formatListString(membersToFormat: List<Member>): String =
        membersToFormat.joinToString(separator = "\n") { member ->
            members.indexOf(member).toString() + ": " + member.toString()
        }

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD methods for member arraylist
    // ----------------------------------------------
    fun add(member: Member): Boolean {
        member.memberId = getId()
        return members.add(member)
    }
    fun updateMember(id: Int, member: Member?): Boolean {
        val foundMember = findMember(id)
        if ((foundMember != null) && (member != null)) {
            foundMember.memberName = member.memberName
            foundMember.memberContact = member.memberContact
            foundMember.memberAddress = member.memberAddress
            return true
        }
        return false
    }
    fun upgradeMembership(id: Int): Boolean {
        val foundMember = findMember(id)
        if ((foundMember != null) && (!foundMember.isMemberVIP)) {
            foundMember.isMemberVIP = true
            return true
        }
        return false
    }
    fun deleteMember(id: Int) = members.removeIf { member -> member.memberId == id }

    // ----------------------------------------------
    //  Listing methods for member arraylist
    // ----------------------------------------------
    fun listMember() =
        if (members.isEmpty()) {
            "no members stored"
        } else {
            formatListString(members)
        }
    fun listVIPs() =
        if (numberOfVIPs() == 0) {
            "No members stored"
        } else {
            formatListString(members.filter { member: Member -> member.isMemberVIP })
        }
    fun listNormalMembers() =
        if (numberOfNormalMembers() == 0) {
            "No members stored"
        } else {
            formatListString(members.filter { member: Member -> !member.isMemberVIP })
        }

    // ----------------------------------------------
    //  COUNTING METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun numberOfMembers() = members.size
    fun numberOfVIPs() = members.count { member: Member -> member.isMemberVIP }
    fun numberOfNormalMembers() = members.count { member: Member -> !member.isMemberVIP }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ----------------------------------------------
    fun findMember(id: Int) = members.find { member -> member.memberId == id }

    fun searchMembersByName(searchString: String) =
        formatListString(
            members.filter { member -> member.memberName.contains(searchString, ignoreCase = true) }
        )
}
