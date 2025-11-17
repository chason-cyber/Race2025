package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tw.edu.pu.csim.tcyang.race.ui.theme.Horse

class GameViewModel: ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(false)

    // *** 新增狀態: 儲存獲勝訊息 ***
    var winnerMessage by mutableStateOf("")

    var circleX by mutableStateOf(100f)
        private set
    var circleY by mutableStateOf(100f)
        private set

    var score by mutableStateOf(0)

    val horses = mutableListOf<Horse>()

    // ... (SetGameSize 函式保持不變) ...
    fun SetGameSize(w: Float, h: Float) {
        if (screenWidthPx == 0f || screenHeightPx == 0f || w != screenWidthPx || h != screenHeightPx) {
            screenWidthPx = w
            screenHeightPx = h

            if (horses.isEmpty()) {
                for (i in 0..2){
                    horses.add(Horse(i))
                }
            }
        }
    }

    fun increaseScore() {
        score++
    }

    /**
     * 啟動遊戲迴圈 (包含勝負判定邏輯)
     */
    fun StartGame() {
        if (screenHeightPx > 0f) {
            circleX = screenWidthPx / 2f
            circleY = screenHeightPx - 100f
        } else {
            circleX = 300f
            circleY = 500f
        }

        // *** 初始化：清空勝利訊息，重置所有馬匹位置 ***
        winnerMessage = ""
        horses.forEach { it.horseX = 0 }

        gameRunning = true
        viewModelScope.launch {
            while (gameRunning) {
                delay(16)

                // 玩家圓形邊界檢查/得分邏輯
                if (circleX >= screenWidthPx - 100f){
                    increaseScore()
                    circleX = 100f
                }

                var winnerIndex = -1 // 用於記錄獲勝馬匹的索引

                // 遍歷所有馬匹並更新位置和檢查邊界
                for (i in 0..2){
                    // 只有在還沒有勝者時才讓馬匹跑動
                    if (winnerMessage.isEmpty()) {
                        horses[i].HorseRun()
                    }

                    // 檢查是否抵達終點線 (假設終點線為 screenWidthPx - 200)
                    if (horses[i].horseX >= screenWidthPx - 200) {
                        if (winnerIndex == -1) {
                            winnerIndex = i + 1 // 記錄獲勝者 (第 1, 2, 3 匹馬)
                            // 設置勝利訊息
                            winnerMessage = "第${winnerIndex}馬獲勝"

                            // 立即重置所有馬匹位置到起點 (X軸設為 0)
                            horses.forEach { horse ->
                                horse.horseX = 0
                            }
                            // 增加玩家分數 (可選，根據遊戲規則決定是否給玩家加分)
                            // increaseScore()

                            // 停止遊戲迴圈 (或準備進入下一回合)
                            gameRunning = false
                            break // 找到勝者後跳出迴圈
                        }
                    }
                }
            }
        }
    }
    // ... (MoveCircle 函式保持不變) ...
    fun MoveCircle(x: Float, y: Float) {
        val newX = circleX + x
        val newY = circleY + y

        if (newX >= 100f && newX <= screenWidthPx - 100f) {
            circleX = newX
        }
        if (newY >= 100f && newY <= screenHeightPx - 100f) {
            circleY = newY
        }
    }
}