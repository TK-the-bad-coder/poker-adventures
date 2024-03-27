package com.example.cs102.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.cs102.boss.Boss;
import com.example.cs102.boss.BossDAO;
import com.example.cs102.hand.BossHand;
import com.example.cs102.hand.Hand;
import com.example.cs102.hand.PlayerHand;
import com.example.cs102.player.Player;
import com.example.cs102.player.PlayerDAO;
import com.example.cs102.potion.Potion;
import com.example.cs102.potion.PotionDAO;
import com.example.cs102.poker.BestHandUtility;
import com.example.cs102.poker.Card;
import com.example.cs102.poker.ComboUtility;
import com.example.cs102.poker.BossDmgCalculatorUtility;
import com.example.cs102.poker.Deck;
import com.example.cs102.poker.DeckController;
import com.example.cs102.exceptions.PlayerNotFoundException;
import com.example.cs102.exceptions.DuplicateCardException;
import com.example.cs102.exceptions.InsufficientGoldException;
import com.example.cs102.exceptions.InvalidHandException;


public class GameController {

    // private final GameService service;
    private PlayerDAO playerDAO;
    private BossDAO bossDAO;
    private PotionDAO potionDAO;

    private Player player;
    private Boss boss;

    private GameState gameState;
    private boolean hasFlee = false;

    public GameController() {
        playerDAO = new PlayerDAO();
        bossDAO = new BossDAO();
        potionDAO = new PotionDAO();
    }

    public static boolean checkValidName(String name){
        if (name.isBlank()){
            return false;
        }
        for (int i = 0 ; i < name.length() ; i++){
            if (!(Character.isAlphabetic(name.charAt(i)) || Character.isWhitespace(name.charAt(i)))){
                return false;
            }
        }
        return true;
    }

    public Player login(String name) {
        player = playerDAO.retrieve(name);
        // if the player is not in the database
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        return player;
    }

    // select boss, loops until user selects a valid difficulty
    public Boss selectBoss(int n){
        return bossDAO.retrieve(n);
        }

    public List<Boss> loadBosses() {
        return bossDAO.retrieveBosses();
    }

    public List<Potion> loadPotions() {
        return potionDAO.retrievePotion();
    }

    public Potion selectPotion(int n){
        return potionDAO.retrieve(n);
    }
    // shop
    public void purchasePotion(int hp, int gold){
        
        if(player.getGold() < gold){
            throw new InsufficientGoldException("Not enough credit");

        }
        player.setHp(player.getHp() + hp);
        player.setGold(player.getGold() - gold);
        System.out.println("");
        System.out.println("Thank you for purchasing with us");
        System.out.println("Your new stats are " + player.toString());
        System.out.println("");
        playerDAO.savePlayer(player.getName(), player.getHp(), player.getGold());
    }

    public void increaseGold() {
        // reward for player
        player.setGold(player.getGold() + boss.getGold());

        System.out.println("Your new stats are " + player.toString());
        playerDAO.saveAfterBattle(player.getName(), player.getGold());
    }

    // loading the player and boss into the controller
    public void initPlayer(Player player) {
        this.player = player;
    }

    public void initBoss(Boss boss) {
        this.boss = boss;
    }

    public Boss getBoss() {
        return boss;
    }

    public Player getPlayer() {
        return player;
    }

    public Player makeNewPlayer(String name) {
        this.playerDAO.addPlayer(name);
        return login(name);
    }

    public List<Card> bossMove() {
        return BestHandUtility.getBestHand(boss.getCards());
    }

    public int playTurn(String comboMove) {
        return ComboUtility.getDamageValue(comboMove);
    }

    

    public void checkMove(int[] input) {
        int handSize = input.length;
        if (handSize != 1 && handSize != 2 && handSize != 5) {
            throw new InvalidHandException("Please enter a valid hand length!");
        }
        if (Arrays.stream(input).distinct().toArray().length != handSize) {
            throw new DuplicateCardException("Hand contains duplicate choices! Please ensure all numbers are unique.");
        }
        if (Arrays.stream(input).filter(num -> num < 0 || num > 9).toArray().length > 0) {
            throw new IllegalArgumentException(
                    "Your input contains an invalid number! Please key in numbers only from 0 to 9");
        }
    }

    public void startGame() {
        List<Card> cards = new ArrayList<>();
        DeckController deckControl = new DeckController(cards);
        cards = deckControl.initCards();

        Deck playerDeck = new Deck(new ArrayList<>(cards));
        Deck bossDeck = new Deck(new ArrayList<>(cards));

        player.setHand(new PlayerHand(playerDeck));
        boss.setHand(new BossHand(bossDeck));
        gameState = new GameState(player, boss);
        hasFlee = false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void playCombo(List<Card> selectedCards) {
        String combo = ComboUtility.getHandValue(selectedCards);
        int damage = ComboUtility.getDamageValue(combo);
        gameState.doDamageTo(boss, damage);

        PlayerHand playerHand = player.getHand();
        playerHand.discard(selectedCards);
        playerHand.addToHand();
    }

    public void bossMove(List<Card> selectedCards) {
        String combo = BossDmgCalculatorUtility.getHandValue(selectedCards);
        int damage = BossDmgCalculatorUtility.bossDamageCalculation(combo, boss);
        gameState.doDamageTo(player, damage);

        Hand bossHand = boss.getHand();
        bossHand.discard(selectedCards);
        bossHand.addToHand();
    }

    public List<Card> playerMove(int[] input) {
        List<Card> currentHand = player.getCards();
        List<Card> cardSelection = new ArrayList<>();
        // getting the cards selected
        for (int number : input) {
            cardSelection.add(currentHand.get(number));
        }
        return cardSelection;
    }

    public void flee() {
        hasFlee = true;
    }

    public boolean hasFled() {
        return hasFlee;
    }
}
