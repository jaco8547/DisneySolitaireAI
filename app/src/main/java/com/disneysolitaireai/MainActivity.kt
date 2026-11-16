package com.disneysolitaireai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DisneySolitaireApp() }
    }
}

@Composable
private fun DisneySolitaireApp() {
    var running by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("대기 중") }
    var apiEnabled by remember { mutableStateOf(false) }

    MaterialTheme {
        Scaffold { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding)
                    .verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("DisneySolitaireAI", style = MaterialTheme.typography.headlineMedium)
                Text("실시간 카드 인식 기반 Klondike 자동 풀이 앱")
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("실행 상태", style = MaterialTheme.typography.titleMedium)
                        Text(status, style = MaterialTheme.typography.bodyLarge)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = {
                                running = true
                                status = "시작 요청됨 — 화면 캡처 및 접근성 권한 모듈 연결 전"
                            }, enabled = !running) { Text("시작") }
                            OutlinedButton(onClick = {
                                running = false
                                status = "정지됨"
                            }, enabled = running) { Text("정지") }
                        }
                    }
                }
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("인식 / 풀이 상태", style = MaterialTheme.typography.titleMedium)
                        StatLine("인식된 카드", "아직 없음")
                        StatLine("현재 단계", "초기화 전")
                        StatLine("실행한 이동", "0")
                        StatLine("오류", "없음")
                    }
                }
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("AI 보조 판단", style = MaterialTheme.typography.titleMedium)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("DeepSeek 사용", modifier = Modifier.weight(1f))
                            Switch(checked = apiEnabled, onCheckedChange = { apiEnabled = it })
                        }
                        Text(
                            "API 키는 앱 코드에 저장하지 않습니다. 보안 저장 및 사용자 입력 기능을 별도 구현할 예정입니다.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Text(
                    "현재 버전은 새 프로젝트의 UI 기반입니다. 화면 캡처, 카드 인식, 실제 자동 터치, AI 호출은 아직 연결되지 않았습니다.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun StatLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label)
        Text(value, color = MaterialTheme.colorScheme.secondary)
    }
}
