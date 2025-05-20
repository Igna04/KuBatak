// import retrofit2.Call
// import retrofit2.http.Body
// import retrofit2.http.POST

// data class AuthRequest(val email: String, val password: String)

// data class User(val id: String, val email: String?)

// data class SessionResponse(val access_token: String?, val refresh_token: String?, val user: User?)

// interface SupabaseApi {
//     @POST("auth/v1/signup") fun register(@Body request: AuthRequest): Call<SessionResponse>

//     @POST("auth/v1/token?grant_type=password")
//     fun login(@Body request: AuthRequest): Call<SessionResponse>
// }
