# klondike Solitaire
Klondike Solitaire

Java based program (batch application) that simulates klondike solitaire.
- arraylist, collections, lists, stringtokenizer, linkedhashset, linkedlist, queue, filereader

User Input (via console)
1. User option to shuffle cards (Y/N)
2. Number of talon cards (1 or 3 cards to get at stock pile at a time/per turn).

Input Text File is given with default sequence and given format.
D-K,D-Q,D-J,D-10,D-9,D-8,D-7,D-6,D-5,D-4,D-3,D-2,D-A,H-K,H-Q,H-J,H-10,H-9,H-8,H-7,H-6,H-5,H-4,H-3,H-2,H-A,S-K,S-Q,S-J,S-10,S-9,S-8,S-7,S-6,S-5,S-4,S-3,S-2,S-A,C-K,C-Q,C-J,C-10,C-9,C-8,C-7,C-6,C-5,C-4,C-3,C-2,C-A

![image](https://github.com/user-attachments/assets/3625ea6b-4cef-46d8-ac50-c1bc6d8c38ce)

No line of codes to validate the input text fie/deck of cards. assumption is that it is unique and follows format.

=================================================
Classic Solitaire: Klondike Solitaire

Aim:
The aim of Klondike Solitaire is to build ascending suit sequences, in the foundation zone.

How to Play:

The opening tableau has:
•	7 manoeuvre stacks, of increasing length 
•	4 foundation stacks, and 
•	the talon. 

In the manoeuvre zone, form descending sequences of alternate color. Multiple cards forming a run can be moved at once.

At any time, flip cards from the talon. Where possible, move these cards into the manoeuvre zone, or directly into the foundation zone. If, during the course of play, a manoeuvre stack becomes empty, only a King can be moved onto it.

You may cycle through the talon an unlimited number of times.

Classic Solitaire comes with two variations on Klondike Solitaire: Draw One and Draw Three. This refers to how many cards are flipped from the talon at a time.

=================================================
Sample Input/Output Messages:

Validation:
1. shuffle option No, with talon cards 1 or 3 will result in game ended as won.
   
![Console_input](https://github.com/user-attachments/assets/299d707e-8eff-4f54-84c1-cba4f79c91b5)

![Console_output_1](https://github.com/user-attachments/assets/b740ef2b-64ee-46fe-b803-fe534c80fb12)

![Console_output_2](https://github.com/user-attachments/assets/7ef728cc-6c7b-4f3f-a8f7-16b5ce061b1f)

3. Note:  Shuffle option Yes, is so random and will result a lot of combinations and may have long running time.
Sample of game option shuffle Y, talon card = 3 (pre-terminated)

![Console_output_ShuffleY_Talon3](https://github.com/user-attachments/assets/8d535a0b-e338-4ea1-a495-bf8c4ed1f656)

