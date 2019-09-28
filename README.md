#Realizacija softverskog sistema za evaluaciju nastavnika

Seminarski rad Apeiron, predmet: Web Servisi i aplikacije

##Funkcionalnosti
Potrebno je realizovati softverski sitem koji omogućava unos ostvarenih rezultata pojedinačnih nastavnika. Osnovne opcije su:

**Upravljanje završnim radovima** - Pod upravljanjem završnim radovima podrazumijeva se dodavanje, brisanje i izmjena svih podataka vezanih za diplomu, npr. tip studija, institucija na kojima je diploma stečena, mentor, naučne oblasti na koje se diplomski rad odnosi… Za instituciju je potrebno omogućiti, kao i za neke druge podatke, izbor iz već definisanog skupa, takozvanog šifarnika, ali i unos naziva i mjesta institucije ukoliko tražena institucija nije među ponuđenima. Za naučne oblasti na koje se diplomski rad odnosi predviđen je šifarnik, razlika između ovog i ostalih šifarnika, čije korišćenje treba omogućiti nastavniku, je u tome što jedan diplomski rad može odnositi na više oblasti. Potrebno je obezbijediti istu funkcionalnost i za nostrifikovane diplome.

**Upravljanje projektima** - Pod upravljanjem projektima podrazumjeva se dodavanje, brisanje i izmjena svih podataka vezanih za projekte u kojima nastavnik učestvuje ili je učestvovao. Ti podaci su naziv, vrsta projekta i godine početka odnosno zavešetka rada na projektu. Za vrstu projekta nije predviđen šifarnik, pošto trenutno nije bilo moguće izvršiti klasifikaciju vrsti projekata, pa je potrebno dozvoliti nastavniku slobodan unos vrste projekta.

**Upravljanje mentorskim radovima** - Pod upravljanjem mentorskim radovima podrazumeva se dodavanje, brisanje i izmjena svih podataka vezanih za mentorski rad nastavnika. Ti podaci su vrsta mentorstva, naziv rada i podaci o kandidatu. Za vrste mentorstva predviđen je šifarnik i nastavniku treba omogućiti samo izbor jednog od ponuđenih vrsta mentorstva.

**Upravljanje aktivnostima nastavnika** - Pod upravljanjem aktivnostima nastavnika podrazumijeva se ažuriranje zbirnih podataka naučne, umjetničke i stručne aktivnosti nastavnika. U to spadaju broj citata u časopisima sa SCI(SSCI) liste, broj radova sa SCI(SSCI) liste, broj domaćih i međunarodnih projekata u kojima nastavnik trenutno učestvuje kao i stručna usavršavanja koja ja nastavnik pohađao.

**Upravljanje ostalim podacima relevantnim nastavniku** - Pod upravljanjem ostalim podacima relevantnim nastavniku podrazumijeva se dodavanje, brisanje i izmjena podataka koje nastavnik smatra relevantnim, a nisu predviđeni ni jednom od gore navedenih grupa podataka.

##Razvoj
Za projekat je potrebno imati:

maven

java 11

mysql

projekat se pokrece sa

./mvnw
