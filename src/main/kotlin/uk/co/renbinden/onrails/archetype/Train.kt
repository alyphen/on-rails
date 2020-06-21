package uk.co.renbinden.onrails.archetype

import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.animation.Animation
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.velocity.Velocity

object Train {
    operator fun invoke(assets: Assets, x: Double, y: Double) = entity {
        add(Animation(assets.images.trainNorth, 0.2))
        add(Depth(-1))
        add(Position(x, y))
        add(Velocity(0.0, -128.0))
        add(uk.co.renbinden.onrails.train.Train())
    }
}