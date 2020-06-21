package uk.co.renbinden.onrails.assets

import uk.co.renbinden.ilse.asset.AnimationAsset
import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.asset.TextAsset

class Assets {

    inner class Images {
        val backgroundStart = ImageAsset("static/images/start_bg_full.png")
        val title = ImageAsset("static/images/on_rails_logo.png")
        val titleHover = ImageAsset("static/images/on_rails_logo_hover.png")
        val buttonStart = ImageAsset("static/images/start_start.png")
        val buttonStartHover = ImageAsset("static/images/start_hover.png")
        val backgroundStation1 = ImageAsset("static/images/bg1.jpg")
        val portraitAngelaFunikular = ImageAsset("static/images/portrait_angela_funikular.png")
        val portraitJasonHardrail = ImageAsset("static/images/portrait_jason_hardrail.png")
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
    }

    inner class Maps {
        val map1 = TextAsset("static/maps/map1.tmx")
        val overworld = TextAsset("static/maps/tilemap_Overworld.tmx")
    }

    val images = Images()
    val maps = Maps()

}