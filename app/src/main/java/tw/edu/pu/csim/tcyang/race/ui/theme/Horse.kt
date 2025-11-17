package tw.edu.pu.csim.tcyang.race.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

class Horse(val number: Int) {

    // 必須使用 mutableStateOf，這樣 UI 才能在數值改變時重繪
    var horseX by mutableStateOf(0)

    // Y 座標根據編號分佈，讓每匹馬有自己的軌道
    // 假設馬匹 Y 座標從 100 開始，每條軌道間隔 250 單位
    var horseY by mutableStateOf(100 + number * 250)

    // 隨機速度，讓比賽看起來更真實
    // 速度介於 2 到 6 之間 (Int)
    private val speed = Random.nextInt(2, 7)

    /**
     * 更新馬匹的 X 座標
     */
    fun HorseRun() {
        horseX += speed
    }
}