plugins {
  java
}

group = "org.rognan"
version = "2.0.1"

java {
  targetCompatibility = JavaVersion.VERSION_12
  sourceCompatibility = JavaVersion.VERSION_12
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
      "--output", "dist"
    )
  }
}