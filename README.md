Create minimal runtime image using [Gradle](https://gradle.org/) and [JLink](https://docs.oracle.com/javase/9/tools/jlink.htm).

Build & Run:

```bash
$ ./gradlew jlink
$ ./build/image/bin/launch
$ curl localhost:8080
PONG
```

System property 'java.home' is expected to point to a JDK

_MIT © 2018_
