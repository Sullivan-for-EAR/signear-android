buildscript {
    val kotlin_version by extra("1.4.32")
    addScriptRepository()
    addScriptDependencies()
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}