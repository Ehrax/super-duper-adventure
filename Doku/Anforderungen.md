# Nicht funktionale Anforderungen
1. Die Appp soll eine gewisee Robustheit aufweisen, damit im Falle eines
Abusturzes die App ohne Datetenverlust wied er herstestellt werden kann.

2. Die APp sollte möglichst einfach um weitere Funktionen erweriterbar werden
können.

3. Die Oberfläche sollte stehts auf Benuzereingaben reagieren.

4. Der Benutzer sollte sich duch eine intuitive und benutzerfreundliche 
Oberfläche schnell zurechtfinden.

# Funktionale Anforderungen
### FA#1 Accountmanagementsystem
Es ist möglich einen Account anzulegen und zu löschen. Der Benutzer muss die 
Möglichkeit haben, falls er das Passwort vergisst, seinen Account wiederhestellen
zu können.

### FA#2 Anlegen von Events
Dem Benutzer ist es möglich, mit möglichst wenigen Klicks ein neues Event 
anzulegen.  
Dieses Event hat folgende Parameter:
    - Dauer
    - Ort
    - Kategorie
    - Beschreibung
    - Bild

### FA#3 Übersicht aller Events in der Nähe
Der Benutzer soll übersichtlich alle Events in seiner näheren Umgebung
(Umkreis vom Benutzer konfigurierbar) angezeigt bekommen.

### FA#4 Gruppenchat
Innerhalb eines Events, dem der Benutzer beigetreten ist, hat er die Möglichkeit
sich mit anderen Teilnehmern in einem Gruppenchat auszutauschen. Dieser lässt 
sich Stummschalten.

### FA#5 Userprofil
Jeder Benuzer hat ein eigenes Profil mit Profilbild und einem Namen.

### FA#6 Kartenansicht
Ist man einem Event beigetreten, sieht man den Ort des Events auf einer Karte
und hat die Möglichkeit sich eine Route berechnen zu lassen.

### FA#7 Filter
Der Benutzer kann die Events nach bestimmten Kriterien filtern.
    - Umkreis
    - Kategorie
