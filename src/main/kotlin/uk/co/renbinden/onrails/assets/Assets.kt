package uk.co.renbinden.onrails.assets

import uk.co.renbinden.ilse.asset.ImageAsset

class Assets {

    inner class Images {
        val title = ImageAsset("static/images/title.png")
        val buttonStart = ImageAsset("static/images/button_start.png")
        val backgroundStation = ImageAsset("static/images/background_station.png")
        val portraitAngelaFunikular = ImageAsset("static/images/portrait_angela_funikular.png")
        val portraitJasonHardrail = ImageAsset("static/images/portrait_jason_hardrail.png")
        val textBoxBackground = ImageAsset("static/images/textbox_background.png")
    }

    val images = Images()

}