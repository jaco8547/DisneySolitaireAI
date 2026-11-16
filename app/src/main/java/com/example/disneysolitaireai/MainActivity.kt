package com.example.disneysolitaireai

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.provider.Settings
import android.net.Uri
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val captureRequest = 4102
    private lateinit var status: TextView
    private lateinit var apiInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28, 36, 28, 24)
        }
        layout.addView(TextView(this).apply {
            text = "Disney Solitaire AI"
            textSize = 26f
        })
        status = TextView(this).apply {
            text = "상태: 대기 중\n실제 게임 화면 분석기는 아직 보정이 필요합니다."
            textSize = 15f
            setPadding(0, 18, 0, 18)
        }
        layout.addView(status)
        apiInput = EditText(this).apply {
            hint = "새 DeepSeek API 키 입력 (이전 노출 키 사용 금지)"
            inputType = 129
        }
        layout.addView(apiInput)
        layout.addView(Button(this).apply {
            text = "API 키 저장"
            setOnClickListener {
                val key = apiInput.text.toString().trim()
                if (key.isBlank()) status.text = "API 키를 입력하세요."
                else {
                    SecureKeyStore.save(this@MainActivity, key)
                    apiInput.setText("")
                    status.text = "API 키를 기기 내부 저장소에 저장했습니다."
                }
            }
        })
        layout.addView(Button(this).apply {
            text = "화면 캡처 권한 요청"
            setOnClickListener {
                val mgr = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
                startActivityForResult(mgr.createScreenCaptureIntent(), captureRequest)
            }
        })
        layout.addView(Button(this).apply {
            text = "접근성 서비스 설정"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        })
        layout.addView(Button(this).apply {
            text = "자동 플레이 시작 (개발용)"
            setOnClickListener {
                status.text = "시작 요청됨. 실제 카드 인식/게임별 좌표 보정은 아직 구현되지 않았습니다."
            }
        })
        layout.addView(Button(this).apply {
            text = "일시정지"
            setOnClickListener { status.text = "일시정지됨" }
        })
        layout.addView(Button(this).apply {
            text = "종료"
            setOnClickListener { status.text = "중지됨" }
        })
        setContentView(ScrollView(this).apply { addView(layout) })
    }

    @Deprecated("Deprecated by Android; kept for broad compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == captureRequest && resultCode == Activity.RESULT_OK && data != null) {
            val svc = Intent(this, CaptureService::class.java).apply {
                putExtra("resultCode", resultCode)
                putExtra("data", data)
            }
            startForegroundService(svc)
            status.text = "화면 캡처 권한 승인됨. 캡처 서비스 시작 요청 완료."
        }
    }
}
