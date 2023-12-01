package controllers

import models.Member

class MemberAPI {

    private var members = arrayListOf<Member>()
    private fun formatListString(membersToFormat : List<Member>) : String =
        membersToFormat.joinToString (separator = "\n") { member ->
            members.indexOf(member).toString() + ": " + member.toString() }

    fun add(member: Member) : Boolean{
        return members.add(member)
    }

    fun listMember() : String {
        return if (members.isEmpty()) {
            "no members stored"
        } else {
            formatListString(members)
        }
    }

    fun updateMember(indexToUpdate: Int, member: Member?) : Boolean{
        val foundMember = findMember(indexToUpdate)
        if((foundMember !=null)&& (member != null)){
            foundMember.memberName = member.memberName
            foundMember.memberContact = member.memberContact
            foundMember.memberAddress = member.memberAddress
            return true
        }
        return false
    }

    fun upgradeMembership(indexToUpgrade: Int): Boolean{
        val foundMember = findMember(indexToUpgrade)
        if(foundMember != null) {
            foundMember.isMemberVIP = true
        }
        return false
        }


    fun deleteMember(indexToDelete : Int): Member? {
        return if (isValidListIndex(indexToDelete, members)){
            members.removeAt(indexToDelete)
        } else null
    }

    fun numberOfMembers(): Int {
        return members.size
    }
    fun findMember(index: Int): Member? {
        return if (isValidListIndex(index, members)) {
            members[index]
        } else null
    }

    fun searchMembersByName(searchString : String) =
        formatListString(
            members.filter { member -> member.memberName.contains(searchString, ignoreCase = true)}
        )

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, members);
    }
}