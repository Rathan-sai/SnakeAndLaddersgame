package com.example.demo4;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Player {

    //created a circle coin which indicated the moving positions of the player in the board
    private Circle coin;

    //defines a name, coinposition to the player
    private String name;
    private int coinPosition;

    //used gameBoard as Board reference to transilate the player.
    private static Board gameBoard = new Board();
//    PauseTransition pauseTransition = new PauseTransition(Duration.millis(200));

    //created a constructor to create a player with parameters or details as tilesize, coincolor, playername.
    public Player(int tileSize, Color coinColor, String playerName){
        coinPosition=1;
        name = playerName;
        coin = new Circle(tileSize/2);
        coin.setFill(coinColor);
        coin.setTranslateX(-20);
        coin.setTranslateY(380);
//        coin.setFill(img);
    }

    //playerwon function message won when the player travels all the board and reach 100 tile.
    public String playerWon(){
        if(coinPosition == 100)
        {
            return name + " won the game..!";
        }
        return null;
    }

    //moveplayer is used to transilate the player from one position to another position with the value of dice.
    public void movePlayer(int diceValue){
        if(coinPosition + diceValue <= 100){
            coinPosition += diceValue;

            //translateTransition is a animation class defined below
            TranslateTransition move1 = transilatePlayer();
            move1.play();

            //new position is helps to checking whether there is ladder or snake on the way.
            //if there is any ladder or snake then it transition value changes as per the ladder top position or snake tail position.
            int newPosition = gameBoard.getNextPosition(coinPosition);
            if(newPosition != coinPosition){
                coinPosition = newPosition;
                TranslateTransition move2 = transilatePlayer();

                //The sequentialtransition is used to do two transition and a gap of seconds.
                SequentialTransition sq = new SequentialTransition(move1, new PauseTransition(Duration.millis(5)), move2);
                sq.play();
//                move2.play();
            }
        }
    }

    //TranslateTransition is a animation class which is used to transilate the coin position from one position to the another position like a moving coil.
    private TranslateTransition transilatePlayer(){

        TranslateTransition move = new TranslateTransition(Duration.millis(500), this.coin);
        move.setToX(gameBoard.getXCoordinate(coinPosition));
        move.setToY(gameBoard.getYCoordinate(coinPosition));
        //the setautoreverse is a method that retrieve the same position where it initially started.
        move.setAutoReverse(false);
//        move.play();
        return move;
    }

    //placed the getter and the setter methods.
    public int getCoinPosition() {
        return coinPosition;
    }

    //this getcoin returns the coin position.
    public Circle getCoin() {
        return coin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoinPosition(int coinPosition) {
        this.coinPosition = coinPosition;
    }

}
