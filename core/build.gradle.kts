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

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.runner)
    androidTestImplementation(TestImpl.espresso)
}