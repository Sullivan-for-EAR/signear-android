buildscript {
    addScriptRepository()
    addScriptDependencies()
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}