# Server API Dokumentation
## Grundlegendes
### SSL
Alle requests sind grundsätzlich per https durchzuführen, unverschlüsselte Anfragen werden vom Webserver abgelehnt.

### Content Types
Die API kann sowohl mit JSON als auch mit XML umgehen. Bei Anfragen muss stets der accept und content type im header auf den entsprechenden Wert gesetzt werden da die API sonst nicht weiß wie der request zu interpretieren ist.

### Authentifizierung
Die Authentifizierung erfolgt per JWT (Json Web Token) dieser kann unter folgender URL bezogen werden:
https://urban-action.max-karthan.de/TinderAPI/Token

An diese URL ist ein POST Request mit folgendem Body zu senden:
  username=<username>&password=<password>&grant_type=password

Der Server antwortet im JSON Format wobei das Feld "access_token" den entsprechenden Token enthält.
Dieser Token läuft nach 14 Tagen ab und es muss ein neuer Token generiert werden.

Außerdem gibt es in der Antwort folgende Felder:
 - ".expires": Datum an dem der Token abläuft
 - ".issued" : Zeitpunkt an dem der Token erstellt wurde
 - "expires_in" :  zeit bis der Token abläuft in sekunden
 - "token_type" : bearer
 - "userName" : username des users für den der Token generiert wurde

Für alle API Requests bis auf das Registrieren und das Generieren eines Tokens gilt die Requests müssen den Token zur Authentifizierung im Header enthalten.
Das Headerfeld Authorization muss folgenden Wert enthalten:
 Authorization: Bearer <token>
 
## Events
### Events auflisten
URL: https://urban-action.max-karthan.de/TinderAPI/api/Event
HTTP-Verb: GET
Parameter:
  category:
    typ: string, default = all, zulässige Werte: "sport","erholung","kultur","ausgehen","sonstiges"

    unzulässiger Wert liefert alle Kategorien.

  longitude:
    typ: double, default = -181, zulässige Werte: "-180 - 180"

    bei unzulässigem Wert wird die Umkreissuche ignoriert.
  latitude:
    typ: double, default = -91, zulässige Werte: "-90 - 90"

    bei unzulässigen Werten wird die Umkreissuche ignoriert.

  distance:
   typ: int, default = 5000, zulässige Werte: integer >= 0

    bei unzulässigen Werten wird die Umkreissuche ignoriert.

Beschreibung:
Diese Methode liefert die Events als JSON-Objekte. Es kann nach Kategorie und Umkreis gefiltert werden.
Wobei der Parameter distance den Umkreis in Metern von der angegeben Position (longitude und latitude) darstellt.
