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
 
## Skjermbilder
**Generelt:**
[Startside](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/39aeab2301a6e21659cb7196d3073bd9/home.PNG)
[Registrer](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/dee04af6a46149a072a90658f3cb5ade/register.PNG)
[Logg inn](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/931020e275b48f0e6f06f59a8ce3f97b/login.PNG)
[Skriv melding](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/884c35ed0876e79ffc007e430de5be6f/writemessage.PNG)
[Se meldinger](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/07050a8e1d8140a840527ff22bd17d30/viewmessages.PNG)
[Les melding](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/7702965156e0833baf4140dbd41258cc/readmessage.PNG)

**Student:**
[Side for student](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/e1ab21869e9adbc025b4bf3c650f508d/studentpage.PNG)
[Book saltid (samme for studass & student)](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/0274c86e803443c358b1e1a9c84a7f30/bookassistanttimes.PNG)
[Send inn innlevering](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/fccfae15f0264683775734ff05992ee4/uploadassignment.PNG)
[Se vurderinger](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/27da5d0dfaa55e8f1af1a417603d32bc/viewevaluations.PNG)
[Legg til emne](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/67f94fdde1c70490f2c4790b6368d975/addsubject.PNG)
[Kalender](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/f9d6e57ee8a67fb0f2ba95798575b114/calendar.PNG)

**Studass:**
[Side for studass](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/47f077d6b696a15eeabb7aa26a4f4b8b/assistantpage.PNG)
[Book saltid (samme for studass & student)](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/0274c86e803443c358b1e1a9c84a7f30/bookassistanttimes.PNG)
[Se innleveringer](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/35eb5b94491ee935c9e6df67c7bde47a/viewassignments.PNG)
[Legg til vurdering](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/612e8acff67969d3f09e121ae91fbdd6/addevaluation.PNG)


*Studass har i tillegg tilgang til alle sidene som student har tilgang til, siden man kan være både studass og student.*

**Emneveileder:**
[Side for emneveileder](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/096437f4cd6db8787f10d4b98356ab15/supervisorpage.PNG)
[Legg til studass](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/9108f2e4781a7146e4d977f586ebc8a8/addassistant.PNG)
[Angi saltider studasser kan velge](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/d59a6c69c838d3f1835c6d4aff18ab1e/addassistanttimes.PNG)

**Admin:**
[Legg til emneveileder](https://gitlab.stud.idi.ntnu.no/programvareutvikling-v19/gruppe-18/wikis/uploads/17fb5d94321b75639d0b50f5030a9c77/addsupervisor.PNG)

## Teknologi/rammeverk
**Laget med**
- [Eclipse](https://www.eclipse.org/)
- [SceneBuilder](https://gluonhq.com/products/scene-builder/)
- [MySQL Workbench](https://www.mysql.com/products/workbench/)

**Plug-ins**
- [Buildship for Eclipse](https://projects.eclipse.org/projects/tools.buildship)

## Features
Pent og brukervennlig design. Rask og effektiv booking. Bookinger for studass og student i en og samme kalender. Kommunikasjon mellom brukere.

## Kodeeksempel
Kodeeksempel som demonstrerer noen viktige funksjoner:

```
	public static void main(String[] args) {
		//Requires that GUI is running to work
		App instance = App.getInstance();
		instance.userRegister("epost@ntnu.no", "passord123", "Ola", "Nordmann", false); //false: check if user exists
		instance.userLogin("epost@ntnu.no", "passord123"); //success
		
		//Gets avaialble bookings from database and picks the first two
		ArrayList<Booking> availableBookings = DBBooking.getAvailableBookingsStudent();
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		bookings.add(availableBookings.get(0));
		bookings.add(availableBookings.get(1));
		
		//Inserts selected bookings to database
		DBBooking.addHalltimeStudent(bookings);
		
		instance.userLogout();
	}
```


## Installasjon
**Kjøre programmet uten utviklingsmiljø:**
1. [Last ned](release/myTA.jar) jar-filen som ligger i release-mappa
2. Sørg for at du er koblet til et NTNU-nettverk, for eksempel gjennom VPN
3. Kjør programmet ved å dobbelklikke på fila. Hvis det ikke fungerer åpne kommandolinja og skriv "java -jar <bane til fila>", uten apostrof
4. Benytt brukermanualen lenger nede dersom det skulle være behov

**Sette opp utviklingsmiljø:**
1.  Sørg for at Java er installert. JavaFX dependency-en som benyttes i prosjektet krever Java 11 eller høyere
2.  Installer Eclipse. (IntelliJ f.eks. kan også brukes, men denne brukerveiledningen tar utgangspunkt i Eclipse)
3.  Installer Buildship plugin-en til Eclipse. Denne benytter Gradle wrapperen som allerede ligger i prosjektet til å håndtere dependencies ol., så Gradle trenger ikke å installeres.
4.  Start Eclipse på nytt.
5.  Importer prosjektet. Ønsker man å klone dette prosjektet går man på "File" > "Import..." > "Projects from Git" > "Clone URI". Man går så inn på prosjektsiden, trykker "Clone" og kopierer deretter adressen under "Clone with HTTPS". Denne adressen limes inn under "URI" i Eclipse, så trykk "Next >" og "Next >" igjen hvis man vil ha alle branchene. Velg "Import existing Eclipse project" > "Next >" og deretter "Finish".
5.  Hvis dependencies ikke lastes inn ordentlig, høyreklikk på prosjektet og trykk deretter "Gradle" > "Refresh Gradle Project". Hvis ikke "Gradle"-menyen vises høyreklikk på prosjektet og deretter "Configure" > "Add Gradle Nature"
6.  Implementasjonen vår benytter en database på NTNU-serverne. Ønsker man å sette opp en egen database kan man kjøre [dbscript.sql](dbscript.sql)-fila i rotmappa til prosjektet og endre innloggingsdetaljer i [DataSource.java](src/main/java/database/DataSource.java)-fila under "src/main/java/database". Ønsker man å sette den opp lokalt kan man f.eks. åpne en lokal server i MySQL Workbench og kjøre innholdet i fila som en query, så vil alt legges inn. Deretter setter man variabelen "DB_URL" til "localhost" i DataSource-fila, og "DB_USER" og "DB_PASSWORD" til brukernavn og passord på den lokale serveren.
7.  Ønsker man å kompilere en jar med alle nødvendige dependencies kan man enkelt gjøre dette ved å ved å velge "shadowJar"-oppgaven under "shadow"-mappa i "Gradle Tasks". Filen lagres lokalt i "build\libs\<prosjektnavn>-all.jar"

## API referanser
* [MySQL Connector/J Reference](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference.html)
* [Apache Commons DBCP 2.6.0 Reference](https://commons.apache.org/proper/commons-dbcp/xref/index.html)
* [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html)
 
## Tester
Med Buildship installert er det eneste man trenger å gjøre å åpne mappa for prosjektet i "Gradle Tasks" og deretter "verification" > "test". Denne oppgaven kjører alle JUnit testene som ligger under "src/test/java". Hvis alle testene passerte skal det være en grønn hake ved siden av ":test". Det kan også nevnes at hvis man benytter CI/CD konfigurasjonen som vi har benyttet så kjøres denne oppgaven for hver commit av GitLab-runneren.

## Brukermanual
1. Kjør JAR-filen.
2. Trykk "Go to login page".
3. Skriv inn innloggingsinformasjon og trykk "Log in". Hvis du ikke allerede har en bruker, trykk "Registrer" og fyll inn skjemaet.

**[ADMIN] Angi en emneveileder:**
1. Opprett faget hvis det ikke allerede eksisterer ved å skrive inn emnekode og navn, og deretter trykk "Add subject".
2. Skriv inn e-postadressen til emneveilederen og angi fagkode der vedkommende skal være emneveileder.

**[EMNEVEILEDER] Sette opp saltid i fag:**
1. Trykk "Create assistant times".
2. Angi saltimer i uka ved å merke av sjekkboksene i tabellen. Merker man f.eks. raden "08:00" oppretter man saltid i tidsrommet 8 til 10 i gjeldende uke.
3. Angi ledige plasser, fag og uke. Deretter trykk "Confirm times".
4. Merker man av en saltid som allerede eksisterer blir antall ledige plasser skrevet over, så hvis man ønsker å oppdatere antall ledige plasser lager man bare en ny saltid for gjeldende tidsrom.
 
**[EMNEVEILEDER] Legg til studass**
1. Trykk "Add assistants"
2. Velg fag og oppgi e-postadressen til ønsket studass
 
**[STUDASS] Angi saltid hvor en er tilgjengelig:**
1. Trykk "Teacher assistant" oppe i høyre hjørne
2. Trykk "Add assistant times"
3. Velg fag og uke
4. Angi tidsintervall der en er tilgjengelig
5. Trykk "Confirm selected times"
 
**[STUDASS] Legg til vurdering:**
1. Trykk "Teacher assistant" oppe i høyre hjørne
2. Trykk "Evaluating"
3. Velg emne. Hvis ønskelig, sorter tabellen etter valgt kolonne ved å trykke på kolonnenavnet
4. Velg øving
5. Hvis fil er vedlagt, last ned fil ved å trykk "Download"
6. Skriv inn score og eventuelt kommentar.
7. Trykk "Confirm score"
 
**[STUDENT\*] Book studass:**
1. Trykk "Book assistant times"
2. Velg fag og uke
3. Velg bookingtid(er) (max 2 per uke per fag med vår implementasjon)
4. Trykk "Confirm booking"

**[STUDENT\*] Legg til fag:**
1. Trykk "Add subject"
2. Skriv in fagkode
3. Trykk "Add subject"

**[STUDENT\*] Send inn innlevering:**
1. Trykk "Evaluating"
2. Trykk "Upload assignment"
3. Velg fag og skriv inn navn på øving
4. Velg fil hvis ønskelig
5. Trykk "Upload"

**[STUDENT\*] Se vurderinger:**
1. Trykk "Evaluating"
2. Trykk "View evaluations"
3. Velg fag

*\*Studass har i tillegg tilgang til alle sidene som student har tilgang til, siden man kan være både studass og student*

**[STUDASS/STUDENT] Se timeplan:**
1. Trykk "My Calendar" oppe i venstre hjørne.
2. Velg uke
3. Oransje er bookinger der man er studass og en student har meldt seg på. Rosa er bookinger man har gjort som student

**[EMNEVEILEDER/STUDASS/STUDENT] Sende melding:**
1. Trykk "Messages"
2. Trykk "New message"
3. Skriv inn mottaker, emne og tekst
4. Trykk "Send Message"

**[EMNEVEILEDER/STUDASS/STUDENT] Lese melding:**
1. Trykk "Messages"
2. Trykk på meldingen ønsker å lese

## Bidra
Utviklingen på dette prosjektet er avsluttet. Ønsker man å bidra anbefales det at man kopierer prosjektet og igangsetter en kollaborativ innsats derifra.

## Honnør
Takk til NTNU, emneveiledere, studasser og alle involvert. Dette har vært riktig gøy.

## Lisens
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org>
