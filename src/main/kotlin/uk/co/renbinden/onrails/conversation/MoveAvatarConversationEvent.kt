package uk.co.renbinden.onrails.conversation

import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.onrails.avatar.Avatar
import uk.co.renbinden.onrails.avatar.AvatarName
import uk.co.renbinden.onrails.path.Path
import uk.co.renbinden.onrails.position.Position
import kotlin.math.max

data class MoveAvatarConversationEvent(
    val avatar: Avatar,
    val endX: Double,
    val endY: Double,
    val animationTime: Double
) : ConversationEvent {
    override fun invoke(engine: Engine) {
        val avatarEntity = engine.entities.firstOrNull { it.has(AvatarName) && it[AvatarName].name == avatar.name } ?: return
        if (avatarEntity.has(Path)) {
            avatarEntity.remove(Path)
        }
        val position = avatarEntity[Position]
        val startX = position.x
        val startY = position.y
        avatarEntity.add(Path(
            { t -> ((max(t, animationTime) / animationTime) * endX) + ((1 - (max(t, animationTime) / animationTime)) * startX) },
            { t -> ((max(t, animationTime) / animationTime) * endY) + ((1 - (max(t, animationTime) / animationTime)) * startY) }
        ))
    }
}