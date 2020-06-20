package uk.co.renbinden.onrails.conversation

import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.avatar.Avatar

class ConversationTimeline(val engine: Engine, val assets: Assets) {
    val events = mutableListOf<ConversationEvent>()
    private var eventIndex = 0

    fun progress() {
        while (eventIndex < events.size && events[eventIndex] !is ShowTextConversationEvent) {
            events[eventIndex++]()
        }
        if (eventIndex < events.size) {
            events[eventIndex++]()
        }
    }

    fun createAvatar(avatar: Avatar, x: Double, y: Double) {
        events.add(CreateAvatarConversationEvent(engine, avatar, x, y))
    }

    fun removeAvatar(avatar: Avatar) {
        events.add(RemoveAvatarConversationEvent(engine, avatar))
    }

    fun moveAvatar(avatar: Avatar, x: Double, y: Double, animationTime: Double) {
        events.add(MoveAvatarConversationEvent(engine, avatar, x, y, animationTime))
    }

    fun showText(speaker: Avatar?, message: String) {
        events.add(ShowTextConversationEvent(engine, assets, speaker, message))
    }
}

fun timeline(engine: Engine, assets: Assets, init: ConversationTimeline.() -> Unit): ConversationTimeline {
    val timeline = ConversationTimeline(engine, assets)
    timeline.init()
    return timeline
}