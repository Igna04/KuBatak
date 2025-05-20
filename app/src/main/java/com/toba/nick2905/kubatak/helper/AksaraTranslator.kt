// package com.toba.nick2905.kubatak.helper

// import android.content.Context
// import android.util.Log

// class AksaraTranslator(private val context: Context) {
//     private val TAG = "AksaraTranslator"

//     // Kamus untuk menerjemahkan aksara Batak ke kata dalam bahasa Batak
//     private val aksaraToWordMap =
//             mapOf(
//                     "ha" to "horas",
//                     "ka" to "kacang",
//                     "hi" to "hidup",
//                     "ki" to "kita",
//                     "hu" to "hujan",
//                     "ku" to "kursi",
//                     "ta" to "tano",
//                     "te" to "teman"
//                     // Tambahkan lebih banyak pemetaan sesuai kebutuhan
//                     )

//     // Kamus untuk menerjemahkan kata bahasa Batak ke bahasa Indonesia
//     private val batakToIndonesianMap =
//             mapOf(
//                     "horas" to "salam",
//                     "kacang" to "kacang",
//                     "hidup" to "hidup",
//                     "kita" to "kita",
//                     "hujan" to "hujan",
//                     "kursi" to "kursi",
//                     "tano" to "tanah",
//                     "teman" to "teman",
//                     "goar" to "nama",
//                     "ahu" to "aku",
//                     "tudia" to "kemana",
//                     "kerja" to "karejo",
//                     "hita" to "Kita"
//                     )

//     /**
//      * Menerjemahkan aksara Batak yang terdeteksi menjadi kata dalam bahasa Batak
//      * @param detectedAksara string aksara Batak yang terdeteksi (contoh: "ha-ka")
//      * @return Triple berisi (aksara asli, kata dalam bahasa Batak, arti dalam bahasa Indonesia)
//      */
//     fun translateAksara(detectedAksara: String): Triple<String, String, String> {
//         try {
//             // Pisahkan aksara yang terdeteksi jika ada lebih dari satu
//             val aksaraList = detectedAksara.split("-")
//             val batakWords = mutableListOf<String>()

//             // Terjemahkan setiap aksara menjadi kata Batak
//             for (aksara in aksaraList) {
//                 val word = aksaraToWordMap[aksara] ?: aksara
//                 batakWords.add(word)
//             }

//             // Gabungkan kata-kata Batak
//             val batakSentence = batakWords.joinToString(" ")

//             // Terjemahkan ke bahasa Indonesia
//             val indonesianWords =
//                     batakWords.map { batakWord -> batakToIndonesianMap[batakWord] ?: batakWord }
//             val indonesianSentence = indonesianWords.joinToString(" ")

//             return Triple(detectedAksara, batakSentence, indonesianSentence)
//         } catch (e: Exception) {
//             Log.e(TAG, "Error during translation: ${e.message}")
//             return Triple(detectedAksara, "Tidak dapat diterjemahkan", "Tidak dapat diterjemahkan")
//         }
//     }
// }
