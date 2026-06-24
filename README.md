# IRentMy 

Aplicație Android de închirieri stil „Vinted”, dedicată închirierii de obiecte (unelte, echipamente sportive, haine etc.). Utilizatorii pot vedea un feed de anunțuri, posta propriile anunțuri, închiria obiecte și urmări istoricul închirierilor.

Proiect realizat în cadrul disciplinei [numele materiei], de către o echipă de 2 persoane.

---

##  Funcționalități

- **Autentificare** — înregistrare și login cu email/parolă (Firebase Authentication)
- **Feed de anunțuri** — listă scrollabilă cu anunțuri aduse de pe un API (titlu, descriere, preț/oră, preț/zi, preț/lună, proprietar)
- **Căutare** — filtrare instantă a anunțurilor după titlu
- **Postare anunț** — formular cu validare (toate câmpurile obligatorii); anunțul se salvează local și pe server
- **Închiriere** — alegerea perioadei (oră/zi/lună) și a cantității, cu un ecran de plată simulat și datele de contact ale proprietarului
- **Istoric închirieri** — lista obiectelor închiriate cu termenul de returnare („mai ai X zile”)
- **Pagină de cont** — profil editabil (nume, descriere), lista anunțurilor proprii cu opțiune de ștergere, și logout
- **Funcționare offline** — dacă nu există conexiune, datele se citesc din baza de date locală (fără crash)

---

##  Tehnologii folosite

- **Limbaj:** Kotlin
- **UI:** Jetpack Compose
- **Arhitectură:** MVVM (Model–View–ViewModel)
- **Navigare:** Navigation Compose (cu bottom navigation bar)
- **Bază de date locală:** Room
- **Stocare simplă:** SharedPreferences
- **Rețea:** Retrofit + Gson (deserializare JSON)
- **API de date:** MockAPI.io
- **Autentificare:** Firebase Authentication
- **Programare asincronă:** Kotlin Coroutines
- **XML clasic:** Splash screen cu shape, gradient și selector

**Cerințe minime:** Android 7.0 (API 24) sau mai nou.

---

##  Cum rulezi proiectul

1. Clonează repository-ul:
   ```
   git clone [link-ul-repository-ului]
   ```
2. Deschide proiectul în **Android Studio**.
3. Adaugă fișierul **`google-services.json`** (de la Firebase) în folderul `app/`.
   > Fișierul se obține din Firebase Console → Project Settings → aplicația Android.
4. În `data/remote/RetrofitClient.kt`, setează adresa ta de MockAPI în `BASE_URL`:
   ```
   private const val BASE_URL = "[url-ul-tau-de-mockapi]"
   ```
5. Sincronizează proiectul cu Gradle (**Sync Now**).
6. Rulează aplicația pe un emulator sau pe un telefon (Run ▶).

---

## Structura proiectului

```
app/src/main/java/com/example/irentmy/
├── SplashActivity.kt          # ecran de pornire (XML)
├── MainActivity.kt            # punctul de intrare (Compose)
├── data/
│   ├── RentalItem.kt          # model anunț (entitate Room + JSON)
│   ├── RentedItem.kt          # model închiriere (istoric)
│   ├── local/                 # Room: DAO-uri + baza de date
│   ├── remote/                # Retrofit: ApiService + client
│   └── repository/            # logica datelor (rețea + local)
├── util/
│   └── PrefsManager.kt        # SharedPreferences
└── ui/
    ├── auth/                  # login + register
    ├── feed/                  # feed de anunțuri + card
    ├── post/                  # postare anunț
    ├── checkout/              # închiriere + plată simulată
    ├── rentals/               # istoric închirieri
    ├── account/               # pagină de cont
    └── navigation/            # NavHost + bottom bar
```


---



- **Plata este simulată** — aplicația nu procesează plăți reale; ecranul de checkout doar înregistrează închirierea local.
- **MockAPI.io** este folosit ca sursă de date pentru anunțuri (request-uri GET, POST, DELETE).
- **Firebase** este folosit doar pentru autentificare; parolele nu sunt stocate de aplicație.
