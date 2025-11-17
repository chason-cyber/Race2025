package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row // 新增 Row 引入，用於按鈕佈局
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

    val imageBitmap = ImageBitmap.imageResource(R.drawable.horse0)

    val myName = "陳宇謙"

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
    ){
        Canvas (modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    gameViewModel.MoveCircle( dragAmount.x, dragAmount.y)
                }
            }
        ) {
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.circleX, gameViewModel.circleY)
            )
            drawImage(
                image = imageBitmap,
                dstOffset = IntOffset(0, 100),
                dstSize = IntSize(200, 200)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(text = "姓名: $myName")
                Text(text = "分數: ${gameViewModel.score}")
                Text(text = message + gameViewModel.screenWidthPx.toString() + "*" + gameViewModel.screenHeightPx.toString())
            }


            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = {
                    gameViewModel.score = 0
                    gameViewModel.StartGame()
                }) {
                    Text("遊戲開始")
                }

            }
        }
    }
}