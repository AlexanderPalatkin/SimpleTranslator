plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.core"
}

dependencies {
    implementation(project(Modules.model))
    implementation(project(Modules.utils))

    //Design
    implementation(Design.appcompat)
    implementation(Design.material)

    //Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    //Koin
    implementation(Koin.koin_android)
    implementation(Koin.koin_java_compat)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.runner)
    androidTestImplementation(TestImpl.espresso)
}