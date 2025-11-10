package tw.edu.pu.csim.tcyang.race

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier // 雖然沒用到，但保持原樣
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.window.layout.WindowMetricsCalculator
import tw.edu.pu.csim.tcyang.race.ui.theme.RaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        // 修正：一次性隱藏所有系統列，包含狀態列和導覽列
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        WindowCompat.setDecorFitsSystemWindows(
            window, false)

        val windowMetricsCalculator =
            WindowMetricsCalculator.getOrCreate()
        val currentWindowMetrics=
            windowMetricsCalculator.computeCurrentWindowMetrics(this)
        val bounds = currentWindowMetrics.bounds
        val screenWidthPx = bounds.width().toFloat()
        val screenHeightPx = bounds.height().toFloat()
        val gameViewModel: GameViewModel by viewModels()
        gameViewModel.SetGameSize(screenWidthPx , screenHeightPx)

        setContent {
            RaceTheme {
                GameScreen(
                    message = "橫式螢幕，隱藏系統列",
                    gameViewModel = gameViewModel
                )
            }
        }
    }
}