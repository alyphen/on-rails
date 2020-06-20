package uk.co.renbinden.onrails.renderer

import org.w3c.dom.*
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.onrails.fillstyle.FillStyle
import uk.co.renbinden.onrails.font.Font
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.text.Text

class TextRenderer(
    val canvas: HTMLCanvasElement,
    val ctx: CanvasRenderingContext2D,
    val engine: Engine
) : Renderer {
    override fun onRender() {
        engine.entities
            .filter { it.has(Position) && it.has(Text) && it.has(FillStyle) && it.has(Font) }
            .forEach { entity ->
                val (x, y) = entity[Position]
                val (text, maxWidth) = entity[Text]
                val (fillStyle) = entity[FillStyle]
                val (font) = entity[Font]
                ctx.textAlign = CanvasTextAlign.LEFT
                ctx.textBaseline = CanvasTextBaseline.TOP
                ctx.font = font
                ctx.fillStyle = fillStyle
                val lines = getLines(
                    ctx,
                    text,
                    maxWidth
                )
                for ((i, line) in lines.withIndex()) {
                    ctx.fillText(
                        line,
                        x,
                        y + (24 * i),
                        maxWidth
                    )
                }
            }
    }

    private fun getLines(ctx: CanvasRenderingContext2D, text: String, maxWidth: Double): List<String> {
        val wordsFirstPass = text.split(" ")
        val words = mutableListOf<String>()
        for (word in wordsFirstPass) {
            if (word.contains("\n")) {
                val splitWords = word.split("\n").map { splitWord -> "$splitWord\n" }.toMutableList()
                splitWords[splitWords.size - 1] = splitWords[splitWords.size - 1].replace("\n", "")
                words.addAll(splitWords)
            } else {
                words.add(word)
            }
        }
        val lines = mutableListOf<String>()
        if (words.isEmpty()) return lines
        var currentLine = ""
        for (word in words) {
            val width = ctx.measureText("$currentLine $word").width
            if (width >= maxWidth) {
                lines.add(currentLine)
                currentLine = word.replace("\n", "")
            } else {
                currentLine += if (currentLine.isEmpty()) {
                    word.replace("\n", "")
                } else {
                    " ${word.replace("\n", "")}"
                }
            }
            if (word.endsWith("\n")) {
                lines.add(currentLine)
                currentLine = ""
            }
        }
        lines.add(currentLine)
        return lines
    }

}