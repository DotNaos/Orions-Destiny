## Von Oliver, Timon, Domenik


# Config
>JDK-17 wird benötigt in der Run bzw. Build config von IntelliJ

[Adoptium Seite](https://adoptium.net/de/)

[Adoptium Github](https://github.com/adoptium/temurin17-binaries/releases/tag/jdk-17.0.6%2B10)


# Orions-Destiny
Ein 2D Platformer über ein Wesen, das gegen mächtige Gegner kämpft und an ihnen stärker wird.

# Beschreibung 
Ein 2D-Platformer, in dem man in verschiedenen Leveln gegen Gegner kämpft.
Nach besiegen dieser Gegner erhält man deren Fähigkeiten und kann diese gegen die nächsten gegner einsetzen.
Zu Beginn soll man zwischen verschiedenen Characteren wählen, die jeweils ihre eigenen Fähigkeiten haben. 

Und als zustätzlich Hardware projekt, bauen wir einen Controller mit einem Esp-32 und Joystick controller, den man dann entweder mit Bluetooth oder per kabel als HID-Device verwenden kann.

Zur Umsetzung verwenden wir die LW**JGL** Bibliothek und wir bauen ein Entity-component system und viel Verrerbung wird verwendet.


# Ziele 
- Entity Component System
- Ein "spielbares" Spiel 
- Character mit Fähigkeiten (Skilltree)
- Gegner mit Fähigkeiten 
- Character bekommt beim besiegen der Gegner (Boss) ihre Fähigkeiten
- Fortschritt in JSON datei speichern
- Animationen für Character und Gegner
- Level

<img width="397" alt="image" src="https://user-images.githubusercontent.com/78213692/225554883-04532fe8-b3bc-4577-be70-a8abc9c4a5a8.png">


