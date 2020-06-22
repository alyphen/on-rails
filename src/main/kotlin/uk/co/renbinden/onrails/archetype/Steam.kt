package uk.co.renbinden.onrails.archetype

import uk.co.renbinden.ilse.collision.RectangleCollider
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.collision.Collider
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.timer.TimerTask
import uk.co.renbinden.onrails.velocity.Velocity

object Steam {
    operator fun invoke(engine: Engine, assets: Assets, x: Double, y: Double) = entity {
        add(Position(x, y))
        add(Velocity(0.0, 0.0))
        add(Bounds(64.0, 64.0))
        add(Image(assets.images.steam))
        add(Depth(-3))
        add(Collider(RectangleCollider(
            get(Position)::x,
            get(Position)::y,
            get(Bounds)::width,
            get(Bounds)::height
        )))
        add(TimerTask(0.5) {
            engine.remove(this)
        })
        add(uk.co.renbinden.onrails.steam.Steam())
    }
}