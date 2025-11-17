package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned // 導入這個
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

    val density = LocalDensity.current
    val myName = "(作者：陳宇謙) "


    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)

        .onGloballyPositioned { coordinates ->

            val w = with(density) { coordinates.size.width.toDp().toPx() }
            val h = with(density) { coordinates.size.height.toDp().toPx() }
            gameViewModel.SetGameSize(w, h)
        }
    ){
        Canvas (modifier = Modifier
            .fillMaxSize()

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


            gameViewModel.horses.forEach { horse ->
                drawImage(
                    image = imageBitmaps[horse.number % imageBitmaps.size],
                    // horseX 和 horseY 是 Int 型態，這裡可以直接使用
                    dstOffset = IntOffset(horse.horseX, horse.horseY),
                    dstSize = IntSize(200, 200) // 馬匹圖片大小 200x200
                )
            }
        }

        if (gameViewModel.winnerMessage.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center // 將內容置中
            ) {
                Text(
                    text = gameViewModel.winnerMessage,
                    color = Color.Black, // 為了清晰，顏色改為黑色
                    fontSize = 48.sp, // 放大字體
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.8f), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)) // 增加白色半透明背景
                        .padding(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(text = "賽馬遊戲: $myName")
                Text(text = "分數: ${gameViewModel.score}")
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