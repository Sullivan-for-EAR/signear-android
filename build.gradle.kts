buildscript {
    addScriptRepository()
    addScriptDependencies()
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects { parent!!.path.takeIf { it != rootProject.path }?.let { evaluationDependsOn(it)  } }