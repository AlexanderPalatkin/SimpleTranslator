plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.historyscreen"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.repository))
    implementation(project(Modules.model))
    implementation(project(Modules.descriptionScreen))

    //Design
    implementation(Design.appcompat)
    implementation(Design.material)
    implementation(Design.constraint)
    implementation(Design.swipe_refresh)

    //Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    //Koin
    implementation(Koin.koin_android)
    implementation(Koin.koin_view_model)
    implementation(Koin.koin_java_compat)

    //Room
    implementation(Room.room_runtime)
    kapt(Room.room_compiler)
    implementation(Room.room_ktx)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.runner)
    androidTestImplementation(TestImpl.espresso)
}