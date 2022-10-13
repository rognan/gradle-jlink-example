plugins {
  id("java")
}

group = "org.rognan"
version = "0.1.0"

java {
  targetCompatibility = JavaVersion.toVersion("17")
  sourceCompatibility = JavaVersion.toVersion("17")
}

tasks.jar {
  manifest {
    attributes(
      "Main-Class" to "org.rognan.Application"
    )
  }
}

tasks.register<Exec>("jlink") {
  group = "Build"
  description = "Generate Java runtime image"

  dependsOn("clean", "jar")

  val javaHome = System.getProperty("java.home")
  val moduleName = "org.rognan.jlink"
  val moduleLaunchPoint = "org.rognan.Application"

  commandLine = listOf(
    "$javaHome/bin/jlink",
    "--module-path", "$buildDir/libs${File.pathSeparatorChar}$javaHome/jmods",
    "--strip-debug", "--no-header-files", "--no-man-pages", "--compress", "2",
    "--add-modules", moduleName,
    "--launcher", "launch=$moduleName/$moduleLaunchPoint",
    "--output", "$buildDir/image"
  )
}
