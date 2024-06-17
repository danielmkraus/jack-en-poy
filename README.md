# Jack en poy

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=danielmkraus_jack-en-poy&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=danielmkraus_jack-en-poy)
[![Java CI with Maven](https://github.com/danielmkraus/jack-en-poy/actions/workflows/maven.yml/badge.svg)](https://github.com/danielmkraus/jack-en-poy/actions/workflows/maven.yml)

## Description

Jack en poy or rock paper scissors (also known by other orderings of the three items, with "rock" sometimes being
called "stone", roshambo or ro-sham-bo) is a hand game usually played between two people, in which each player
simultaneously forms one of three shapes with an outstretched hand. These shapes are "rock" (a closed fist), "paper" (a
flat hand), and "scissors" (a fist with the index finger and middle finger extended, forming a V). "Scissors" is
identical to the two-fingered V sign (also indicating "victory" or "peace") except that it is pointed horizontally
instead of being held upright in the air.               
A simultaneous, zero-sum game, it has only two possible outcomes: a draw, or a win for one player and a loss for the
other. A player who decides to play rock will beat another player who has chosen scissors ("rock crushes scissors" or
sometimes "blunts scissors"), but will lose to one who has played paper ("paper covers rock"); a play of paper will lose
to a play of scissors ("scissors cuts paper"). If both players choose the same shape, the game is tied and is usually
immediately replayed to break the tie. The type of game originated in China and spread with increased contact with East
Asia, while developing different variants in signs over time.

- source [Wikipedia](https://en.wikipedia.org/wiki/Rock_paper_scissors)

### Project business requirements

- Users can trigger matches played by two automated agents, first will always play rock, and the second will play
  randomly
- Users can see matches played by him/her
- Users can reset matches played by him/her
- Users can check the total score of matches played by all users in score view

## Technical requirements

- Java 17 +

## Running the application

enter inside the project folder with a shell and execute the following command

```
./mvnw clean -U compile exec:java -Dexec.mainClass="io.github.danielmkraus.jackenpoy.Server"
```

After run this command, you should be able to open the [main page](http://127.0.0.1:8080/webapp)

## TO-DO

- Change the CDN strategy to packaging for the web interface part (add package.json and manage dependencies there).
- Implement swagger / openapi documentation for the Rest endpoints
