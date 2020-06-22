package uk.co.renbinden.onrails.assets

import org.w3c.dom.Audio
import uk.co.renbinden.ilse.asset.AnimationAsset
import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.asset.SoundAsset
import uk.co.renbinden.ilse.asset.TextAsset

class Assets {

    inner class Images {
        val backgroundStart = ImageAsset("static/images/start_bg_full.png")
        val backgroundStartTile = ImageAsset("static/images/start-bg.gif")
        val backgroundStars1 = ImageAsset("static/images/background_stars_1.png")
        val backgroundStars2 = ImageAsset("static/images/background_stars_2.png")
        val title = ImageAsset("static/images/on_rails_logo.png")
        val titleHover = ImageAsset("static/images/on_rails_logo_hover.png")
        val buttonStart = ImageAsset("static/images/start_start.png")
        val buttonStartHover = ImageAsset("static/images/start_hover.png")
        val backgroundStation1 = ImageAsset("static/images/bg1.jpg")
        val backgroundStation2 = ImageAsset("static/images/dawn_station_bg.jpg")
        val backgroundStation3 = ImageAsset("static/images/grey_station_bg.jpg")
        val backgroundStation4 = ImageAsset("static/images/love_station_bg.jpg")
        val portraitAngelaFunikular = ImageAsset("static/images/portrait_angela_funikular.png")
        val portraitDianaBogie = ImageAsset("static/images/portrait_diana_bogie.png")
        val portraitJasonHardrail = ImageAsset("static/images/portrait_jason_hardrail.png")
        val portraitLuluMomentis = ImageAsset("static/images/portrait_lulu_momentis.png")
        val portraitMauriceSourth = ImageAsset("static/images/portrait_maurice_sourth.png")
        val textBoxBackground = ImageAsset("static/images/textbox_background.png")
        val nameBoxBackground = ImageAsset("static/images/namebox_background.png")
        val optionBackground = ImageAsset("static/images/option_background.png")
        val optionHoverBackground = ImageAsset("static/images/option_hover_background.png")
        val verticalTrackTile = ImageAsset("static/images/verticaltracktile.png")
        val horizontalTrackTile = ImageAsset("static/images/horizontaltracktile.png")
        val northOrEastPoints = ImageAsset("static/images/northoreastpoints.png")
        val northOrEastPointsB = ImageAsset("static/images/northoreastpointsB.png")
        val northOrWestPoints = ImageAsset("static/images/northorwestpoints.png")
        val northOrWestPointsB = ImageAsset("static/images/northorwestpointsB.png")
        val northToEastTrackTile = ImageAsset("static/images/northtoeasttracktile.png")
        val northToWestTrackTile = ImageAsset("static/images/northtowesttracktile.png")
        val southOrEastPoints = ImageAsset("static/images/southoreastpoints.png")
        val southOrEastPointsB = ImageAsset("static/images/southoreastBpoints.png")
        val southOrWestPoints = ImageAsset("static/images/southorwestpoints.png")
        val southOrWestPointsB = ImageAsset("static/images/westorsouthpoints.png")
        val southToEastTrackTile = ImageAsset("static/images/southtoeasttracktile.png")
        val southToWestTrackTile = ImageAsset("static/images/southtowesttracktile.png")
        val trainNorth = AnimationAsset("static/images/trainnorth.png", 128, 128)
        val trainSouth = AnimationAsset("static/images/trainsouth.png", 128, 128)
        val trainLeft = AnimationAsset("static/images/trainleft.png", 128, 128)
        val trainRight = AnimationAsset("static/images/trainright.png", 128, 128)
        val jubilanceBubble = AnimationAsset("static/images/jubilance_bubble.png", 64, 64)
        val miseryBubble = AnimationAsset("static/images/misery_bubble.png", 64, 64)
        val animosityBubble = AnimationAsset("static/images/animosity_bubble.png", 64, 64)
        val intimacyBubble = AnimationAsset("static/images/intimacy_bubble.png", 64, 64)
        val steam = ImageAsset("static/images/steam.png")
        val levelSelectButton = ImageAsset("static/images/level_select_button.png")
        val levelSelectButtonHover = ImageAsset("static/images/level_select_button_hover.png")
        val blueStation = ImageAsset("static/images/bluestation.png")
        val greenStation = ImageAsset("static/images/greenstation.png")
        val redStation = ImageAsset("static/images/redstation.png")
        val yellowStation = ImageAsset("static/images/yellowstation.png")
    }

    inner class Maps {
        val overworld = TextAsset("static/maps/tilemap_Overworld.tmx")
        val level2 = TextAsset("static/maps/level2.tmx")
    }

    inner class Sounds {
        val beep = SoundAsset("static/sounds/Beep.wav")
        val bubbleGet = SoundAsset("static/sounds/Bubbleget.mp3")
        val dreamtrainBlues = Audio("static/sounds/Dreamtrain_Blues.mp3").also { it.loop = true }
        val funktionJunktionStation = Audio("static/sounds/Funktion_Junction_Station.mp3").also { it.loop = true }
        val levelEnd = SoundAsset("static/sounds/Level_End.mp3")
        val steamCannon1 = SoundAsset("static/sounds/Steam_Cannon_1.wav")
        val steamCannon2 = SoundAsset("static/sounds/Steam_Cannon_2.wav")
        val steamCannon3 = SoundAsset("static/sounds/Steam_Cannon_3.wav")
        val trackSwitch = SoundAsset("static/sounds/trackswitch.mp3")
        val whistle = SoundAsset("static/sounds/Whistle.mp3")
    }

    val images = Images()
    val maps = Maps()
    val sounds = Sounds()

}