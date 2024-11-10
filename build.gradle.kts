// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jetbrains.kotlin.kapt") version "1.9.10"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}