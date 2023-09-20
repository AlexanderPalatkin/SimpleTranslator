plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.utils"
}

dependencies {

    implementation(Design.appcompat)
    implementation(Kotlin.core)
    implementation(Design.material)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.runner)
    androidTestImplementation(TestImpl.espresso)
}