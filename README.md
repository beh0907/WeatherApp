# <div align=center>WeatherApp</div>
[Weather API](https://open-meteo.com/)를 활용한 kotlin 언어 기반의 날씨 정보 제공 안드로이드 어플리케이션입니다.

# 특징

* 오늘 날씨 정보 제공
* 현재 위치와 시간 기준 시간별 날씨 정보 제공
* 7일간 날씨 예측 정보 제공

# 기술스택 및 라이브러리

* 최소 SDK 26 / 타겟 SDK 34
* kotlin 언어 기반, 비동기 처리를 위한 coroutine + Flow
* 종속성 주입을 위한 [Dagger Hilt](https://dagger.dev/hilt/)
* JetPack
  * Compose - Android의 현대적인 선언적 UI 툴킷으로, Kotlin 기반의 UI 개발을 통해 효율적이고 유연한 사용자 인터페이스 구축합니다.
  * LifeCycle - Android의 수명 주기를 관찰하고 수명 주기 변경에 따라 UI 상태를 처리합니다.
  * ViewModel - UI와 DATA 관련된 처리 로직을 분리합니다.
  * ViewBinding - View(Compose)와 코드(kotlin)간의 상호작용을 원활하게 처리합니다.
  * Navigation - Scaffold의 BottomNavigation을 활용한 하단 탭 기반 화면 전환 구현
  * Permissions - [TedPermission](https://github.com/ParkSangGwon/TedPermission)을 활용해 GPS 위치 정보에 관한 권한을 요청하고 처리합니다.
  * DataStore - SharedPreferences의 한계점을 개선한 라이브러리로 위치정보를 저장합니다.
* Architecture
  * MVVM 패턴 적용 - Model + View + ViewModel
  * Repository 패턴 적용 - Data + Domain + Presentation Layer 클린 아키텍처
* [Retrofit2](https://github.com/square/retrofit) - REST API를 호출하여 서버로부터 JSON 타입의 데이터를 수신합니다.
* GPS
  * FusedLocationProviderClient를 활용한 고정밀 위치 정보 수신 
  * LocationRequest를 통한 위치 업데이트 간격 및 정확도 최적화
  * 위치 정보 변경 시 자동으로 날씨 정보 업데이트
  * BroadcastReceiver 활용, GPS 활성화 상태 실시간 체크

# 스크린샷

| 오늘 날씨 화면                                                                                     | 날짜별 날씨 화면                                                                                     |
|----------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| ![오늘 날씨 화면](https://github.com/user-attachments/assets/154d5919-7da9-480f-b86d-78b0b2aeacc9) | ![날짜별 날씨 화면](https://github.com/user-attachments/assets/eb4dd5b7-0721-41c9-898f-f68218ba653a) |
