plugins {
  id("java")
}

group = "org.rognan"
version = "0.1.0"

val jvmReleaseTarget = JavaLanguageVersion.of(19)
val launcher = javaToolchains.launcherFor {
  languageVersion.set(jvmReleaseTarget)
}
val compiler = javaToolchains.compilerFor {
  languageVersion.set(jvmReleaseTarget)
}

java {
  toolchain {
    languageVersion.set(jvmReleaseTarget)
  }
}

tasks.withType<JavaCompile>().configureEach {
  javaCompiler.set(compiler)
  options.encoding = "UTF-8"
  options.isIncremental = true
  options.compilerArgs = listOf("--enable-preview")
}

tasks.jar {
  manifest.attributes["Main-Class"] = "org.rognan.Application"

  from(rootDir) {
    include("LICENSE")
  }
}

tasks.register<Exec>("jlink") {
  group = "Build"
  description = "Generate Java runtime image"

  dependsOn("clean", "jar")

  val javaHome = launcher.get().metadata.installationPath.asFile.absolutePath
  val moduleName = "org.rognan.jlink"
  val moduleLaunchPoint = "org.rognan.Application"

  commandLine = listOf(
    "$javaHome/bin/jlink",
    "--add-options", "\"--enable-preview\"",
    "--module-path", "$buildDir/libs${File.pathSeparatorChar}$javaHome/jmods",
    "--strip-debug", "--no-header-files", "--no-man-pages", "--compress", "2",
    "--add-modules", moduleName,
    "--launcher", "launch=$moduleName/$moduleLaunchPoint",
    "--output", "$buildDir/image"
  )
}
