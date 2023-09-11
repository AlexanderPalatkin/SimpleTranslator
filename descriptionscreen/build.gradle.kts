plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.descriptionscreen"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.utils))

    //Design
    implementation(Design.appcompat)
    implementation(Design.swipe_refresh)

    //Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    //Coil
    implementation(Coil.coil)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.runner)
    androidTestImplementation(TestImpl.espresso)
}