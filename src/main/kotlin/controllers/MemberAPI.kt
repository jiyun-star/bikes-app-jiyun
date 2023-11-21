package controllers

import models.Member

class MemberAPI {
    private var members = arrayListOf<Member>()
    private fun formatListString(MembersToFormat : List<Member>) : String =
        MembersToFormat
            .joinToString (separator = "\n") { member ->
                members.indexOf(member).toString() + ": " + member.toString() }



    fun add(member: Member) : Boolean{
        return members.add(member)
    }

    fun listMember() : String{
        return formatListString(members)
    }

  /*
    fun updateMember(indexToUpdate: Int, member: Member?) : Boolean{}
   */

    fun deleteMember(indexToDelete : Int): Member? {
        return if (isValidListIndex(indexToDelete, members)){
            members.removeAt(indexToDelete)
        } else null
    }

    fun numberOfmembers(): Int {
        return members.size
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, members);
    }
}