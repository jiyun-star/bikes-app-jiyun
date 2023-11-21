package controllers

import models.Member

class MemberAPI {
    private var members = arrayListOf<Member>()
    private fun formatListString(notesToFormat : List<Member>) : String =
        notesToFormat
            .joinToString (separator = "\n") { member ->
                members.indexOf(member).toString() + ": " + member.toString() }



    fun addMember(member: Member) : Boolean{
        return members.add(member)
    }

    fun listMember() : String{
        return formatListString(members)
    }

  /*
    fun updateMember(indexToUpdate: Int, member: Member?) : Boolean{}
   */

    fun deleteMember(indexToDelete : Int): Member {
        return members.removeAt(indexToDelete)
    }
}