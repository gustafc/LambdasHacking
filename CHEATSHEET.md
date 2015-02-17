Lathund för lambdas och strömmar
================================

### Lambdas/metodrefenser

Följande lambauttryck/metodreferenser är ekvivalenta:

    Function<String, Integer> toInt = (String s) -> Integer.parseInt(s);
    Function<String, Integer> toInt = s -> Integer.parseInt(s);
    Function<String, Integer> toInt = Integer::parseInt;

Följande lambauttryck/metodreferenser är ekvivalenta:

    Function<String, Integer> strlen = (String s) -> s.length();
    Function<String, Integer> strlen = s -> s.length();
    Function<String, Integer> strlen = String::length;

Följande lambauttryck/metodreferenser är ekvivalenta:

    String data = "Lots of datas go here";
    Function<String, Integer> find = (String part) -> data.indexOf(part);
    Function<String, Integer> find = part -> data.indexOf(part);
    Function<String, Integer> find = data::indexOf;

### Strömmar

Få ut en ström från en array:

    String[] arr = {"foo", "bar", "baz"};
    Stream<String> stream = Stream.of(arr);

Få ut en ström från en collection:

    List<String> strings = ...;
    Stream<String> stream = strings.stream();;

Göra en ström till en lista:

    Stream<String> strings = ...;
    List<String> stream = strings.collect(Collectors.toList());

Filtrera ut vissa specifika element ur en ström:

    Stream<String> strings = ...;
    Stream<String> stringsContainingOK = files.map(s -> s.contains("OK"));

Transformera varje element i en ström till ett annat element:

    Stream<Integer> numbers = ...;
    Stream<String> hex = numbers.map(Integer::toHexString);


Transformera varje element i en ström till ett godtyckligt antal andra
element:

    Stream<Country> countries = ...;
    Stream<City> citiesInAllCountries = countries.flatMap(country -> {
      List<City> citiesInThisCountry = country.getCities();
      return citiesInThisCountry.stream();
    });
