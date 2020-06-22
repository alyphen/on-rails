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
                    createAvatar(avatars.jasonHardrail, 450.0, 100.0)
                    showText(avatars.jasonHardrail, "Hey, you're finally asleep! Glad you made it!")
                    showTextWithOptions(avatars.jasonHardrail,"How you feelin', newbie?", "Somehow...still tired. Also, what.", "Pretty good. Also, what.")
                    showText(avatars.jasonHardrail, "Haha, yeah it takes a lot to get used to. Bet you're a natural though.")
                    showText(null, "The stranger gives you a warm smile, and his teeth twinkle in the starlight.")
                    showText(avatars.jasonHardrail, "Welcome to Junction Function Interpsyche Freightway. Call me Jason Hardrail.")
                    showTextWithOptions(avatars.jasonHardrail, "Y'see, everyone dreams right? Well you ever think about where those dreams come from?", "The brain sorting through memories?", "Visions from a higher power?")
                    showText(avatars.jasonHardrail, "Pssh, nah. They come from Dreamrealm, which is where we're at right now. Pretty fancy huh?")
                    showText(avatars.jasonHardrail, "You're one of the cool cats who've been selected to help people get the dreams they need. Welcome to your new job, newbie.")
                    showTextWithOptions(avatars.jasonHardrail, "You with me so far?", "Hey, I didn't sign up for this!", "Right on, how do we do it?")
                    showText(avatars.jasonHardrail, "Yeah, I hear ya I hear ya. We don't really get a say in who gets the job, but I gotta say it's a pretty sweet gig. And you get your own ride.")
                    showText(avatars.jasonHardrail, "Speakin' of, we gotta get you set up. You seem funky, but we both got work to do, so listen up newbie.")
                    showText(null, "Jason gets this real intense look all of a sudden.")
                    showText(avatars.jasonHardrail, "You're now a conductor of your very own Dreamtrain! You're gonna ride the rails of Dreamrealm, and give your passengers the perfect dreams!")
                    showText(avatars.jasonHardrail, "Don't worry, kid, it's waaay easier than it sounds. The engine'll move forward on it's own. When you're reaching a junction, hold either A or D depending on if you wanna go left or right.")
                    showText(avatars.jasonHardrail, "Out there in the tracks, you'll find a bunch of floatin' emotions called Dream Bubbles. These wild vibes are out to make your dreams real good, or real bad depending.")
                    showText(avatars.jasonHardrail, "Each passenger will need a different kinda dream. Everybody needs somethin', right? So there's different kinds of Dream Bubbles that you'll need to grab for them.")
                    showText(avatars.jasonHardrail, "To draw those Dream Bubbles towards your train, blow the WHISTLE with RIGHT MOUSE BUTTON. This'll attract all kinds of bubbles towards you like a magnet. Now...")
                    showText(avatars.jasonHardrail, "Some vibes out there you don't want. You gotta blow them away! Fire your STEAM CANNON with LEFT MOUSE BUTTON. Use the mouse to aim, too, y'dig?")
                    showText(avatars.jasonHardrail, "Oh, and some tracks'll be better for certain vibes than others, so once you learn your rails you'll be able to give way better dreams.")
                    showTextWithOptions(avatars.jasonHardrail, "Aaaaaaalright. That's pretty much it. That all make sense, kid?", "Kinda?", "...No!")
                    showText(avatars.jasonHardrail, "That's the spirit. Anyway, lets get your first job, lemme check who's up first!")
                    showText(null, "For a few seconds, Jason's eyes roll back into his head and he floats several inches off the platform floor.")
                    showText(avatars.jasonHardrail, "Alright, looks like you're startin' with one of our very own JFIF employees. She'll be nice. Hang around here for a bit, she'll be along soon. Catch you around kid!")
                    showText(null, "Jason Hardrail strikes a pose and yells at the top of his lungs, teleporting away with what you can only assume is Dreamrealm funk magic.")
                    removeAvatar(avatars.jasonHardrail)
                    createAvatar(avatars.luluMomentis, 450.0, 100.0)
                    showText(avatars.luluMomentis, "Uuuh, hey? You're that new conductor right? Jason told me about you with his mind powers.")
                    showText(avatars.luluMomentis, "I'm Lulu Momentis, I work in transcendental metaphoricology, over near finance. Buuuut, today's my day off, so I ain't getting near that damn engine.")
                    showText(avatars.luluMomentis, "I'm looking to have a pretty happy dream, alright? Not too fussed about the intensity, so long as its good vibes all the way down.")
                    showText(avatars.luluMomentis, "Oh, right. Jason probably forgot to mention. There are FOUR TYPES of Dream Bubble, and they come in opposite pairs.")
                    showText(avatars.luluMomentis, "You got YELLOW JUBILANCE and BLUE MISERY, which are self explanatory; Jubilance for happy dreams, Misery for sad ones.")
                    showText(avatars.luluMomentis, "You also RED ANIMOSITY and GREEN INTIMACY. These affect the temperament of the dream.")
                    showText(avatars.luluMomentis, "Just focus on getting those Jubilance bubbles, and make sure you blast any Blue Misery away with your steam cannon.")
                    showTextWithOptions(avatars.luluMomentis, "Alright I really gotta dream. Let's get you on rails!", "Let's go!", "Choo choo, motherfucker!")
                    showTextWithOptions(null, "Board the train to Dream Town?", "Yes.", "Yes.",)
                },
                assets.images.backgroundStation2,
                timeline(assets) {
                    createAvatar(avatars.luluMomentis, 450.0, 100.0)
                    showTextWithOptions(avatars.luluMomentis, "Hey, not bad out there! Considering it was your first time, be proud! Rails are pretty wild huh?", "Yeah, I nailed it!", "I dunno, I could've done better.")
                    showText(avatars.luluMomentis, "Just keep at it, yeah? You got this.")
                    showText(avatars.luluMomentis, "I gotta go wake up now, but I'll let Jason know how ya did, I'm sure he'll wanna hear about how our new conductor's skills.")
                    showTextWithOptions(avatars.luluMomentis, "See ya around!", "Thanks, Lulu!", "Haha, later!,")
                    showText(null, "Lulu Momentis waves slowly fades away.")
                    showText(null, "LEVEL COMPLETE")
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

        engine.add(LevelSelectButtonText("2", 390.0, 320.0))
        engine.add(LevelSelectButton(assets, 368.0, 300.0) {
            removeListeners()
            assets.sounds.beep.play()
            level(
                2,
                assets.images.backgroundStation2,
                timeline(assets) {

                createAvatar(avatars.dianeBogie, 450.0, 100.0)
                createAvatar(avatars.angelaFunikular, 450.0, 100.0)
                showText(null, "After falling asleep, you arrive back in Dreamrealm once again. Your engine waits for you at the station.")
                showText(null, "There's a seriously looking woman on the platform...oh know she's looking this way!")
                showTextWithOptions(avatars.angelaFunikular, "...................................................Hm." "Uh....hi?", "Good evening, ma'am.")
                showText(avatars.angelaFunikular, "My name..............is Angela Funikular. You are working for..........my company...")
                showText(avatars.angelaFunikular, "...")
                showText(avatars.angelaFunikular, "...")
                showText(avatars.angelaFunikular, "...")
                showText(avatars.angelaFunikular, "...")
                showText(avatars.angeulaFunikular, "...Junction Function Interpsyche Freightway. We are a...proud company. I expect you to give your very best on this and every job.")
                showText(avatars.angelaFunikular, "Do you think you can.........handle it?", "Y...yeah.", "Yes Ma'am!",)
                showText(avatars.angelaFunikular, "Groovy. I will....see you.......................................................again.")
                showText(null, "Well, that was intense.")
                showText(null, "Looks like there's a passenger waiting over there. Better take where they need to go.")
                showTextWithOptions(avatars.dianeBogie, "U-uhm...hi...I was wondering if you'd be able to help me?", "Sure can!", "I can certainly try.",)
                showText(avatars.dianeBogie,"T...That's great. My name's Diane Bogie, and I...look I'm in kind of a mess right now, and...I dunno. It feels like I could really just feeling my feelings.")
                showTextWithOptions(avatars.dianeBogie,"You get what I mean?", "Go on...?", "I think so. Catharsis, right?")
                showText(avatars.dianeBogie,"Yeah, so. I guess...can you make it like...sad? And super aggressive? That's what I've been feeling. Just a bunch of sad, bad and angry feelings.")
                showText(avatars.dianeBogie, "Is that something you can do?", "Sure can.", "Of course.",)
                showText(avatars.dianeBogie, "Thanks. This'll be good for me I think.")
                showText(null, "Diane smiles gratefully as you both board the train.")


                },
                assets.images.backgroundStation3,
                timeline(assets) {
                createAvatar(avatars.dianeBogie, 450.0, 100.0)
                createAvatar(avatars.jasonHardrail, 450.0, 100.0)
                showTextWithOptions(avatars.dianeBogie, "Diane doesn't say much, but she smiles at you as she disappears." "Say goodbye.", "Just smile back.",)
                showText(null, "As she leaves, you hear a loud crack of thunder.")
                showText(avatars.jasonHardrail, "Heeeeeeey newbie! I'm back!")
                showText(null, "Jason is back.")
                showText(avatars.jasonHardrail, "Hahaaaa, how'd it GO!? Lulu said you were pretty good!", "I think I'm getting the hang of it" "What ARE you?")
                showText(avatars.jasonHardrail, "Yeah, I can dig it. Alright, you must be pretty awake now you've got some rails under your hat!")
                showText(avatars.jasonHardrail, "You did great out there. Hope to see you around the JFIF more, kid! See you around!")
                showText(null, "Jason folds up like an origami doll and explodes into confetti.")
                showText(null, "END OF LEVEL")
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
            add(Text("Level 2 score: ${Scoring.getScore(2)}", 256.0))
            add(FillStyle("rgb(255, 255, 255)"))
            add(Font("16px 'Chelsea Market', cursive"))
            add(Position(336.0, 396.0))
        })

        engine.add(entity {
            add(Text("Total score: ${Scoring.getTotalScore(2)}", 256.0))
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
