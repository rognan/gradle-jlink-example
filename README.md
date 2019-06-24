# JLink example

    Use gradle to create a minimal runtime image using JLink.

Build & Run:

```bash
$ ./gradlew jlink
$ ./build/dist/bin/launch
$ curl localhost:8080
PONG
```

System property 'java.home' is expected to point to a JDK

```groovy
 System.properties.'java.home'
``` 

# LICENSE

MIT © 2018
