package models

data class Bike(
    var bikeId: Int = 0,
    var bikeColor: String,
    var bikeSize: Int,
    var startDate: String,
    var endDate: String
) {
    override fun toString(): String {
        return "$bikeId : $bikeColor color, $bikeSize inch, $startDate ~ $endDate \n"
    }
}
