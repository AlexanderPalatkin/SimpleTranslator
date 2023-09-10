plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.model"
}

dependencies {
    implementation(project(Modules.utils))

    implementation(Design.appcompat)

    //Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    //Retrofit
    implementation(Retrofit.converter_gson)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.runner)
    androidTestImplementation(TestImpl.espresso)
}