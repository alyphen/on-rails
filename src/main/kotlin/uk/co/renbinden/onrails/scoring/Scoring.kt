package uk.co.renbinden.onrails.scoring

import kotlin.browser.window

object Scoring {
    fun setScore(level: Int, score: Int) {
        if (score > getScore(level)) {
            window.localStorage.setItem("score_$level", score.toString())
        }
    }

    fun getScore(level: Int): Int {
        return window.localStorage.getItem("score_$level")?.toIntOrNull() ?: 0
    }

    fun getTotalScore(levels: Int): Int {
        return (1..levels).sumBy(::getScore)
    }

}