package uk.co.renbinden.onrails.conversation

import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.avatar.Avatar
import uk.co.renbinden.onrails.avatar.AvatarName
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position

class CreateAvatarConversationEvent(
    val avatar: Avatar,
    val x: Double,
    val y: Double
) : ConversationEvent {
    override fun invoke(engine: Engine) {
        engine.add(entity {
            add(Image(avatar.asset))
            add(Depth(1))
            add(AvatarName(avatar.name))
            add(Position(x, y))
        })
    }
}