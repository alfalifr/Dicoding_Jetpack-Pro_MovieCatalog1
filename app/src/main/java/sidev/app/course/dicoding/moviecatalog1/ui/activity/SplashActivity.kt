package sidev.app.course.dicoding.moviecatalog1.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.lib.android.std.tool.util._ThreadUtil
import sidev.lib.android.std.tool.util.`fun`.startAct

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)

        _ThreadUtil.delayRun(2000){
            startAct<MainActivity>()
            finish()
        }
    }
}