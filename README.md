Övningar för funktionell programmering i Java 8
==============

Övningar för [Mejslas][mejsla] kurs i funktionell programmering i Java 8.

## Bygga och köra

Detta repo är ett helt vanligt [Maven][maven]-projekt uppsatt för Java 8.
Utöver Java 8 används även [JUnit][junit] för testerna.

Har du Maven installerat och din `JAVA_HOME` pekandes på JDK8 ska du
kunna bygga projektet och köra alla tester från kommandoraden med:

    mvn test

Alla tester är "utkommenterade" med `@Ignore`, så en ren utcheckning av
detta repo bör resultera i att Maven konstaterar `[INFO] BUILD SUCCESS`.

Kommit så långt? Då är du redo att hacka på övningarna!

## Övningar

**Del 1 - streams:**  Använd streams för att implementera lösningar för
testerna i `src/test/nighthacking/ExercisesTest.java` (lånat från
  [NightHacking][nh]).

[mejsla]: http://www.mejsla.se/
[junit]: http://junit.org/
[maven]: http://maven.apache.org/
[nh]: https://github.com/NightHacking/LambdasHacking
