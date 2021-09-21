# Uno Card Game

## Description
This is a card game called Uno. The number of players are 2-9. You can decide the number of human players and two different AI. At the beginning, each player will have 7 cards. The initial game state will be deternmined by the first normal card drawn before the start of the game. The code follows the MVC architecture with uno, view, controller package accordingly.

Rules:\
On a player's turn, they have options below to perform:
1. play one card matching the latest card of the discard pile in color, number, or symbol
2. play a Wild card, or a Wild Draw Four card
3. draw the top card from the deck, then play it if possible

The played-out card will be the latest card of the discard pile.\
If all cards are drawn (draw pile is empty), while no player has played all his or her cards, the top card in the discard pile is set aside and the rest of the discard pile is shuffled to create a new deck. Play then proceeds normally.\
The game ends if one player has all of their cards played out.

## Classes in Project
Card:\
Abstract class of card with NormalActionCard, SkipActionCard, ReverseActionCard, DrawTwoActionCard, WildActionCard, WildDrawFourActionCard as its subclass.

Deck:\
Deck of the cards with draw pile and discard pile.

Player:\
Abstract class of player with name, playerId, and hand of cards. It has HumanPlayer, BaselineAI, StrategicAI as its subclass.

GameState:\
Game state including valid color, valid symbol, valid number, current player Id, game order, number of cards cumulated, number of players.

Game:\
This is where the main game loop happens. It can set up the game and have startGame function.

LaunchPage:\
The launch page of the game, which includes the input from users.

GameStagePage:\
The game stage page of the game, which is where player play the game.

EndingScene:\
The game ending scene which displays the winner.

LaunchPageActionListener:\
ActionListener for the game launch page. It is used to detect the number of players input and decide to start the game.

GameStagePageActionListener:\
ActionListener for the game stage page. It is used to detect the event for all the buttons and make actions accordingly.

Test:\
Different test classes that correspond to the previous classes.

## Usage
Run Main class in view package.