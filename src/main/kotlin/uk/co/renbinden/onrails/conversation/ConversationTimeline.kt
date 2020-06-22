package uk.co.renbinden.onrails.conversation

import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.avatar.Avatar

class ConversationTimeline(val assets: Assets) {
    val events = mutableListOf<ConversationEvent>()
    private var eventIndex = 0
    val currentEvent: ConversationEvent
        get() = events[eventIndex - 1]

    fun progress(engine: Engine) {
        while (eventIndex < events.size && events[eventIndex] !is ShowTextConversationEvent && events[eventIndex] !is ShowTextWithOptionsConversationEvent) {
            events[eventIndex++](engine)
        }
        if (eventIndex < events.size) {
            events[eventIndex++](engine)
        }
    }

    fun createAvatar(avatar: Avatar, x: Double, y: Double) {
        events.add(CreateAvatarConversationEvent(avatar, x, y))
    }

    fun removeAvatar(avatar: Avatar) {
        events.add(RemoveAvatarConversationEvent(avatar))
    }

    fun moveAvatar(avatar: Avatar, x: Double, y: Double, animationTime: Double) {
        events.add(MoveAvatarConversationEvent(avatar, x, y, animationTime))
    }

    fun showText(speaker: Avatar?, message: String) {
        events.add(ShowTextConversationEvent(assets, speaker, message))
    }

    fun showTextWithOptions(speaker: Avatar?, message: String, vararg options: String) {
        events.add(ShowTextWithOptionsConversationEvent(assets, this, speaker, message, options))
    }

    fun execute(action: () -> Unit) {
        events.add(ActionConversationEvent(action))
    }
}

fun timeline(assets: Assets, init: ConversationTimeline.() -> Unit): ConversationTimeline {
    val timeline = ConversationTimeline(assets)
    timeline.init()
    return timeline
}