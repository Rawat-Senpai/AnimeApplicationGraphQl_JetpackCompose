// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.apollographql.apollo") version "4.0.0"

    id("com.google.dagger.hilt.android") version "2.48" apply false
}