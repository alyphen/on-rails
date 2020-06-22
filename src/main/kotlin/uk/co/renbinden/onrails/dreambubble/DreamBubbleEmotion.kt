package uk.co.renbinden.onrails.dreambubble

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

enum class DreamBubbleEmotion : Component {

    JUBILANCE,
    MISERY,
    ANIMOSITY,
    INTIMACY;

    companion object : ComponentMapper<DreamBubbleEmotion>(DreamBubbleEmotion::class)
}