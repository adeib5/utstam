package com.example.utstam.repository

import android.content.Context
import androidx.core.content.edit
import com.example.utstam.R
import com.example.utstam.api.ApiConfig
import com.example.utstam.model.ChatMessage
import com.example.utstam.model.Education
import com.example.utstam.model.Report
import com.example.utstam.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {
    private const val PREF_NAME = "SpeakUpPrefs"
    private const val KEY_USER_ID = "logged_in_user_id"
    private const val KEY_USER_NAME = "logged_in_user_name"
    private const val KEY_USER_EMAIL = "logged_in_user_email"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_ANONYMOUS_MODE = "anonymous_mode"

    val reports = mutableListOf(
        Report("1", "user1", "Anonim", "Verbal", "Kantin", "2023-11-01", "Diejek karena penampilan", null, System.currentTimeMillis() - 400000000, "Selesai", "Kami telah memberikan teguran kepada pelaku."),
        Report("2", "user1", "Ahmad Fauzi", "Senioritas", "Asrama", "2023-11-05", "Dipaksa cuci baju senior", null, System.currentTimeMillis() - 300000000, "Diproses"),
        Report("3", "user1", "Anonim", "Online", "Grup WhatsApp", "2023-11-10", "Pesan bernada ancaman di grup kelas", null, System.currentTimeMillis() - 200000000, "Diproses"),
        Report("4", "user1", "Ahmad Fauzi", "Fisik", "Gedung A", "2023-11-12", "Didorong hingga jatuh saat praktikum", null, System.currentTimeMillis() - 100000000, "Diproses")
    )

    val educationList = listOf(
        Education(1, "Bullying di Kampus", "Kenali bentuk-bentuknya.", "Bullying di lingkungan kampus seringkali lebih halus namun merusak. Bentuknya bisa berupa senioritas yang berlebihan, pengucilan sosial, hingga pelecehan verbal di depan umum.\n\nMahasiswa harus sadar bahwa setiap orang berhak merasa aman di lingkungan pendidikan. Jangan biarkan tindakan merugikan ini berlanjut.", R.drawable.illustration_edu_1),
        Education(2, "Dampak Psikologis", "Dampak nyata bagi mahasiswa.", "Korban bullying seringkali mengalami penurunan prestasi akademik, depresi, kecemasan berlebih, hingga keinginan untuk berhenti kuliah.\n\nKesehatan mental sama pentingnya dengan kesehatan fisik. Speak Up hadir untuk memastikan suara Anda didengar.", R.drawable.illustration_edu_2),
        Education(3, "Cara Melapor Aman", "Langkah-langkah yang tepat.", "Pastikan Anda mencatat detail kejadian: waktu, lokasi, and siapa pelakunya. Jika ada bukti foto atau pesan, simpan baik-baik.\n\nGunakan fitur Speak Up untuk melaporkan secara anonim jika Anda merasa kurang nyaman menggunakan nama asli.", R.drawable.illustration_edu_3),
        Education(4, "Hak-Hak Mahasiswa", "Lindungi diri Anda.", "Setiap kampus memiliki kode etik yang melarang kekerasan dan perundungan. Anda dilindungi secara hukum and institusi.\n\nSpeak Up bekerja sama dengan pihak kampus untuk nindaklanjuti setiap laporan yang masuk secara serius.", R.drawable.illustration_edu_4)
    )
    
    val chatMessages = mutableListOf(
        ChatMessage("Halo! Admin Speak Up di sini. Apa yang bisa kami bantu?", false),
    )

    private const val KEY_LOCAL_USERS = "local_users"

    val users = mutableListOf(
        User("user1", "Ahmad Fauzi", "ahmad@kampus.id", "password123")
    )

    var loggedInUser: User? = null

    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val gson = Gson()
        val json = prefs.getString(KEY_LOCAL_USERS, null)
        if (json != null) {
            val type = object : TypeToken<List<User>>() {}.type
            val savedUsers: List<User> = gson.fromJson(json, type)
            savedUsers.forEach { savedUser ->
                if (users.none { it.email == savedUser.email }) {
                    users.add(savedUser)
                }
            }
        }

        val isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        if (isLoggedIn) {
            val id = prefs.getString(KEY_USER_ID, "user1") ?: "user1"
            val name = prefs.getString(KEY_USER_NAME, "Mahasiswa") ?: "Mahasiswa"
            val email = prefs.getString(KEY_USER_EMAIL, "") ?: ""
            loggedInUser = User(id, name, email, "")
        }
    }

    fun addUser(context: Context, user: User) {
        if (users.none { it.email == user.email }) {
            users.add(user)
            saveUsers(context)
        }
    }

    private fun saveUsers(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(users.filter { it.id != "user1" })
        prefs.edit().putString(KEY_LOCAL_USERS, json).apply()
    }

    fun fetchReportsFromApi(onResult: (List<Report>?) -> Unit) {
        ApiConfig.getApiService().getReports().enqueue(object : Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>) {
                if (response.isSuccessful) {
                    val apiReports = response.body()
                    if (apiReports != null) {
                        onResult(apiReports)
                    }
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun postReportToApi(report: Report, onResult: (Boolean) -> Unit) {
        ApiConfig.getApiService().createReport(report).enqueue(object : Callback<Report> {
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                onResult(response.isSuccessful)
            }

            override fun onFailure(call: Call<Report>, t: Throwable) {
                onResult(false)
            }
        })
    }

    fun login(user: User, context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER_ID, user.id)
            putString(KEY_USER_NAME, user.name)
            putString(KEY_USER_EMAIL, user.email)
        }
        loggedInUser = user
    }

    fun logout(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit { clear() }
        loggedInUser = null
    }

    fun setAnonymousMode(context: Context, isEnabled: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit { putBoolean(KEY_ANONYMOUS_MODE, isEnabled) }
    }

    fun isAnonymousMode(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_ANONYMOUS_MODE, false)
    }
    
    fun updateUser(context: Context, name: String) {
        loggedInUser?.let {
            it.name = name
            val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit { putString(KEY_USER_NAME, name) }
        }
    }
}
