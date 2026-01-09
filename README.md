# Spring Boot Pizzeria - Dokumentácia k projektu

Tento projekt je implementácia webovej aplikácie pre pizzeriu v rámci predmetu Webové aplikácie na platforme Java.

## 🚀 Spustenie projektu
1. Importujte projekt do IntelliJ IDEA ako Maven projekt.
2. Otvorte rozhranie **phpMyAdmin**.
3. Kliknite na **Import**.
4. Vyberte súbor db_full.sql z adresára database/.
5. Kliknite na **Vykonať (Go)**. 
   - Skript automaticky vytvorí databázu `Pizzeria` a naplní ju demo dátami.
6. Skontrolujte nastavenia v `src/main/resources/application.properties` (prihlasovacie údaje k DB).
7. Spustite aplikáciu a otvorte `http://localhost:8080`.

## 👤 Testovacie účty

Pre otestovanie rôznych úrovní oprávnení a prístupov môžete použiť nasledujúce preddefinované účty. Všetky účty majú rovnaké heslo.

| Rola | Meno a Priezvisko | Email (Prihlasovacie meno) | Heslo |
| :--- | :--- | :--- | :--- |
| **Administrátor** | Liubym Naval | `admin@gmail.sk` | `123456789Password` |
| **Kuchár** | Peter Kuchár | `kuchar@gmail.sk` | `123456789Password` |
| **Kuriér** | Michal Kuriér | `kurier@gmail.sk` | `123456789Password` |

> [!TIP]
> Po prihlásení ako **Administrátor** máte prístup k správe používateľov, produktov a kompletnému prehľadu objednávok. Kuchár a kuriér majú rozhrania prispôsobené ich úlohám v procese prípravy a doručenia.
