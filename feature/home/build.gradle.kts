plugins {
    alias(libs.plugins.cashsense.android.feature)
    alias(libs.plugins.cashsense.android.library.compose)
}

android {
    namespace = "ru.resodostudios.cashsense.feature.home"
}

dependencies {
    implementation(projects.feature.transaction)
    implementation(projects.feature.wallet)
    implementation(projects.feature.categories)
}