package models

data class Member(var memberId: Int =0,
                  var memberName: String,
                  var memberContact: Int,
                  var memberAddress: String,
                  var isMemberVIP: Boolean = false,
                  var bikes : MutableSet<Bike> = mutableSetOf())
    {
    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastBikeId = 0
    private fun getBikeId() = lastBikeId++

    fun numberOfBikes() = bikes.size
    fun findOne(id: Int): Bike? {
        return bikes.find { bike -> bike.bikeId == id }
    }
        // ----------------------------------------------
        //  CRUD for bike
        // ----------------------------------------------
    fun addBike(bike: Bike): Boolean {
        bike.bikeId = getBikeId()
        return bikes.add(bike)
    }

    fun delete(id: Int): Boolean {
        return bikes.removeIf { bike -> bike.bikeId == id }
    }

    fun update(id: Int, newBike: Bike): Boolean {
        val foundBike = findOne(id)
        if (foundBike != null) {
            foundBike.endDate = newBike.endDate
            return true
        }
        return false
    }

    fun listBikes() =
        if (bikes.isEmpty()) "NO BIKES ADDED"
        else utils.Utilities.formatSetString(bikes)

    override fun toString(): String {
        val vip = if(isMemberVIP) "✨" else ' '
        return "$vip$memberName$vip ☎($memberContact),🏠︎($memberAddress) \n ${listBikes()}"
    }
}