TChallenges
==

## Was ist TChallenges?


TChallenges ist ein Plugin, das mehrere verschiedene Challenges
für Minecraft beinhaltet. Außerdem gibt es viele verschiedene 
Optionen. Für Entwickler gibt es eine API, mit der man eigene Challenges in das Plugin
hinzufügen kann.

## Challenges

- Dont Jump Challenge

## API

Mit der TChallenges API ist es möglich Events zu erkennen, und damit die Abläufe auf dem 
Server zu steuern. Lade dir dafür das neueste Release von TChallenges herunter. Du findest es [hier](https://github.com/TheWebcode/TChallenges/releases). Erstelle nun 
einen neuen Paper Plugin, und füge die API zu deinem Projekt hinzu. Danach ist es wichtig, die API in der plugin.yml zu vermerken, und eine Dependency auf 
den TChallenges Plugin zu erstellen:

```yaml
name: MyChallenge
version: 1.0
main: de.webcode.challenge.MyChallenge
api-version: 1.18
authors: [ TheWebcode ]
description: My Own Challenge for TChallenges
depend: [ TChallenges ]

```

Wichtig ist hierbei folgender Punkt:
```yaml
depend: [ TChallenges ]
```

### API Instanz erstellen

Es wird empfohlen für die Challenge eine eigene Hauptklasse, und nicht die Hauptklasse des 
Plugins zu verwenden. Erstellen einer Instanz zur API: 

```java
 TChallengesAPI api = TChallengesAPI.getInstance();
```

### Challenge - Hauptklasse

Die Hauptklasse der Challenge sollte nicht die Paper Hauptklasse sein. Sie muss die
Klasse TChallenge Extenden:

```java
public class Challenge extends TChallenge {
  
}
```

In der Spigot ```onEnable``` Methode ist es nun wichtig die Challenge dem Plugin hinzuzufügen:
```java
@Override
public void onEnable() {
    TChallengesAPI api = TChallengesAPI.getInstance();     
    api.addChallenge(new Challenge());
}
```

In der Klasse müssen nun einige Methoden Implementiert werden:

```java
public class Challenge extends TChallenge {
    
    
    @Override
    public void onChallengeEnable() {
        //Ausgeführt wenn die Challenge aktiviert wird
    }


    @Override
    public void onChallengeDisable() {
        //Ausgeführt wenn die Challenge deaktiviert wird
    }

    @Override
    public ArrayList<Listener> getEventlisteners() {
        //Eventlistener der Challenge
        return null;
    }

    @Override
    public ArrayList<TChallengeCommand> getCommands() {
        //Commands der Challenge
        return null;
    }
    @Override
    public String getName() {
        //Name der Challenge
        return "My Challenge";
    }

    @Override
    public String getAuthor() {
        //Entwickler der Challenge
        return "TheWebcode";
    }

    @Override
    public String getVersion() {
        //Version der Challenge
        return "1.0";
    }

    @Override
    public Material getChallengeIcon() {
        //Material das als Icon für die Challenge im Inventar verwendet 
        //werden soll
        return Material.DIAMOND;
    }

    @Override
    public JavaPlugin getInstance() {
        //Instanz zur Paper Hauptklasse
        return Main.getInstance();
    }
}
```

### Eventlistener hinzufügen

Eventlistener funktionieren genauso wie bei Paper Plugins. Nur das registrieren des Eventlisteners ist anders.
Zunächst musst du eine Eventlistener Klasse erstellen, hierbei ist es wichtig, wie zuvor ```@EventHandler``` von
```org.bukkit.event.EventHandler``` zu benutzen, und nicht ```@EventTarget```, auch wenn diese Anotation aus der API stammt:

```java
public class Eventlistener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().sendMessage("§aWillkommen auf dem Server!");
    }
}
```

Um das Event nun zu registrieren wird die ```registerListener()``` Methode der API benötigt.
Dieser wird die Challenge, und der jeweilige Eventlistener übergeben. Es wird empfohlen dies in der Hauptklasse der Challenge in der ```onChallengeEnable()```
Methode zu tun:

```java
    @Override
    public ArrayList<Listener> getEventlisteners() {
        ArrayList<Listener> listeners = new ArrayList<>();
        listeners.add(new Eventlistener());

        return listeners;
    }
```
Es können auch mehrere Eventlistener hinzugefügt werden.

Events werden nur aufgerufen, wenn die jeweilige Challenge auch aktiv ist.
Ist die Challenge deaktiviert, werden keine Events für diese Challenge aufgerufen.

### Einen Command hinzufügen

Commands funktionieren ähnlich wie in Paper. Zunächst wird eine Klasse für den Command erstellt.
Diese Klasse extended ```TChallengeCommand``` aus ```TChallengeCommand```.
Die Klasse muss die ```execute()``` Methode implementieren. Außerdem benötigt die Klasse einen Constructor, der
den Namen des Commands übergiebt. Für einen ```/mycommand``` ist dieser ```super("mycommand")```. Leerzeichen, Sonderzeichen, sowie Großbuchstaben und Spigot/Paper Farbcodes
sind nicht erlaubt. Die ganze Command Klasse sieht nun wie folgt aus:

```java
public class MyCommand extends TChallengeCommand {

    public MyCommand(){
        super("mycommand");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return false;
    }
}
```

Die ```execute()``` Methode funktioniert gleich wie bei Paper.
Die Commands funktionieren nur, wenn die Challenge aktiv ist. Ist die Challenge deaktiviert, funktionieren die Commands nicht.

Nun muss der Command nur noch registriert werden. Dies ist in der Challenge Hauptklasse möglich:

```java
    @Override
    public ArrayList<TChallengeCommand> getCommands() {
        ArrayList<TChallengeCommand> commands = new ArrayList<>();
        commands.add(new MyCommand());
        return commands;
    }
```


### Developers

2022 - Entwickelt von TheWebcode