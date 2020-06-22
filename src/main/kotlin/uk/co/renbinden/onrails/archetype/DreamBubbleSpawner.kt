package uk.co.renbinden.onrails.archetype

import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion
import uk.co.renbinden.onrails.timer.TimerTask

object DreamBubbleSpawner {
    operator fun invoke(engine: Engine, assets: Assets, emotion: DreamBubbleEmotion, x: Double, y: Double, time: Double) = entity {
        spawnBubbleIn(time, engine, assets, emotion, x, y)
    }

    private fun Entity.spawnBubbleIn(time: Double, engine: Engine, assets: Assets, emotion: DreamBubbleEmotion, x: Double, y: Double) {
        add(TimerTask(time) {
            engine.add(DreamBubble(assets, emotion, x, y))
            spawnBubbleIn(time, engine, assets, emotion, x, y)
        })
    }
}