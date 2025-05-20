// import retrofit2.Retrofit
// import retrofit2.converter.gson.GsonConverterFactory
// import okhttp3.OkHttpClient

// object RetrofitClient {
//     private const val BASE_URL = "https://crxdlozulzzhjlxbbvht.supabase.co/"

//     private val client =
//             OkHttpClient.Builder()
//                     .addInterceptor { chain ->
//                         val request =
//                                 chain.request()
//                                         .newBuilder()
//                                         .addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNyeGRsb3p1bHp6aGpseGJidmh0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcyNzU5OTksImV4cCI6MjA2Mjg1MTk5OX0.CFVVmcVgItYTjbfzHd1HcMpNRyxmQkc8436SCgc5XRs")
//                                         .addHeader("Content-Type", "application/json")
//                                         .build()
//                         chain.proceed(request)
//                     }
//                     .build()

//     val instance: SupabaseApi by lazy {
//         Retrofit.Builder()
//                 .baseUrl(BASE_URL)
//                 .client(client)
//                 .addConverterFactory(GsonConverterFactory.create())
//                 .build()
//                 .create(SupabaseApi::class.java)
//     }
// }

