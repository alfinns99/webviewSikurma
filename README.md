# SI KURMA - Portal Kurikulum SMAN Pesanggaran

<p align="center">
  <img src="app/src/main/res/drawable/logo_sekolah.png" alt="Logo SI KURMA" width="150"/>
</p>

<p align="center">
  <strong>Sistem Informasi Kurikulum - SMAN Pesanggaran</strong>
</p>

<p align="center">
  <a href="#fitur">Fitur</a> •
  <a href="#screenshot">Screenshot</a> •
  <a href="#instalasi">Instalasi</a> •
  <a href="#teknologi">Teknologi</a> •
  <a href="#lisensi">Lisensi</a>
</p>

---

## 📱 Tentang Aplikasi

**SI KURMA** (Sistem Informasi Kurikulum) adalah aplikasi Android WebView resmi untuk mengakses Portal Kurikulum SMAN Pesanggaran. Aplikasi ini dirancang untuk mempermudah akses ke sistem kurikulum sekolah melalui perangkat mobile dengan pengalaman pengguna yang optimal.

### 🎯 Tujuan Aplikasi
- Mempermudah akses portal kurikulum dari smartphone
- Menyediakan interface yang user-friendly
- Mendukung offline error handling
- Pengalaman browsing yang cepat dan ringan

---

## ✨ Fitur

| Fitur | Deskripsi |
|-------|-----------|
| 🎨 **Splash Screen** | Tampilan awal dengan logo sekolah dan animasi loading |
| 🌐 **WebView Modern** | Memuat portal kurikulum dengan performa optimal |
| 🔄 **Swipe to Refresh** | Tarik ke bawah untuk memuat ulang halaman |
| 📊 **Progress Indicator** | Progress bar horizontal saat loading halaman |
| 📶 **Network Status** | Deteksi koneksi internet secara real-time |
| ❌ **Error Page** | Halaman error custom saat tidak ada koneksi |
| 🔄 **Retry Button** | Tombol coba lagi saat koneksi terputus |
| ℹ️ **Info Dialog** | Dialog informasi tentang aplikasi |
| ⬅️ **Back Navigation** | Navigasi mundur dalam WebView |
| 🔒 **SSL Handling** | Penanganan sertifikat SSL untuk kompatibilitas |

---

## 📸 Screenshot

> *Screenshot akan ditambahkan setelah build release*

---

## 🛠️ Instalasi

### Persyaratan Sistem
- Android Studio Hedgehog (2023.1.1) atau lebih baru
- JDK 11 atau lebih baru
- Android SDK API Level 24 (minimum) - 34 (target)
- Gradle 8.9

### Clone Repository

```bash
git clone https://github.com/alfinns99/webviewSikurma.git
cd webviewSikurma
```

### Build Project

1. **Buka di Android Studio**
   ```
   File → Open → Pilih folder webviewSikurma
   ```

2. **Sync Gradle**
   ```
   Klik "Sync Now" atau File → Sync Project with Gradle Files
   ```

3. **Build Project**
   ```
   Build → Make Project (Ctrl+F9)
   ```

4. **Run di Emulator/Device**
   ```
   Run → Run 'app' (Shift+F10)
   ```

### Generate APK

**Debug APK:**
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

**Release APK (Signed):**
```bash
./gradlew assembleRelease
```

---

## 🔧 Konfigurasi

### Mengubah Target URL

Edit file `MainActivity.kt`:

```kotlin
companion object {
    private const val BASE_URL = "https://kurikulum.smanpesanggaran.sch.id/"
}
```

### Mengubah Informasi Aplikasi

Edit file `app/src/main/res/values/strings.xml`:

```xml
<string name="app_name">Portal Kurikulum</string>
<string name="app_description">Portal Kurikulum SMAN Pesanggaran</string>
<string name="app_version">v1.0.0</string>
<string name="app_copyright">© 2026 SMAN Pesanggaran</string>
```

### Mengubah Warna Tema

Edit file `app/src/main/res/values/colors.xml`:

```xml
<color name="primary">#1565C0</color>
<color name="primary_dark">#0D47A1</color>
<color name="accent">#03DAC5</color>
```

---

## 💻 Teknologi

| Komponen | Teknologi |
|----------|-----------|
| **Bahasa** | Kotlin |
| **Min SDK** | API 24 (Android 7.0 Nougat) |
| **Target SDK** | API 33 (Android 13) |
| **Build System** | Gradle Kotlin DSL |
| **Architecture** | Single Activity |
| **UI** | Material Design 3 |
| **Networking** | WebView + NetworkCallback |

### Dependencies

```kotlin
// AndroidX Core
implementation("androidx.core:core-ktx:1.10.1")
implementation("androidx.appcompat:appcompat:1.6.1")

// Material Design
implementation("com.google.android.material:material:1.9.0")

// Layout
implementation("androidx.constraintlayout:constraintlayout:2.1.4")
implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

// WebView
implementation("androidx.webkit:webkit:1.6.1")

// Splash Screen API
implementation("androidx.core:core-splashscreen:1.0.1")

// Lifecycle
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
```

---

## 📁 Struktur Project

```
app/
├── src/main/
│   ├── java/id/sch/smanpesanggaran/kurikulum/
│   │   ├── MainActivity.kt          # Activity utama dengan WebView
│   │   ├── SplashActivity.kt         # Splash screen
│   │   ├── ui/
│   │   │   └── InfoDialogFragment.kt # Dialog informasi
│   │   └── utils/
│   │       └── NetworkUtils.kt       # Utility network status
│   └── res/
│       ├── drawable/                 # Icons dan backgrounds
│       ├── layout/                   # XML layouts
│       ├── menu/                     # Menu resources
│       ├── mipmap-*/                 # App icons
│       ├── values/                   # Colors, strings, themes
│       └── xml/                      # Network security config
├── build.gradle.kts                  # App-level build config
└── proguard-rules.pro               # ProGuard rules
```

---

## 🐛 Troubleshooting

### WebView Blank/Putih
- Pastikan emulator/device terhubung ke internet
- Untuk API < 28, SSL certificate mungkin tidak dipercaya (sudah di-handle di kode)

### Build Error JDK
- Pastikan menggunakan JDK 11 atau lebih baru
- Setting: File → Project Structure → SDK Location → JDK

### Gradle Sync Failed
- Clean project: Build → Clean Project
- Invalidate caches: File → Invalidate Caches → Invalidate and Restart

---

## 📄 Lisensi

```
MIT License

Copyright (c) 2026 Alfin Nur Said - ANSProject

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 🤝 Kontribusi

Aplikasi ini bersifat **Open Source** dan boleh dikembangkan lebih lanjut oleh siapa saja. Kontribusi sangat diterima!

### Cara Berkontribusi

1. Fork repository ini
2. Buat branch baru (`git checkout -b feature/AmazingFeature`)
3. Commit perubahan (`git commit -m 'Add some AmazingFeature'`)
4. Push ke branch (`git push origin feature/AmazingFeature`)
5. Buat Pull Request

---

## 👨‍💻 Developer

<p align="center">
  <strong>Developed with ❤️ by</strong>
</p>

<p align="center">
  <strong>Alfin Nur Said</strong><br>
  <em>ANSProject</em>
</p>

<p align="center">
  <a href="https://github.com/alfinns99">GitHub</a>
</p>

---

<p align="center">
  <sub>© 2026 SMAN Pesanggaran. All rights reserved.</sub>
</p>
