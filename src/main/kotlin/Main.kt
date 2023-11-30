import controllers.MemberAPI
import models.Bike
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
            > 1. Create the member
            > 2. List all the members
            > 3. Update member information
            > 4. delete member
            > 5. Upgrade membership
            >
            >Manage Bike
            > 6. rent a bike
            > 7. extend rental time
            > 8. return the bike
            > 
            >Report Member
            > 10. Search for all members(by names)
            > 
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
            6 -> addBike()
            7 -> extendBike()
            8 -> returnBike()
            10 -> searchMember()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}
///////////////////Member menu/////////////////////
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

fun searchMember() {
    val searchName = readNextLine("Enter the name of number: ")
    val searchResults = memberAPI.searchMembersByName(searchName)
    if (searchResults.isEmpty()) {
        println("No members found")
    } else {
        println(searchResults)
    }

}
//////////////////bike menu////////////////

private fun addBike() {
    val member: Member? = askUserToChooseMemeber()
    if (member != null) {
        if (member.addBike(Bike(
                bikeColor = readNextLine("bike color:"),
                bikeSize = readNextInt("bike size: "),
                startDate = readNextLine("start date: "),
                endDate = readNextLine("end date: ")
            )))
            println("Bike rental successed")
        else println("You didnt get bike")
    }
}

fun extendBike() {
    val member: Member? = askUserToChooseMemeber()
    if (member != null){
        val bike: Bike? = askUserToChooseBike(member)
        if(bike != null) {
            val newEndDate = readNextLine("Enter the new delay date: ")
            if (member.update(bike.bikeId, Bike(bike.bikeId,bike.bikeColor,bike.bikeSize,bike.startDate, endDate = newEndDate))) {
                println("Bike got delayed")
            } else {
                println("Failed")
            }
        }
    }
}

fun returnBike() {
    val member: Member? = askUserToChooseMemeber()
    if (member != null) {
        val bike: Bike? = askUserToChooseBike(member)
        if (bike != null) {
            val isReturned = member.delete(bike.bikeId)
            if (isReturned) {
                println("Bike returned successful!")
            } else {
                println("Bike returned failed")
            }
        }

    }
}


//////////////helper
private fun askUserToChooseMemeber(): Member? {
    listMembers()
    if (memberAPI.numberOfMembers() > 0) {
        val member = memberAPI.findMember(readNextInt("Enter the member id: "))
        if (member != null) return member
        else println("Member is not valid")
    }
    return null
}
private fun askUserToChooseBike(member: Member) : Bike? {
    if (member.numberOfBikes() > 0) {
        print(member.listBikes())
        return member.findOne(readNextInt("\nEnter the id of the bike: "))
    } else {
        println("No bikes for chosen member")
        return null
    }
}

fun exitApp() {
    System.exit(0)
}
