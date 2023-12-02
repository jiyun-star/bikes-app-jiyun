import controllers.MemberAPI
import models.Bike
import models.Member
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine

private val memberAPI = MemberAPI()
fun main() = runMenu()
fun runMenu() {
    do {
        when (val option = mainMenu()) {
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
            else -> System.out.println("Invalid option entered: $option")
        }
    } while (true)
}
fun mainMenu() = readNextInt(
    """

██████╗░██╗██╗░░██╗███████╗░░░░░░░░██████╗░███████╗███╗░░██╗████████╗░█████╗░██╗░░░░░░░░░░░░░░█████╗░██████╗░██████╗░
██╔══██╗██║██║░██╔╝██╔════╝░░░░░░░░██╔══██╗██╔════╝████╗░██║╚══██╔══╝██╔══██╗██║░░░░░░░░░░░░░██╔══██╗██╔══██╗██╔══██╗
██████╦╝██║█████═╝░█████╗░░░░░░░░░░██████╔╝█████╗░░██╔██╗██║░░░██║░░░███████║██║░░░░░░░░░░░░░███████║██████╔╝██████╔╝
██╔══██╗██║██╔═██╗░██╔══╝░░░░░░░░░░██╔══██╗██╔══╝░░██║╚████║░░░██║░░░██╔══██║██║░░░░░░░░░░░░░██╔══██║██╔═══╝░██╔═══╝░
██████╦╝██║██║░╚██╗███████╗░░░░░░░░██║░░██║███████╗██║░╚███║░░░██║░░░██║░░██║███████╗░░░░░░░░██║░░██║██║░░░░░██║░░░░░
╚═════╝░╚═╝╚═╝░░╚═╝╚══════╝░░░░░░░░╚═╝░░╚═╝╚══════╝╚═╝░░╚══╝░░░╚═╝░░░╚═╝░░╚═╝╚══════╝░░░░░░░░╚═╝░░╚═╝╚═╝░░░░░╚═╝░░░░░
>
            >🧑𝕞𝕒𝕟𝕒𝕘𝕖 𝕞𝕖𝕞𝕓𝕖𝕣🧑           
            >  1. Create the member
            >  2. List all the members
            >  3. Update member information
            >  4. Delete member
            >  5. Upgrade membership
            >
            >🚲𝕞𝕒𝕟𝕒𝕘𝕖 𝕓𝕚𝕜𝕖🚲
            >  6. Rent a bike
            >  7. Extend rental time
            >  8. Return the bike
            > 
            >📝𝕣𝕖𝕡𝕠𝕣𝕥📝
            >  10. Search for all members(by names)
            >
            >  0. EXIT🚪🏃
         > ==>> """.trimMargin(">")
)

// ----------------------------------------------
//  Member menu
// ----------------------------------------------
fun addMember() {
    val memberName = readNextLine("Enter a Member name: ")
    val memberContact = readNextInt("Enter a Member contact: ")
    val memberAddress = readNextLine("Enter a Member address: ")
    val isAdded = memberAPI.add(Member(memberName = memberName, memberContact = memberContact, memberAddress = memberAddress))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
fun listMembers() {
    if (memberAPI.numberOfMembers() > 0) {
        val option = readNextInt(
            """
                >1. List ALL members 🧑,⭐🧑⭐
                >2. List VIP members ⭐🧑⭐
                >3. List normal members 🧑
                >==>> """.trimMargin(">")
        )
        when (option) {
            1 -> listAllMembers()
            2 -> listVIPs()
            3 -> listNormalMembers()
            else -> println("Invalid option entered")
        }
    } else {
        println("Option invalid - No members stored")
    }
}
fun listAllMembers() = println(memberAPI.listMember())
fun listVIPs() = println(memberAPI.listVIPs())
fun listNormalMembers() = println(memberAPI.listNormalMembers())

fun updateMembers() {
    listAllMembers()
    if (memberAPI.numberOfMembers() > 0) {
        val id = readNextInt("Enter the index of the member to update: ")
        if (memberAPI.findMember(id) != null) {
            val memberName = readNextLine("Enter a name:")
            val memberContact = readNextInt("Enter number: ")
            val memberAddress = readNextLine("Enter address: ")
            if (memberAPI.updateMember(id, Member(0, memberName, memberContact, memberAddress, false))) {
                println("$memberName information updated")
            } else {
                println("update failed")
            }
        } else {
            println("There are no member for this index number ")
        }
    }
}
fun deleteMember() {
    listAllMembers()
    if (memberAPI.numberOfMembers() > 0) {
        val id = readNextInt("Enter the index of the member to delete: ")
        val memberToDelete = memberAPI.deleteMember(id)
        if (memberToDelete) {
            println("deleted successful")
        } else {
            println("Delete is not successful")
        }
    }
}
fun upgradeMembership() {
    listNormalMembers()
    if (memberAPI.numberOfNormalMembers() > 0) {
        val id = readNextInt("Enter the index of the member to upgrade: ")
        if (memberAPI.upgradeMembership(id)) {
            println("VIP enroll succeed")
        } else {
            println("Upgrade is not successful")
        }
    } else { println("There are no member") }
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

// ----------------------------------------------
//  bike menu
// ----------------------------------------------
private fun addBike() {
    val member: Member? = askUserToChooseMember()
    if (member != null) {
        if (member.addBike(
                Bike(
                        bikeColor = readNextLine("bike color(R,G,B):"),
                        bikeSize = readNextInt("bike size(16,18,20): "),
                        startDate = readNextLine("start date(yyyy-mm-dd): "),
                        endDate = readNextLine("end date(yyyy-mm-dd): ")
                    )
            )
        ) {
            println("Bike rental successed")
        } else {
            println("You didnt get bike")
        }
    }
}

fun extendBike() {
    val member: Member? = askUserToChooseMember()
    if (member != null) {
        val bike: Bike? = askUserToChooseBike(member)
        if (bike != null) {
            val newEndDate = readNextLine("Enter the new delay date(yyyy-mm-dd): ")
            if (member.update(bike.bikeId, Bike(bike.bikeId, bike.bikeColor, bike.bikeSize, bike.startDate, endDate = newEndDate))) {
                println("Bike got delayed")
            } else {
                println("Failed")
            }
        }
    }
}

fun returnBike() {
    val member: Member? = askUserToChooseMember()
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

// ----------------------------------------------
//  helper
// ----------------------------------------------
private fun askUserToChooseMember(): Member? {
    listMembers()
    if (memberAPI.numberOfMembers() > 0) {
        val member = memberAPI.findMember(readNextInt("Enter the member id: "))
        if (member != null) {
            return member
        } else {
            println("Member is not valid")
        }
    }
    return null
}
private fun askUserToChooseBike(member: Member): Bike? {
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
