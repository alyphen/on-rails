package uk.co.renbinden.onrails.position

class OffsetPosition(
    val basePosition: Position,
    val xOffset: Double,
    val yOffset: Double
) {
    var x: Double
        get() = basePosition.x + xOffset
        set(value) {
            basePosition.x = value - xOffset
        }

    var y: Double
        get() = basePosition.y + yOffset
        set(value) {
            basePosition.y = value - yOffset
        }
}