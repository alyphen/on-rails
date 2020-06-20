package uk.co.renbinden.onrails.conversation

import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.avatar.Avatar
import uk.co.renbinden.onrails.avatar.AvatarName
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position

class CreateAvatarConversationEvent(
    val engine: Engine,
    val avatar: Avatar,
    val x: Double,
    val y: Double
) : ConversationEvent {
    override fun invoke() {
        engine.add(entity {
            add(Image(avatar.asset, 1))
            add(AvatarName(avatar.name))
            add(Position(x, y))
        })
    }
}