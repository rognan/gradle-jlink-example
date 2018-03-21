# JLink example

    Use gradle to create a minimal runtime image (22M) with JLink.

Build & Run:

```bash
$ ./gradlew jlink
$ ./build/dist/bin/launcher
$ curl localhost:8080
PONG
```

# LICENSE

MIT © 2018