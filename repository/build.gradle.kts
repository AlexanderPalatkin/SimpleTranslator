plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.repository"
}

dependencies {
    implementation(project(Modules.model))

    implementation(Design.appcompat)

    //Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    //Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converter_gson)
    implementation(Retrofit.adapter_coroutines)
    implementation(Retrofit.loggins_interceptor)

    //Room
    implementation(Room.room_runtime)
    kapt(Room.room_compiler)
    implementation(Room.room_ktx)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.runner)
    androidTestImplementation(TestImpl.espresso)
}