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
### Events auflisten und Filtern
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

Response:
  [
    Members":[
      {
        "Claims":[],
        "Events":[],
        "Logins":[],
        "Roles":[],
        "ProfileImage":null, // Base64 Codiertes Image
        "Email":"ich@max-karthan.de",
        "EmailConfirmed":false,
        "PasswordHash":"AOMHdivFRxEPH1HK8l5cpZsKUuJhrHpMhaQ3wjClOTdIw3XskcjqIfUQZvU4vdIXlw==",
        "SecurityStamp":"07d1dfb7-1e9a-40ba-b396-e650e8026005",
        "PhoneNumber":null,"PhoneNumberConfirmed":false,
        "TwoFactorEnabled":false,"LockoutEndDateUtc":null,
        "LockoutEnabled":false,"AccessFailedCount":0,
        "Id":"6dcbf60c-9288-4274-9cca-756bb66c25e4",
        "UserName":"max"
      }
    ],
    "EventModelID":8,
    "Title":"testeventasdasd",
    "Description":"testdescription",
    "EndDate":"2017-06-02T17:11:46.007",
    "UserCounter":0,
    "MaxUsers":5,
    "Latitude":75.25,
    "Longitude":175.48,
    "Category":2,
    "Creator":{
      ...
    },
    "EventImage":null // Base64 Codiertes Image
    }
  ]

Aktuell werden der Einfachheit halber alle Informationen der Member und des Creators die in der Datenbank vorhanden sind zurückgegeben.
(Im Produktivsystem wäre dies natürlich nicht so, aber der Fokus liegt erstmal auf der App deshalb aus Zeitgründen diese Lösung).

### Ein einzelnes Event anzeigen
URL: https://urban-action.max-karthan.de/TinderAPI/api/Event/{id}
HTTP-Verb: GET  

Beschreibung:
  Diese Methode liefert ein Einzelnes Event anhand seiner EventModelID.

Response:
 Analog zu oben aber eben nur ein Element.

### Ein Event erstellen
URL: https://urban-action.max-karthan.de/TinderAPI/api/Event
HTTP-Verb: POST
Parameter:
  title:
    typ: string

  description (optional) :
    typ: String

  timespan:
    typ: long, zulässige Werte > 0

  maxUsers:
    typ: integer

  category:
    typ: Integer, zulässige Werte: 0 = Sport, 1 = Ausgehen, 2 = Erholung, 3 = Kultur, 4 = sonstiges

  longitude:
    typ: double, default = -181, zulässige Werte: "-180 - 180"

  latitude:
    typ: double, default = -91, zulässige Werte: "-90 - 90"

  imagebase64:
    typ: String, zulässige Werte: ein jpeg oder png Bild in Base64 Codiert
    Falls der String kein Bild im jpeg oder png Format darstellt liefert die API einen 500er StatusCode mit der Message "NotAImage"

Beschreibung:
Diese Methode erstellt ein neues Event in der Datenbank.

### Einem Event beitreten
URL: https://urban-action.max-karthan.de/TinderAPI/api/Event/{id}/join
HTTP-Verb: PUT
Parameter:
  id:
    typ: Integer, die EventModelID des Events dem beigetreten werden soll

### Ein Event verlassen
URL: https://urban-action.max-karthan.de/TinderAPI/api/Event/{id}/leave
HTTP-Verb: PUT
Parameter:
  id:
    typ: Integer, die EventModelID des Events das verlassen werden soll

### Ein Event manuell löschen
URL: https://urban-action.max-karthan.de/TinderAPI/api/Event/{id}
HTTP-Verb: DELETE
Parameter:
  id:
    typ: Integer, die EventModelID des Events das gelöscht werden soll

Der Bearer-Token im Header muss dem User zugeordnet werden können, der das Event erstellt hat.
Ist dies nicht der Fall antwortet die API mit einem 401 Unauthorized Status Code.
