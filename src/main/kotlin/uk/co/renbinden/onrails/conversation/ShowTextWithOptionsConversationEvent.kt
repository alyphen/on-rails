package uk.co.renbinden.onrails.conversation

import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.action.Action
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.avatar.Avatar
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.fillstyle.FillStyle
import uk.co.renbinden.onrails.font.Font
import uk.co.renbinden.onrails.hover.HoverImage
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.text.Text

class ShowTextWithOptionsConversationEvent(
    val assets: Assets,
    val conversationTimeline: ConversationTimeline,
    val speaker: Avatar?,
    val text: String,
    val options: Array<out String>
) : ConversationEvent {
    override fun invoke(engine: Engine) {
        engine.entities.filter { it.has(Text) }.forEach(engine::remove)
        if (speaker != null) {
            engine.add(entity {
                add(Position(32.0, 456.0))
                add(Text(speaker.name, 256.0))
                add(FillStyle("rgb(255, 255, 255)"))
                add(Font("20px 'Chelsea Market', cursive"))
            })
            if (!engine.entities.any { it.has(Image) && it[Image].asset == assets.images.nameBoxBackground }) {
                engine.add(entity {
                    add(Image(assets.images.nameBoxBackground))
                    add(Depth(-1))
                    add(Position(16.0, 448.0))
                })
            }
        } else {
            engine.entities
                .filter { it.has(Image) && it[Image].asset == assets.images.nameBoxBackground }
                .forEach(engine::remove)
        }
        engine.add(entity {
            add(Position(32.0, 504.0))
            add(Text(text, 736.0))
            add(FillStyle("rgb(255, 255, 255)"))
            add(Font("20px 'Chelsea Market', cursive"))
        })

        val startY = 480 - (options.size * 48)
        val optionEntities = options.mapIndexed { i, option -> entity {
            add(Position(528.0, startY + (i * 48.0)))
            add(Image(assets.images.optionBackground))
            add(HoverImage(assets.images.optionBackground, assets.images.optionHoverBackground))
            add(Depth(-1))
            add(Bounds(256.0, 32.0))
        }}
        optionEntities.forEach { optionEntity ->
            optionEntity.add(Action {
                optionEntities.forEach(engine::remove)
                conversationTimeline.progress(engine)
            })
            engine.add(optionEntity)
        }
        options.mapIndexed { i, option -> entity {
            add(Position(544.0, startY + 8 + (i * 48.0)))
            add(Text(option, 256.0))
            add(FillStyle("rgb(255, 255, 255)"))
            add(Font("20px 'Chelsea Market', cursive"))
        }}.forEach(engine::add)
    }
}