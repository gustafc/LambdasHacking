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

## Att tänka på

- Du kan springa in i lite problem med Javas system för generics när du arbetar
  med denna uppgift. Så länge du vet att du gör rätt får du lösa dessa problem
  som du vill. "You must know when to break the rules!"
- Det är inte helt solklart vad som är "rätt" sätt att lösa t.ex. uppgiften för
  felhantering på. När du bygger din lösning, fundera lite över vilka alternativ
  du har. Vad ser du för för- och nackdelar med olika lösningar? Vad tror du är
  bäst sätt att lösa problemet på? Tycker du det fattats några felbeslut i
  den kod som finns till övningarna?

## Övningar

### Del 1 - streams

Använd streams och lambdas för att implementera lösningar för testerna i
`src/test/nighthacking/ExercisesTest.java` (lånat från [NightHacking][nh]).

### Del 2 - Återuppfinn några vanliga `Stream`-operationer

Många metoder på [`Stream`][api_stream] är egentligen bara enklare varianter av
andra metoder, som t.ex. `map` är en förenklad variant av `flatMap`. Exakt hur
de här operationerna hänger ihop kan du utforska i klassen `Reimplementation`
och dess test. Prova att skriva om metoderna i den klassen så att de inte
använder sin motsvarighet i `Stream`, utan någon mer generell variant.

Observera att det räknas som fusk att bygga en t.ex. en `java.util.List` som man
mellanlagrar datat i - du vill bara arbeta med strömmar i den här uppgiften!

**Bonusfråga:** Några operationer går inte att implementera helt optimalt bara
med de metoder som finns på `Stream`. Vilka då? Varför? Kan du tänka dig någon
operation man skulle kunna utöka `Stream` med som skulle kunna hjälpa?

### Del 3 - funktionell felhantering

Inom funktionell programmering är man inte helt förtjust i att kasta exceptions.

Kritikerna menar att exceptions i allmänhet fungerar mest som en sorts `GOTO`,
och hur det än är med den saken är det odiskutabelt så att checked exceptions
komplicerar för den som vill använda högre ordningens funktioner. Exempelvis får
inte en [`Function<T, R>`][api_fn] kasta checked exceptions eftersom signaturen
för `apply` inte har någon `throws` - alternativet till detta hade varit att
deklarera den som `R apply(T) throws Exception`, vilket inte heller hade varit
kul eftersom alla ställen som använder `Function` också hade tvingats ha `throws
Exception`.

Lösningen är istället något vi kan kalla för `Try<T>`, en klass som liknar
[`Optional<T>`][api_opt], fast istället för att representera ett eller inget
värde, motsvarar `Try` antingen ett värde eller ett exception.

Precis som med `Optional` kan vi fråga en `Try` om det har något värde, men vi
kan också använda metoder som `map` och `flatMap` för att behandla det
eventuella värdet.

I `errorhandling.TryTest` finns ett gäng utkommenterade tester som beskriver ett
sätt att bygga `Try` på. Titta igenom testerna och försök få dem att gå igenom.

#### Del 3.b - mer generell felhantering (överkurs, valfri)

En variant på `Try<T>` är `Either<Left, Right>`, en representation av något som
kan ha antagit ett av två värden, där den ena "sidan" (oftast left) motsvarar
ett fel, och den andra sidan ett "önskvärt" värde. Exempelvis skulle en
`Try<String>` kunna representeras av `Either<Exception, String>`.

Precis som för `Try` kan man använda operationer som `map` och `flatMap` för att
bearbeta värdet, som då låter vänstersidan förbli om det är ett "vänstervärde",
och bara bearbetar högersidan. Exempelvis skulle vi kunna tänka så här:

    Either<Exception, String> readFile(){
      try {
        String content = ...;
        return Either.right(content);
      } catch (Exception e) {
        return Either.left(e);
      }
    }

    Either<Exception, String> fileData = readFileContent();
    Either<Exception, Integer> fileAsInt = fileData.map(Integer::parseInt);
    Either<Exception, Person> lookupResult = fileAsInt.flatMap(id -> {
      try {
        return Either.right(someService.getPersonById(id));
      } catch(Exception e) {
        return Either.left(e);
      }
    });

Prova att implementera hela eller delar av den här klassen.

[mejsla]: http://www.mejsla.se/
[junit]: http://junit.org/
[maven]: http://maven.apache.org/
[nh]: https://github.com/NightHacking/LambdasHacking
[api_fn]: http://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html
[api_opt]: http://docs.oracle.com/javase/8/docs/api/java/util/Optional.html
[api_stream]:  http://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
