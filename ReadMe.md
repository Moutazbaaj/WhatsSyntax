# WhatsSyntax API

## Quickstart

### Gruppe anmelden und API-Key bekommen

Um Zugang zur API zu bekommen, brauchst du oder deine Gruppe einen API-Key. Meldet euch dafür
einfach
beim Lehrteam. Deinen oder euren API-Key könnt ihr frei wählen, teilt uns diesen einfach mit.

Jeder Zugang hat seine eigenen Daten, damit ihr euch die nicht gegenseitig überschreibt.

### Base-URL

Die Base-URL lautet: http://81.169.201.230:8080

### Gruppen Endpunkt

Jeder Endpunkt dieser API beginnt mit:

```
/group/{number}/...
```

Number ist in diesem Fall eine Pfad-Variable, die bei jeder Request mitgegeben werden muss.
Wenn du dir nicht mehr sicher bist, wie das funktioniert, sieh nochmal in die Folien von Tag 6.4!

### API-Key

Jeder Request muss ein Query-Parameter mitgegeben werden, um sich bei der API zu authentifizieren.
Das heißt, jeder Aufruf endet mit:

```
?key=deinkey
```

Wenn du dir nicht mehr sicher bist, wie das funktioniert, sieh nochmal in die Folien von Tag 6.4!

### Beispiel-Request

Nehmen wir an, meine Gruppe ist die Gruppe "1" und mein API-Key lautet "test".
Eine Anfrage an den Endpunkt "/chats" würde dann wie folgt aussehen:

```
http://81.169.201.230:8080/group/1/chats?key=test
```

Eine Funktion in unserem API-Interface könnte so aussehen:

```kotlin
@GET("/group/{number}/chats")
suspend fun getChats(@Path("number") number: Int, @Query("key") key: String): List<Chat>
```

Beim Aufruf dieser Funktion aus dem Repository geben wir dann einfach unsere Nummer und den Api-Key als Parameter mit:

```kotlin
val number = 1
val key = "test"
...
val result = api.retrofitService.getCalls(number, key)
```

## Endpunkte

### Chats (GET)

Gibt eine Liste an Chats zurück. Wenn Anfrage fehlerhaft, wird eine leere Liste zurückgegeben.

```
GET
/group/{number}/chats
```

### Contacts (GET)

Gibt eine Liste an Kontakten zurück. Wenn Anfrage fehlerhaft, wird eine leere Liste zurückgegeben.

```
GET
/group/{number}/contacts
```

### Calls (GET)

Gibt eine Liste an Anrufen zurück. Wenn Anfrage fehlerhaft, wird eine leere Liste zurückgegeben.

```
GET
/group/{number}/calls
```

### Profile (GET)

Gibt ein Profil zurück. Wenn Anfrage fehlerhaft, wird ein leeres Profil zurückgegeben.

```
GET
/group/{number}/profile
```

### Get Messages By Chat-Id (GET)

Gibt eine Liste an Nachrichten zurück. Dafür muss die Id des Chats als Path-Variable mitgesendet werden.
Wenn Anfrage fehlerhaft, wird eine leere Liste zurückgegeben.

```
GET
/group/{number}/chat/{chatId}
```

### Profile (POST)

Speichert (überschreibt) die Werte des Profils. Die Anfrage erwartet hierfür einen Body vom Typ Profil.
Ist die Anfrage fehlerhaft, wird das Update nicht ausgeführt.

```
POST
/group/{number}/profile
```

### New Message (POST)

Speichert eine neue Nachricht in den Chat mit der angegebenen Id. Die Anfrage erwartet hierfür einen Body vom Typ Message.
Ist die Anfrage fehlerhaft, wird die Nachricht nicht erstellt.

```
POST
/group/{number}/chats/{chatId}/new-message
```