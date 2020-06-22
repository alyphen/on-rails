package uk.co.renbinden.onrails.archetype

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.animation.Animation
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion.*
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.timer.TimerTask
import uk.co.renbinden.onrails.velocity.Velocity
import kotlin.random.Random

object DreamBubble {
    operator fun invoke(assets: Assets, emotion: DreamBubbleEmotion, x: Double, y: Double) = entity {
        when (emotion) {
            JUBILANCE -> add(Animation(assets.images.jubilanceBubble, 0.2))
            MISERY -> add(Animation(assets.images.miseryBubble, 0.2))
            ANIMOSITY -> add(Animation(assets.images.animosityBubble, 0.2))
            INTIMACY -> add(Animation(assets.images.intimacyBubble, 0.2))
        }
        add(Depth(-2))
        add(Position(x, y))
        add(Velocity(Random.nextDouble(-64.0, 64.0), Random.nextDouble(1.0, 128.0)))
        changeDirectionIn(2.0)
        add(Bounds(64.0, 64.0))
        add(emotion)
    }

    private fun Entity.changeDirectionIn(time: Double) {
        add(TimerTask(time) {
            val velocity = this[Velocity]
            velocity.dx = Random.nextDouble(-64.0, 64.0)
            velocity.dy = Random.nextDouble(-64.0, 64.0)
            changeDirectionIn(2.0)
        })
    }
}