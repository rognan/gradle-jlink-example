plugins {
  java

  id("com.github.ben-manes.versions")
  id("net.nemerosa.versioning")
}

group = "org.rognan"
version = versioning.info.full

java {
  targetCompatibility = JavaVersion.toVersion("13")
  sourceCompatibility = JavaVersion.toVersion("13")
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
