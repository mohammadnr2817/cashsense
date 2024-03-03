plugins {
    alias(libs.plugins.cashsense.android.feature)
    alias(libs.plugins.cashsense.android.library.compose)
}

android {
    namespace = "ru.resodostudios.cashsense.feature.transaction"
}

dependencies {
    implementation(projects.feature.categories)
    implementation(projects.feature.category)
}