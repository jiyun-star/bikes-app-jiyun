package models





data class Member(var memberName: String,
                  var memberContact: Int,
                  var memberAddress: String,
                  var isMemberVIP: Boolean = false,
                  var bikes : MutableSet<Bike> = mutableSetOf())
    {

    private var lastBikeId = 0
    private fun getBikeId() = lastBikeId++
    fun numberOfBikes() = bikes.size
    fun findOne(id: Int): Bike? {
        return bikes.find { bike -> bike.bikeId == id }
    }

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

}