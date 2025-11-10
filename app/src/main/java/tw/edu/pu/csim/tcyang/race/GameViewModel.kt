package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(false)

    var circleX by mutableStateOf(0f)
        private set
    var circleY by mutableStateOf(0f)
        private set


    var score by mutableStateOf(0)


    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
    }


    fun increaseScore() {
        score++
    }

    fun StartGame() {
        circleX = 100f
        circleY = screenHeightPx - 100f


        gameRunning = true

        viewModelScope.launch {
            while (gameRunning) {
                delay(100)
                circleX += 10f


                if (circleX >= screenWidthPx - 100f){
                    increaseScore()
                    circleX = 100f
                }
            }
        }
    }

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

