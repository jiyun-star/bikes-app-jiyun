import controllers.MemberAPI
import models.Member
import persistence.JSONSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import java.io.File

private val memberAPI = MemberAPI(JSONSerializer(File("Members.json")))
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
            >5. Make member VIP (update isvip attribute)
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
            4 -> deleteMember()
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
fun listMembers(){
    memberAPI.listMember()
}
fun deleteMember(){
    listMembers()
    if (memberAPI.numberOfmembers() > 0) {
        val indexToDelete = readNextInt("Enter the index of the member to delete: ")
        val memberToDelete = memberAPI.deleteMember(indexToDelete)
        if (memberToDelete != null) {
            "${memberToDelete.memberName} deleted"
        } else{
            println("Delete is not successful")
        }
    }
}


    fun exitApp() {
        System.exit(0)
    }
