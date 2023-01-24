
## Software Development Method project
# DOTS AND BOXES
[![CircleCI](https://circleci.com/gh/MatteoBille/ProgettoSDM/tree/main.svg?style=shield)](https://circleci.com/gh/MatteoBille/ProgettoSDM/tree/main)
## Il progetto 
Il progetto implementa il gioco Dots and Boxes ed è stato sviluppato in Java, utilizzando le librerie JavaFx per la parte grafica. 
![Schermata di gioco](https://drive.google.com/uc?export=view&id=1_lcqxsi9YwKfzNn0qOVW1CvgHa9XZMHx)
## Le regole del gioco
Si gioca su un tabellone di N x M celle(boxes) e ogni cella è delimitata da 4 punti (dots).
Ad ogni turno un giocatore deve "tracciare" una linea fra due punti addiacenti; quando un giocatore traccia una linea e circonda completamente una cella, allora questa cella diventa di sua proprietà e il giocatore fa un punto.
| Casella vuota | Casella chiusa |
|--|--|
| ![Casella vuota](https://drive.google.com/uc?export=view&id=1JMl55O1-YctA859QewU0XvH6svzq7bP3) |![Casella chiusa](https://drive.google.com/uc?export=view&id=1JMarRl_G33QY4L3A245eyH3a2inePD4M)  |



I turni dei giocatori sono alternati una mossa a testa, tranne nel caso un giocatore riesca a chiudere una casella. In questo caso il turno rimane al giocatore che ha fatto il punto
L'obiettivo del gioco è quello di conquistare più celle dell'avversario.
## Download the game
- Fare il clone della repository sul proprio computer
- Assicurarsi di avere gradle installato sul pc
- Da terminale posizionarsi nella cartella del progetto attraverso il comando `cd path` e digitare `gradlew run`

- In alternativa il gioco è eseguibile una volta aperto l'IDE attraverso il task run di Gradle oppure anche dalla MainApplication presente all'interno del package graphicsclass
