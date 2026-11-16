package com.example.disneysolitaireai

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class DeepSeekClient {
    private val client = OkHttpClient.Builder()
        .callTimeout(20, TimeUnit.SECONDS).build()

    fun evaluate(apiKey: String, stateJson: String): Result<String> = runCatching {
        val body = JSONObject().apply {
            put("model", "deepseek-chat")
            put("messages", org.json.JSONArray().put(JSONObject()
                .put("role", "user")
                .put("content", "Evaluate this Klondike state and suggest a legal move. Return JSON only: $stateJson"))
            put("temperature", 0.1)
        }.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("https://api.deepseek.com/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .post(body).build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) error("DeepSeek HTTP ${response.code}")
            response.body?.string() ?: error("Empty response")
        }
    }
}
