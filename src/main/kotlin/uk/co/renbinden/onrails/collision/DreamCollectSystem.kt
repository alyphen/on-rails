package uk.co.renbinden.onrails.collision

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion
import uk.co.renbinden.onrails.dreamstats.DreamStats
import uk.co.renbinden.onrails.train.Train

class DreamCollectSystem : IteratingSystem({
    it.has(Train) && it.has(Collider)
}) {
    override fun processEntity(entity: Entity, dt: Double) {
        val others = engine.entities.filter { it != entity && it.has(Collider) && it.has(DreamBubbleEmotion) }.associateBy { it[Collider].collider }
        val collider = entity[Collider]
        val collisions = collider.collider.getCollisions(others.keys.toList()).mapNotNull(others::get)
        val dreamStats = entity[DreamStats]
        collisions.forEach { dreamBubble ->
            val emotion = dreamBubble[DreamBubbleEmotion]
            dreamStats.emotions[emotion] = (dreamStats.emotions[emotion] ?: 0) + 1
            engine.remove(dreamBubble)
        }
    }
}