// Arraylist, Random, and Scanner
import java.util.*;
// UI elements
import javax.swing.*; //NOTE - UI not present in test class

/**
 * This is a prototype version of the Blackjack object class, contains test engine
 * 
 * @author Aidan Dittmar, a.dittmar@icloud.com
 * @version v1.0
 */
public class Blackjack {
    
    /**
     * Private Object Class for Game Cards
     */
    private class Card {
        String value;
        String suit;

        /**
         * Constructor for Card object - can only be constructed with params
         * @param value - String representing numeric value of the card
         * @param suit - String representing the suit of the card (C D H S)
         */
        Card(String value, String suit) {
            this.value = value;
            this.suit = suit;
        }

        /**
         * Returns a string representation of the card by displaying its value and suit
         * @return - example is '2D' for Two of Diamonds
         */
        public String toString() {
            return value + suit;
        }

        /**
         * Gets the value of the card and returns it as an integer
         * @return - returns int value of card
         */
        public int getValue() {
            
            //Checks if card is a face card or ace
            if ("AJQK".contains(value)) {
                
                //Returns 11 for aces
                if (value == "A") {
                    return 11;
                }
                
                //Returns 10 for all other face cards
                return 10;
            }
            
            //Parses integer from all non face cards
            return Integer.parseInt(value);
        }

        /**
         * Boolean that checks to see if a card is an ace
         * @return - true if ace, false if not
         */
        public boolean isAce() {
            return value == "A";
        }

    }

    //Global arraylist for the card deck
    ArrayList<Card> deck;
    //Global random object used in the shuffleDeck method
    Random random = new Random();

    //Dealer Variables
    Card hiddenCard;
    Card emptyCard = new Card("0", "Null");
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    //Player Variables
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    /**
     * Builds a deck of cards by passing two arrays into a for loop and iterating through their items
     */
    public void buildDeck() {
        deck = new ArrayList<Card>();

        //Array of strings representing all possible card values
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        //Array of strings representing suits
        String[] suits = {"C", "D", "H", "S"};

        //Repeats loop for each suit
        for (int x = 0; x < suits.length; x++) {
            //Adds a card of each value of the current suit to the ArrayList
            for (int y = 0; y < values.length; y++) {
                Card card = new Card(values[y], suits[x]);
                deck.add(card);
            }
        }

        System.out.println("\nBuilt Deck:");
        System.out.println(deck);

    }

    /**
     * Shuffles the deck of cards using a for loop to iterate through the entire deck
     */
    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            
            //gets a random integer between 0 and 51
            int j = random.nextInt(deck.size());
            
            //Current card at current index, random card assigned to j
            Card currentCard = deck.get(i);
            Card randomCard = deck.get(j);
            
            //Swaps the positions of the randomly selected card and the card at the current index
            deck.set(i, randomCard);
            deck.set(j, currentCard);
        }

        System.out.println("\nShuffled Deck:");
        System.out.println(deck);
    }

    /**
     * Returns all cards to the deck and shuffles it
     */
    public void gameReset() {
        Card card = hiddenCard;
        deck.add(card);
        hiddenCard = emptyCard;
        for (int i = 0; i < dealerHand.size() + 1; i++) {
            card = dealerHand.remove(dealerHand.size() - 1);
            deck.add(card);
        }
        dealerSum = 0;
        dealerAceCount = 0;

        for (int i = 0; i < playerHand.size() + 1; i++) {
            card = playerHand.remove(playerHand.size() - 1);
            deck.add(card);
        }
        playerSum = 0;
        playerAceCount = 0;

        System.out.println("\nAll cards returned to the deck:");
        System.out.println(deck);
        shuffleDeck();
    }

    /**
     * Initiates a game by calling other methods in sequential order 
     * !TEST VERSION!
     */
    public void startTest() {
        
        //First, builds the card deck
        buildDeck();
        
        //Shuffles the deck
        shuffleDeck();

        //Initializes dealer variables
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        //Sets the dealers hidden card to be the last card in the deck
        hiddenCard = deck.remove(deck.size() - 1);
        dealerSum += hiddenCard.getValue();
        if (hiddenCard.isAce()) dealerAceCount += 1;
        //Hidden card not added to hand at this time, still counts toward sum

        //Repeats previous process with visible card
        Card card = deck.remove(deck.size() - 1);
        dealerSum += card.getValue();
        if (card.isAce()) dealerAceCount += 1;
        dealerHand.add(card);

        System.out.println("\nDealer's Hand:");
        System.out.println("Hidden Card - " + hiddenCard);
        System.out.println("Visible Card - " + dealerHand);
        System.out.println("Total - " + dealerSum);
        System.out.println("Ace Count - " + dealerAceCount);

        //Initializes player variables
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        //Adds two cards to the player's hand
        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            playerSum += card.getValue();
            if (card.isAce()) playerAceCount += 1;
            playerHand.add(card);
        }

        System.out.println("\nPlayer's Hand:");
        System.out.println("Cards - " + playerHand);
        System.out.println("Total - " + playerSum);
        System.out.println("Ace Count - " + playerAceCount);

        System.out.println("\nSelect one of the options from the menu below:\n~ (1) Add a card to the dealer's hand\n~ (2) Add a card to the player's hand"
        + "\n~ (3) Add hidden card to dealer's hand\n~ (4) Shuffle the deck\n~ (5) Return all cards to the deck\n~ (6) Exit the program");
        Scanner testKey = new Scanner(System.in);
        boolean testAgain = true;
        do {
            int testInput = 0;
            System.out.println("Enter a number 1-6:\n");
            String testTemp = testKey.nextLine();

            boolean isLetter = true;
            do {
                try {
                    testInput = Integer.parseInt(testTemp);
                    isLetter = false;
                }
                catch(NumberFormatException e) {
                    // Immediately stops to tell the user to input a string instead of an integer
                    System.out.println("Enter a number 1-6:\n");
                    testTemp = testKey.nextLine();
                }
            } while (isLetter);

            switch(testInput) {
                case 1:
                    card = deck.remove(deck.size() - 1);
                    dealerSum += card.getValue();
                    if (card.isAce()) dealerAceCount += 1;
                    dealerHand.add(card);

                    System.out.println("\nDealer's Hand:");
                    System.out.println("Hidden Card - " + hiddenCard);
                    System.out.println("Visible Card - " + dealerHand);
                    System.out.println("Total - " + dealerSum);
                    System.out.println("Ace Count - " + dealerAceCount);
                    break;
                
                case 2:
                    card = deck.remove(deck.size() - 1);
                    playerSum += card.getValue();
                    if (card.isAce()) playerAceCount += 1;
                    playerHand.add(card);

                    System.out.println("\nPlayer's Hand:");
                    System.out.println("Cards - " + playerHand);
                    System.out.println("Total - " + playerSum);
                    System.out.println("Ace Count - " + playerAceCount);
                    break;

                case 3:
                    if (hiddenCard == emptyCard) {
                        hiddenCard = deck.remove(deck.size() - 1);
                        dealerSum += hiddenCard.getValue();
                        if (hiddenCard.isAce()) dealerAceCount += 1;

                        System.out.println("\nDealer's Hand:");
                        System.out.println("Hidden Card - " + hiddenCard);
                        System.out.println("Visible Card - " + dealerHand);
                        System.out.println("Total - " + dealerSum);
                        System.out.println("Ace Count - " + dealerAceCount);

                    } else {
                        System.out.println("\nDealer already has a hidden card");
                    }
                    break;

                case 4:
                    shuffleDeck();
                    break;

                case 5:
                    gameReset();
                    break;

                case 6:
                    testAgain = false;
                    System.out.println("Program terminated. Have a nice day!");
                    break;
            }
        } while (testAgain);
        testKey.close();
    }

    // The block of code below is responsible for initiating a game of Blackjack when a Blackjack object is created
    Blackjack() {
        startTest();
    }
    
}
