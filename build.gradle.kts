plugins {
  id("java")
  id("com.github.ben-manes.versions") version "0.39.0"
  id("net.nemerosa.versioning") version "2.15.0"
}

group = "org.rognan"
version = versioning.info.full

java {
  targetCompatibility = JavaVersion.toVersion("17")
  sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
  jar {
    manifest {
      attributes(
        "Main-Class" to "org.rognan.Application"
      )
    }
  }

  register<Exec>("jlink") {
    group = "Build"
    description = "Generate custom Java runtime image"

    dependsOn("clean", "jar")

    val javaHome = System.getProperty("java.home")
    val moduleName = "org.rognan.jlink"
    val moduleLaunchPoint = "org.rognan.Application"

    workingDir = file("build")

    commandLine(
      "$javaHome/bin/jlink", "--module-path", "libs${File.pathSeparatorChar}$javaHome/jmods",
      "--strip-debug", "--no-header-files", "--no-man-pages", "--compress", "2",
      "--add-modules", moduleName,
      "--launcher", "launch=$moduleName/$moduleLaunchPoint",
      "--output", "image"
    )
  }
}
