## MyTA
MyTA, som er kort for "my teaching assistant", er et bookingsystem laget for Norsk Universitet for Ikke-tekniske Samfunnsvitere. Systemet tar utgangspunkt i tre brukere; student, læringsassistent og emneansvarlig.

Produktet har tre hovedbruksområder; booking av saltid, evaluering og kommunikasjon. 

* I booking kan emneveileder legge inn saltider. Læringsassistentene kan deretter gå inn og velge salstider som passer. Etter dette har studentene mulighet til å booke seg på time hos læringsassistent i valgt fag.
* Evalueringen gjør at en student kan laste opp en fil eller et arbeid som læringsassistenten etterpå kan vurdere og gi tilbakemelding på digitalt. 
* En meldingstjeneste gir kommunikasjonsmuligheter. Her kan man sende en melding til hvem som helst ved bruk av e-post-adressen.

## Motivasjon
Systemet skulle forenkle hverdagen til både studenter og læringsassistenter ved å erstatte den noe uforutsigbare og ikke-elektroniske ordningen de hadde tidligere. Samtidig skulle det administrative og organisatoriske stresset på de ansatte lettes, ved å legge til rette for nye og bedre samarbeidsordninger dem imellom, samt gjøre evalueringsarbeid lettere å gjennomføre. 


## Build status
![Build status](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/badges/master/build.svg)

## Kodestil
Vi har benyttet Googles kodestil for Java.

[Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
 
## Screenshots
Include logo/demo screenshot etc.

## Tech/framework used
Ex. -

<b>Built with</b>
- [Electron](https://electron.atom.io)

## Features
What makes your project stand out?

## Code Example
Show what the library does as concisely as possible, developers should be able to figure out **how** your project solves their problem by looking at the code example. Make sure the API you are showing off is obvious, and that your code is short and concise.

## Installation
Provide step by step series of examples and explanations about how to get a development env running.

## API Reference

Depending on the size of the project, if it is small and simple enough the reference docs can be added to the README. For medium size to larger projects it is important to at least provide a link to where the API reference docs live.

## Tests
Describe and show how to run the tests with code examples.

## How to use?
If people like your project they’ll want to learn how they can use it. To do so include step by step guide to use your project.

## Contribute

Let people know how they can contribute into your project. A [contributing guideline](https://github.com/zulip/zulip-electron/blob/master/CONTRIBUTING.md) will be a big plus.

## Credits
Give proper credits. This could be a link to any repo which inspired you to build this project, any blogposts or links to people who contrbuted in this project. 

#### Anything else that seems useful

## License
A short snippet describing the license (MIT, Apache etc)

MIT © [Yourname]()


----

1. Brukermanual
2. 
3. Trykk på den blå knappen "Go to login page".
4. Om du ikke har bruker fra før, trykk "register".
5. Fyll inn dine personalia i tekstboksene, og trykk på "confirm".
6. Om du trykte på "register" ved en feil, trykk på "login" i høyre hjørne for å returnere til innloggingssiden.
7. Fyll inn email (må være NTNU-adresse) i den første tekstboksen, og passord i den under merket "passord".
8. Trykk så på den blå knappen "login".
9. 
10. Om brukeren din er emneansvarlig, hopp til linje 41.
11. Om brukeren din er student eller studentassistent, fortsett å lese.
12. Du er nå på studentsiden.
13. Om du vil logge ut igjen, trykk på den svarte knappen "logout" i venstre hjørne.
14. 
15. Hvis du bare trenger å hvite hvordan studentassistentsiden fungerer, hopp til linje 29.
16. Før du kan booke saltid, må du legge til dine fag. 
17. Dette gjøres ved å trykke på knappen "Add subject". Der fyller man inn fagkoden i tekstboksen, og trykker på knappen "Add subject". 
18. Du får da meldingen "Course added", eller "Course does not exist" hvis faget ikke eksisterer.
19. Når man har lagt til de aktuelle fagene trykker man på "return" for å gå tilbake til studentsiden.
20. 
21. For å booke en saltid, trykk på den øverste blå knappen "book assistant times".
22. Når du vil returnere til studentsiden, trykker du på "Return" i høyre hjørne.
23. På "book assistant times"-siden må man først velge fag og hvilken uke man ønsker å booke time i.
24. Hvis det finnes ledige timer denne uken, vil de vises som hvite klikkbokser.
25. Marker de timene du eventuelt ønsker, og trykk på den nederste svarte knappen "Confirm booking". 
26. Du vil da få beskjed "Assistants time booked!" til venstre for knappen, og de markerte tekstboksene vil bli grønne som de andre bookede.
27. Når du har booket de timene du ønsket, trykker du på "return"-knappen. Evt. krysser du ut applikasjonen om du er helt ferdig.
28. 
29. For å gå fra studentsiden til studentassistentsiden, trykk på knappen "Teacher assistant" i høyre hjørne.
30. Dette vil bare skje om du er studentassistent i et fag, hvis ikke skjer det ingenting.
31. For å gå tilbake til studentsiden, trykk på "student" i høyre hjørne. 
32. For å registrere deg til en saltid som angitt av emneansvarlig, trykk på "Add assistant times".
33. Når du vil returnere til studentassistentsiden, trykker du på "Return" i høyre hjørne.
34. På "Select assistant times"-siden må man først velge fag og hvilken uke man ønsker å booke time i.
35. Om det er ledige tider den uken, vil man se dem som hvite firkanter, ellers er alle grønne.
36. "Huk av" de ønskede saltidene ved å trykke på firkantene. Disse vil da bli merket.
37. Når du har valgt dine ønskede tider, trykk "Confirm selected times".
38. Du vil da få beskjed "Assistants time selected!" til venstre for knappen, og de markerte tekstboksene vil bli grønne som de andre bookede.
39. Når du har booket de timene du ønsket, trykker du på "return"-knappen. Evt. krysser du ut applikasjonen om du er helt ferdig.
40. 
41. For å legge til en studentassistent, trykk på knappen "Add assistants".
42. Velg faget du ønsker å legge til en studass i kolonnen til venstre, f.eks. TDT4140 om det er det faget du er emneansvarlig i.
43. Skriv så inn NTNU-emailen til den aktuelle studenten du ønsker å gjøre til studentassistent i faget, og trykk på knappen "Confirm assistants". 
44. Du vil da få beskjed "Assistant added" til høyre for knappen, eller "invalid email" om du har skrevet inn en ugyldig bruker.
45. Når du har lagt til studentassistentene du ønsket, trykk på "return" i høyre hjørne.
46. For å lage studentassistenttimer, trykk på knappen "Create assistant times".
47. På "Create assistant times"-siden må du først velge fag og hvilken uke man ønsker å lage timer til, under henholdsvis "Course" og "Week".
48. Du må også velge hvor mange studentassistenter du ønsker skal jobbe den tiden, dette velger du under "Available places".
49. Trykk så på de hvite klikkboksene ved timene du ønsker å angi til studentassistentene for å velge dem, og trykk på "Confirm times"-knappen nederst til høyre.
50. Når du har trykket på knappen vil man få opp "Assistant times added!" under tittelen.
51. Når du har lagt til de aktuelle timene trykker du på "return"-knappen i høyre hjørne.
