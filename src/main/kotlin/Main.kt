import controllers.MemberAPI
import models.Member
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

private val memberAPI = MemberAPI()
fun main(args: Array<String>) {
    runMenu()
}
fun mainMenu(): Int {
    return ScannerInput.readNextInt(
        """
            >Manage Member 
            >1. Create the member
            >2. List all the members
            >3. Update member information
            >4. delete member
            >5. Upgrade membership
            >
            >Manage Bike
            >6. add bike to a member
            >7. update bike statue 
            >8. return the bike
         > ==>> """.trimMargin(">")
    )
}
fun runMenu(){
    do {
        val option = mainMenu()
        when(option){
            1 -> addMember()
            2 -> listMembers()
            3 -> updateMembers()
            4 -> deleteMember()
            5 -> upgradeMembership()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}
fun addMember(){
    val memberName = ScannerInput.readNextLine("Enter a Member name: ")
    val memberContact = ScannerInput.readNextInt("Enter a Member contact: ")
    val memberAddress = ScannerInput.readNextLine("Enter a Member address: ")
    val isAdded = memberAPI.add(Member(memberName, memberContact, memberAddress, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
fun listMembers() {
    println(memberAPI.listMember())
}
fun updateMembers(){
    listMembers()
    if (memberAPI.numberOfMembers() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the member to update: ")
        if (memberAPI.isValidIndex(indexToUpdate)) {
            val memberName = readNextLine("Enter a name:" )
            val memberContact = readNextInt("Enter number: ")
            val memberAddress = readNextLine("Enter address: ")
            if(memberAPI.updateMember(indexToUpdate, Member(memberName,memberContact,memberAddress,false))) {
                println("$memberName information updated")
            } else{
                println("update failed")
            }
        } else{
            println("There are no member for this index number ")
        }
    }
}
fun deleteMember(){
    listMembers()
    if (memberAPI.numberOfMembers() > 0) {
        val indexToDelete = readNextInt("Enter the index of the member to delete: ")
        val memberToDelete = memberAPI.deleteMember(indexToDelete)
        if (memberToDelete != null) {
            "${memberToDelete.memberName} deleted"
        } else{
            println("Delete is not successful")
        }
    }
}
fun upgradeMembership(){
    listMembers()
    if (memberAPI.numberOfMembers() > 0) {
        val indexToUpgrade = readNextInt("Enter the index of the member to upgrade: ")
        if(memberAPI.isValidIndex(indexToUpgrade)) {
            memberAPI.upgradeMembership(indexToUpgrade)
               println("VIP enroll successed")
            } else {
                println("Upgrade is not successful")
            }
        } else {
            println("There are no member")
        }
    }

    fun exitApp() {
        System.exit(0)
    }
