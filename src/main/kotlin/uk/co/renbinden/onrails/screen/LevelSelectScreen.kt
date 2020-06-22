package uk.co.renbinden.onrails.screen

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.ilse.app.screen.Screen
import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.asset.TextAsset
import uk.co.renbinden.ilse.ecs.engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.ilse.event.Events
import uk.co.renbinden.ilse.event.Listener
import uk.co.renbinden.ilse.input.event.MouseDownEvent
import uk.co.renbinden.onrails.action.Action
import uk.co.renbinden.onrails.archetype.LevelSelectButton
import uk.co.renbinden.onrails.archetype.LevelSelectButtonText
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.avatar.Avatars
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.conversation.ConversationTimeline
import uk.co.renbinden.onrails.conversation.timeline
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion.*
import uk.co.renbinden.onrails.fillstyle.FillStyle
import uk.co.renbinden.onrails.font.Font
import uk.co.renbinden.onrails.hover.HoverSystem
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.renderer.*
import uk.co.renbinden.onrails.scoring.Scoring
import uk.co.renbinden.onrails.text.Text
import kotlin.browser.document
import kotlin.browser.window

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class LevelSelectScreen(val app: App, val assets: Assets) : Screen(engine {}) {

    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    val pipeline = RenderPipeline(
        BaseRenderer(canvas, ctx),
        SolidBackgroundRenderer(canvas, ctx, "#000000"),
        BackgroundImageRenderer(canvas, ctx, assets.images.backgroundStart),
        ImageRenderer(canvas, ctx, engine),
        TextRenderer(canvas, ctx, engine)
    )

    private val mouseDownListener = Listener<MouseDownEvent>({ event ->
        val mouseX = event.pageX - (canvas.getBoundingClientRect().left + window.scrollX)
        val mouseY = event.pageY - (canvas.getBoundingClientRect().top + window.scrollY)
        engine.entities
            .filter { entity ->
                if (!entity.has(Position) || !entity.has(Bounds) || !entity.has(Action)) return@filter false
                val position = entity[Position]
                val bounds = entity[Bounds]
                return@filter position.x <= mouseX && position.y <= mouseY
                        && position.x + bounds.width >= mouseX && position.y + bounds.height >= mouseY
            }
            .forEach { entity -> entity[Action].onAction() }
    })

    init {
        engine.add(HoverSystem(canvas))

        engine.add(LevelSelectButtonText("1", 154.0, 320.0))
        val avatars = Avatars(assets)
        engine.add(LevelSelectButton(assets, 128.0, 300.0) {
            removeListeners()
            assets.sounds.beep.play()
            level(
                1,
                assets.images.backgroundStation1,
                timeline(assets) {
                    createAvatar(avatars.jasonHardrail, 500.0, 100.0)
                    createAvatar(avatars.angelaFunikular, 16.0, 100.0)
                    showText(avatars.jasonHardrail, "Yo, this is message 1. This is cool, huh?")
                    showText(avatars.angelaFunikular, "This is message 2. Pretty neat?")
                    showText(null, "This message doesn't have a speaker. So we can describe what's going on.")
                    showTextWithOptions(avatars.jasonHardrail, "You wanna make a choice? I got a bunch of choices!", "Choice A", "Choice B", "Choice C")
                },
                assets.images.backgroundStation2,
                timeline(assets) {
                    createAvatar(avatars.dianaBogie, 500.0, 100.0)
                    showText(avatars.dianaBogie, "And that's the end")
                },
                assets.maps.overworld,
                mapOf(
                    JUBILANCE to 4,
                    MISERY to 0,
                    ANIMOSITY to 3,
                    INTIMACY to 0
                )
            )
        })
        engine.add(entity {
            add(Text("Level 1 score: ${Scoring.getScore(1)}", 256.0))
            add(FillStyle("rgb(255, 255, 255)"))
            add(Font("16px 'Chelsea Market', cursive"))
            add(Position(98.0, 396.0))
        })
        engine.add(entity {
            add(Text("Total score: ${Scoring.getTotalScore(1)}", 256.0))
            add(FillStyle("rgb(255, 255, 255)"))
            add(Font("16px 'Chelsea Market', cursive"))
            add(Position(350.0, 500.0))
        })

        addListeners()
    }

    fun level(level: Int, startBackground: ImageAsset, startConversation: ConversationTimeline, endBackground: ImageAsset, endConversation: ConversationTimeline, map: TextAsset, emotions: Map<DreamBubbleEmotion, Int>) {
        app.screen = ConversationScreen(app, assets, startBackground, startConversation) {
            app.screen = LevelScreen(app, assets, level, map, endBackground, endConversation, emotions)
        }
    }

    fun addListeners() {
        Events.addListener(MouseDownEvent, mouseDownListener)
    }

    fun removeListeners() {
        Events.removeListener(MouseDownEvent, mouseDownListener)
    }

    override fun onRender() {
        pipeline.onRender()
    }
}