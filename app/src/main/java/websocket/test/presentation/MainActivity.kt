package websocket.test.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import websocket.test.R
import websocket.test.presentation.ui.SocketFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SocketFragment.newInstance())
                .commitNow()
        }
    }
}
