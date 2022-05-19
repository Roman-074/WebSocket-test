package websocket.test.inapp.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils.split
import android.text.TextUtils.substring
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.koin.androidx.viewmodel.ext.android.viewModel
import websocket.test.databinding.MainFragmentBinding
import java.io.IOException

class SocketFragment : Fragment() {

    companion object {
        fun newInstance() = SocketFragment()

        var accessToken = ""
        const val ssoToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjJDQUQ4RDFFMkZBMzIyMTRDNUIzODMxMzlFN0FEMjFGREUyOEFCREJSUzI1NiIsInR5cCI6ImF0K2p3dCIsIng1dCI6IkxLMk5IaS1qSWhURnM0TVRubnJTSDk0b3E5cyJ9.eyJuYmYiOjE2NDU3ODk0NTksImV4cCI6MTcwODg2MTQ1OSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdCIsImNsaWVudF9pZCI6InBob25lTnVtYmVyQXV0aGVudGljYXRpb24iLCJzdWIiOiI4NWNmODdlYi02NDQzLTQwMjctYTI2Yi1iZjg0MGUwMTcxNDkiLCJhdXRoX3RpbWUiOjE2NDU3ODk0NTksImlkcCI6ImxvY2FsIiwiSWQiOiI4NWNmODdlYi02NDQzLTQwMjctYTI2Yi1iZjg0MGUwMTcxNDkiLCJQaG9uZU51bWJlciI6Ijc5MTM1NTM0NDU1IiwiUGhvbmVOdW1iZXJDb25maXJtZWQiOnRydWUsIkVtYWlsIjoiIiwiRW1haWxDb25maXJtZWQiOmZhbHNlLCJIYXNQYXNzd29yZCI6ZmFsc2UsIkhhc0NvZGV3b3JkIjpmYWxzZSwiQXNwTmV0LklkZW50aXR5LlNlY3VyaXR5U3RhbXAiOiJCMjJZWFRXTEVNRUNOWE9aTEY1NTVXQ0dWNE0zNVhQSSIsImdpdmVuX25hbWUiOiIiLCJmYW1pbHlfbmFtZSI6IiIsIm1pZGRsZV9uYW1lIjoiIiwicm9sZSI6ImN1c3RvbWVyIiwianRpIjoiRTVBMDdFMTZCOEZERUVFQzI3QUQ3QkMyQkFGRDJFNDMiLCJpYXQiOjE2NDU3ODk0NTksInNjb3BlIjpbImFwaSIsIklkZW50aXR5U2VydmVyQXBpIiwib3BlbmlkIiwib2ZmbGluZV9hY2Nlc3MiXSwiYW1yIjpbInNtcyJdfQ.IaiAZXXe_Ooj0XQG3-_NK2OCEbalYAlKiYgkkBPV06ztvAj3nakZvwseF4qc16MswQDOemzVnJNJmgijgPcCd57VPStV8lcDGL69D-V-k2FoKPUy00AAWJJCxqkETIOr8eAv_SqA7uUD32woow0G2Hw5d2f9FPCmp9MswP35ozGYyXmwIoiJ3KHwlvcb3GMxGXyy09LmTEL95MjsOfmXQb2ixwlG3OShTY5TDnVTA79H10sTruUtFdRTR8-isxGwVAr8whn_lZ0E4xOlITmBdYoB4jUYzgepd4PbxdOJ8dG5RxgT5xVPjqhCCzF6XzgF8jgc6Q--fWl-H7409G7nkw"
    }

    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPost.setOnClickListener { sendPost() }
        binding.buttonSocket.setOnClickListener { }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.ticker.observe(viewLifecycleOwner) { ticker ->
            binding.ticker = ticker
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendPost() {
        val client = OkHttpClient().newBuilder().build()
        val mediaType = "text/plain".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, "")
        val request = Request.Builder()
            .url("https://api-gate.vestabankdev.ru/release/api/signalr-service/ws/negotiate?negotiateVersion=1")
//            .method("POST", body)
            .post(body)
//            .addHeader("Content-Type", "text/plain")
            .addHeader("Authorization", "Bearer $ssoToken")
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) { }
                @SuppressLint("LogNotTimber")
                override fun onResponse(call: Call, response: Response) {
                    response.body?.string()?.let {
                        Log.e("my", ">>>>>>> $it")

                        val lstValues: List<String> = it.split(",").map {
                                last ->
                            last.trim()
                        }
//                        lstValues.forEach { lValue ->
//                            Log.i("my", "value=$lValue")
//                        }

                        val res = lstValues[2].substring(19, lstValues[2].length - 1)
                        Log.i("my", " >>>>>> $res")
                        accessToken = res
                    }
                }
            })
        }
    }

    private val jsonMoshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

@JsonClass(generateAdapter = true)
data class resToken(
    @Json(name = "connectionToken")
    val connectionToken: String
)
