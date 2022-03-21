pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    
}
rootProject.name = "Athleet"


include(":android")
include(":desktop")
include(":common-ui")
include(":common")
includeBuild("../server")