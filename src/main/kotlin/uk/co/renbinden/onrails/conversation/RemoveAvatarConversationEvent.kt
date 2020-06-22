package uk.co.renbinden.onrails.conversation

import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.onrails.avatar.Avatar
import uk.co.renbinden.onrails.avatar.AvatarName

data class RemoveAvatarConversationEvent(val avatar: Avatar) : ConversationEvent {
    override fun invoke(engine: Engine) {
        engine.entities.filter { it.has(AvatarName) && it[AvatarName].name == avatar.name }.forEach(engine::remove)
    }
}