# OpenSourceConnect4

Made a Connect4 clone for fun inspired by a Shopify interview. 

Goals:
* Make a light-weight connect4 clone
* Create perfect AI with a minimax algorithm and have it run in reasonable time
* Implement online play

Current:
* Game is light weight, used as little bloat as possible. You can play local multiplayer or single player with ease!
* Perfect AI is somewhat imperfect, as the game progresses the AI responds faster and smarter (because less moves to computer). I still need to implement a hashmap to save common moves or limit depth of move computation early. Alpha beta pruning was successfully implemented despite this and column priority was added.
* No online play :( 
