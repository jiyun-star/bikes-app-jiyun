package controllers

import models.Member

class MemberAPI {

    private var members = arrayListOf<Member>()
    private fun formatListString(membersToFormat : List<Member>) : String =
        membersToFormat.joinToString (separator = "\n") { member ->
            members.indexOf(member).toString() + ": " + member.toString() }
    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++
    // ----------------------------------------------
    //  CRUD methods for member arraylist
    // ----------------------------------------------
    fun add(member: Member) : Boolean{
        return members.add(member)
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
    // ----------------------------------------------
    //  Listing methods for member arraylist
    // ----------------------------------------------
    fun listMember() =
         if (members.isEmpty()) "no members stored"
         else formatListString(members)
    fun listVIPs() =
        if (numberOfVIPs() ==0) "No VIP members"
        else formatListString(members.filter {member: Member -> member.isMemberVIP})
    fun listNormalMembers() =
        if (numberOfNormalMembers() == 0) "No normal members"
        else formatListString(members.filter {member: Member -> !member.isMemberVIP})
    // ----------------------------------------------
    //  COUNTING METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun numberOfMembers() = members.size
    fun numberOfVIPs() = members.count{member: Member -> member.isMemberVIP }
    fun numberOfNormalMembers() = members.count{member: Member -> !member.isMemberVIP  }
    // ----------------------------------------------
    //  SEARCHING METHODS
    // ----------------------------------------------
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