# Eind app Native App Studio
### Rijksmuseum app
#### Laura Geerars

Dit is de _final_ app voor het vak _Native App Studio_. Het is een app voor het _Rijksmuseum_. Voor deze app is de API gebruikt van het Rijksmuseum. In de Nederlandse app kunnen gebruikers _inloggen_ of zich _registreren_ met email, wachtwoord en gebruikersnaam. Dit is uitgevoerd door gebruik te maken van _Firebase,_ hier worden deze gegevens opgeslagen en dit maakt het inloggen/uitloggen mogelijk. Wanneer de gebruiker is ingelogd, komt de gebruiker op de _homepage_. Op deze pagina staan er _titels_ van items van de collectie van het Rijksmuseum weergeven. De gebruiker kan hierbij _klikken_ op een titel, waarna een nieuwe pagina verschijnt. 

![alt text](https://github.com/LauraGeerars/LauraGeerarsFinalApp/blob/master/Schermafdruk%202017-12-15%2020.53.01.png)

Deze pagina bevat _extra informatie_ over het geselecteerde item, namelijk _de titel_, een _afbeelding, de maker_ en _een beschrijving_. Op deze pagina kan de gebruiker het item _toevoegen aan favorieten_. Wanneer er op deze button gedrukt wordt, wordt deze titel met bijbehorend objectnummer opgeslagen in Firebase.
De gebruiker kan, wanneer ingelogd, op ieder moment naar zijn/haar _profiel_ gaan door op de _profiel afbeelding_ te klikken. Hier verschijnt een nieuwe pagina, met de gebruiker zijn/haar toegevoegde favorieten. Deze worden opgehaald uit Firebase. Op deze pagina heeft de gebruiker ook de mogelijkheid om de _favorieten van andere gebruikers_ te bekijken, door te klikken op de daarvoor gemaakte button. Ook heeft de gebruiker op deze profielpagina de mogelijkheid om uit te loggen. Wanneer de gebruiker dit doet, zal de login pagina weer verschijnen. 
Wanneer de gebruiker de knop aanklikt _"andere favorieten bekijken"_, verschijnt er een pagina met daarop een lijst met andere gebruikers. Deze gebruikers worden opgehaald uit Firebase. Hierbij kan een gebruiker op een andere gebruiker zijn/haar naam klikken, waarna de gebruiker op een pagina komt waar de _favoriete titels_ weergeven zijn van de _andere geselecteerde gebruiker_. Deze titels worden ook opgehaald uit Firebase. 

![alt text](https://github.com/LauraGeerars/LauraGeerarsFinalApp/blob/master/Schermafdruk%202017-12-15%2020.53.12.png)

De app is telkens gerund op de emulator, Nexus 4 API 22. 

[![BCH compliance](https://bettercodehub.com/edge/badge/LauraGeerars/LauraGeerarsFinalApp?branch=master)](https://bettercodehub.com/)

