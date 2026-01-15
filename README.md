# Deployment
https://cooking.planine.hr/

# Cooking Flamingoz
Projektna aplikacija za online tečajeve kuhanja kroz video-lekcije na zahtjev i live radionice

# Opis projekta
Ovaj projekt je reultat timskog rada u sklopu projeknog zadatka kolegija [Programsko inženjerstvo](https://www.fer.unizg.hr/predmet/proinz) na Fakultetu elektrotehnike i računarstva Sveučilišta u Zagrebu. Naš zadatak je izraditi u potpunosti funkcionalnu aplikaciju unutar koje će korisnici moći pohađati razne kulinarske tečajeve internetskim putem te koji će biti popraćeni detaljnim receptima, zadacima i slično. Nadamo se da će aplikacija spojiti ljude s raznih lokacija u zajedničkoj želji za kulinarskim znanjem ili da će biti od pomoći ljudima sa strašću za kuhanjem, ali s nedostatkom vremena za pohađanje tečajeva uživo. Također se nadamo da ćemo pritom obogatiti naše znanje o programskom inženjerstvu i steći ideju o provođenju tog procesa u stvarnoj situaciji.

# Ostvarene Funckije
- Korisnik se moze registrirati u sustav preko email-a
- Korisnik se moze prijaviti u sustav i dobiti JWT session

# Funkcijski zahtjevi
- Korisnik ima mogućnost prijave na pohađanje tečaja
- Korisnik ima mogućnost prijave/registracije u sustav
- Korisnik može postati instruktor kroz zasebnu formu
- Instruktor ima mogućnost objave javnog tečaja (zajedno s modulima i lekcijama)
- Admin ima pristup svim zahjevima za objavu tečaja te ima pravo odobriti ili odbiti objavu prije nego što postane javno dostupna
- Admin ima pristup svim zahtjevima da korisnici postanu instruktori te ih ima pravo odobriti ili odbiti 

# Tehnologije
- Backend: Spring boot
- Frontend: React, JS, HTML, CSS
- Database: PostgreSql, Redis

# Instalacija - DEV
Osigurati tocne verzije NodeJS, Java JDK 17, Maven, PostgreSQL, psql
Lokalno napravite bazu podataka, i zapamtite njezin naziv, naziv korisnika koji ga moze otvoriti te lozinku za tog korisnika.
VAŽNO! Te vrijednosti pohranite u `.env` u vasem lokalnom klonu projekta.

## Namjestanje .env lokalno
.env je datoteka koju ne mergate na projekt, ona je ignorirana u .gitignore.
U njoj morate pohraniti podatke vase lokalne baze podataka koju koristite za pokretanje.
1. Napravite .env na root na vasem lokalnom klonu
2. Kopirajte sadrzaj iz .env.local i stavite u .env
3. Promijenite <STVARI> sa vasom informacijom

Ubuduce necete vise trebat mjenjat podatke da bi pokreniti backend. Sve sto trebate je imati .env na root.

## Migracije
Za ovaj dio je potrebno postaviti psql na PATH u enviorment variables.
Ovisno o terminalu koji koristite ucinite sljedece:
### Powershell
1. Otvori powershell u .\migration i pokreni `.\run_all_migrations.ps1`
2. Ako je sve ispravno napravljeno, pojavi se obavijest napravljenih promjena ili error da su te promjene vec napravljene
### Bash (GitBash)
1. Otvori gitbash u .\migration i pokreni `.\run_all_migrations.sh`
2. Ako je sve ispravno napravljeno, pojavi se obavijest napravljenih promjena ili error da su te promjene vec napravljene
### U terminal bez skripte
1. Otvorite terminal u .\migrations, zamijenite s informacijom vase lokalne baze i pokrenite sljedece:
  1. `$env:PGPASSWORD="<DATABASE_PASSWORD>"`
  2. Za svaku `.sql` datoteku pokreni:
     `psql -h localhost -p 5432 -U <DATABASE_USERNAME> -d <DATABASE_NAME> -f .\<IME_DATOTEKE>.sql`
### psql nije postavljen na PATH
Treba rucno kopirati sql svake datoteke u migrations i pokrenuti u skripti u lokalnoj bazi podataka

## Pokretanje backend-a
Za ovaj dio je potreno imati `.env` ispunjen sa informacijom vase lokalne baze podataka
1. Otvorit terminal u ./backend i pokrenut sljedece:
  `mvn spring-boot:run`
>> Ako se nije pojavio Build failure/success, onda je backend API uspjesno pokrenut

>> Ako je ovo prvi put da pokrecete backend, mozda ce trebat prvo pokrenut `mvn clean install` prvo

## Pokretanje frontend-a
Za ovaj dio je potreno imati NodeJS ispravno uspostavljen
1. Otvorit drugi terminal u .frontend/ i pokrenut sljedece:
  1.  `npm install`
>> Sad imate node_modules instalirane
  2.  `npm run dev`
>> Ako se pojavi link za stranicu, onda je frontend uspjesno pokrenut

## Linux
 Osigurati tocne verzije NodeJS
 Pokrenuti PostgreSQL servis i pokrenuti sve migracije sa `make migrations`
 Pokrenuti backend sa `make run-backend-dev`
 Pokrenuti frontend sa `make run-frontend-dev`

## Otvaranje lokalno
 Backend API je dostupan na localhost:8890
 
 Frontend je dostupan na localhost:5173

# Članovi tima 
> - Sebastijan Kopsejak - Team Lead, devops
> - Tin Jovanović - Backend
> - Filip Polenus - Backend
> - Antonio Gospodnetić - Database
> - Vedran Delić - Frontend, Design
> - Leona Marijanović - Frontend 
> - Karlo Klauški - Frotend

# 📝 Kodeks ponašanja [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)
Kao studenti sigurno ste upoznati s minimumom prihvatljivog ponašanja definiran u [KODEKS PONAŠANJA STUDENATA FAKULTETA ELEKTROTEHNIKE I RAČUNARSTVA SVEUČILIŠTA U ZAGREBU](https://www.fer.hr/_download/repository/Kodeks_ponasanja_studenata_FER-a_procisceni_tekst_2016%5B1%5D.pdf), te dodatnim naputcima za timski rad na predmetu [Programsko inženjerstvo](https://wwww.fer.hr).
Očekujemo da ćete poštovati [etički kodeks IEEE-a](https://www.ieee.org/about/corporate/governance/p7-8.html) koji ima važnu obrazovnu funkciju sa svrhom postavljanja najviših standarda integriteta, odgovornog ponašanja i etičkog ponašanja u profesionalnim aktivnosti. Time profesionalna zajednica programskih inženjera definira opća načela koja definiranju  moralni karakter, donošenje važnih poslovnih odluka i uspostavljanje jasnih moralnih očekivanja za sve pripadnike zajenice.

Kodeks ponašanja skup je provedivih pravila koja služe za jasnu komunikaciju očekivanja i zahtjeva za rad zajednice/tima. Njime se jasno definiraju obaveze, prava, neprihvatljiva ponašanja te  odgovarajuće posljedice (za razliku od etičkog kodeksa). U ovom repozitoriju dan je jedan od široko prihvačenih kodeks ponašanja za rad u zajednici otvorenog koda.

# 📝 Licenca
Važeća (1)
[![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

Ovaj repozitorij sadrži otvoreni obrazovni sadržaji (eng. Open Educational Resources)  i licenciran je prema pravilima Creative Commons licencije koja omogućava da preuzmete djelo, podijelite ga s drugima uz 
uvjet da navođenja autora, ne upotrebljavate ga u komercijalne svrhe te dijelite pod istim uvjetima [Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License HR][cc-by-nc-sa].
>
> ### Napomena:
>
> Svi paketi distribuiraju se pod vlastitim licencama.
> Svi upotrijebleni materijali  (slike, modeli, animacije, ...) distribuiraju se pod vlastitim licencama.

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: https://creativecommons.org/licenses/by-nc/4.0/deed.hr 
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg

Orginal [![cc0-1.0][cc0-1.0-shield]][cc0-1.0]
>
>COPYING: All the content within this repository is dedicated to the public domain under the CC0 1.0 Universal (CC0 1.0) Public Domain Dedication.
>
[![CC0-1.0][cc0-1.0-image]][cc0-1.0]

[cc0-1.0]: https://creativecommons.org/licenses/by/1.0/deed.en
[cc0-1.0-image]: https://licensebuttons.net/l/by/1.0/88x31.png
[cc0-1.0-shield]: https://img.shields.io/badge/License-CC0--1.0-lightgrey.svg

### Reference na licenciranje repozitorija
