package uk.co.renbinden.onrails.dreamstats

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion

class DreamStats : Component {
    val emotions = mutableMapOf<DreamBubbleEmotion, Int>()
    companion object : ComponentMapper<DreamStats>(DreamStats::class)
}