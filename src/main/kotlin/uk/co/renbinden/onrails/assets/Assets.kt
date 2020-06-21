package uk.co.renbinden.onrails.assets

import uk.co.renbinden.ilse.asset.AnimationAsset
import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.asset.TextAsset

class Assets {

    inner class Images {
        val title = ImageAsset("static/images/title.png")
        val buttonStart = ImageAsset("static/images/button_start.png")
        val backgroundStation1 = ImageAsset("static/images/bg1.jpg")
        val portraitAngelaFunikular = ImageAsset("static/images/portrait_angela_funikular.png")
        val portraitJasonHardrail = ImageAsset("static/images/portrait_jason_hardrail.png")
        val textBoxBackground = ImageAsset("static/images/textbox_background.png")
        val nameBoxBackground = ImageAsset("static/images/namebox_background.png")
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
    }

    inner class Maps {
        val map1 = TextAsset("static/maps/map1.tmx")
    }

    val images = Images()
    val maps = Maps()

}