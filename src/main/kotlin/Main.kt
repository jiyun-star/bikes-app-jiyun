import utils.ScannerInput

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
            >
            >Report Member
            >9. search for all members(by name)
            >
            >Report Bike
            >10. search for all bikes(by end date)
         > ==>> """.trimMargin(">")
    )
}

    fun runMenu(){
        do {
            val option = mainMenu()
            when(option){
                0 -> exitApp()
                else -> System.out.println("Invalid option entered: ${option}")
            }
        } while (true)
    }
    fun exitApp() {
        System.exit(0)
    }
