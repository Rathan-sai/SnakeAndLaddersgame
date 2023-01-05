package com.example.demo4;

import javafx.util.Pair;

import java.util.ArrayList;

public class Board {

    //we created references to ArrayList which one contains the positioncoordiantes, and other contains the integer of snakes and ladder positions to transilate.
    private ArrayList<Pair<Integer, Integer>> positionCoordinates;
    private ArrayList<Integer> snakeLadderPosition;

    //initialized the arraylist int the Board class.
    public Board(){
        populatePositionCoordinates();
        populateSnakeLadderPosition();
    }

    //populated the positionscooridinates of the board in the grid and added it to the array with pair.
    private void populatePositionCoordinates(){
        positionCoordinates = new ArrayList<>();
        positionCoordinates.add(new Pair<Integer, Integer>(0, 0));
        int x, y = 10, xPos, yPos;
        for(int i = 0; i < SnakeAndLadder.height; i++)
        {
            x = 1;
            for(int j = 0; j < SnakeAndLadder.width; j++)
            {
                if(y % 2 == 0)
                {
                    xPos = x*SnakeAndLadder.tileSize - SnakeAndLadder.tileSize/2;
                }
                else{
                    xPos = SnakeAndLadder.width*SnakeAndLadder.tileSize - (x*SnakeAndLadder.tileSize - SnakeAndLadder.tileSize/2);
                }
                yPos = y*SnakeAndLadder.tileSize - SnakeAndLadder.tileSize/2;
                positionCoordinates.add(new Pair<Integer, Integer>(xPos, yPos));
                x++;
            }
            y--;
        }
    }

    //using the positioncoordinates and changed the values to the new values for the obstacles for snake and ladders.
    private void populateSnakeLadderPosition(){
        snakeLadderPosition = new ArrayList<>();
        for(int i = 0; i < 101; i++){
            snakeLadderPosition.add(i);
        }

        //this set value are the places for the snakes and ladders and denotes coin next position.
        snakeLadderPosition.set(1, 23);
        snakeLadderPosition.set(4, 14);
        snakeLadderPosition.set(9, 31);
        snakeLadderPosition.set(17, 7);
        snakeLadderPosition.set(21, 42);
        snakeLadderPosition.set(28, 84);
        snakeLadderPosition.set(51, 67);
        snakeLadderPosition.set(54, 34);
        snakeLadderPosition.set(62, 18);
        snakeLadderPosition.set(64, 60);
        snakeLadderPosition.set(72, 91);
        snakeLadderPosition.set(80, 99);
        snakeLadderPosition.set(87, 36);
        snakeLadderPosition.set(93, 73);
        snakeLadderPosition.set(95, 75);
        snakeLadderPosition.set(98, 79);
    }

    //getXcoordinate denotes the x coordinate of the coin and getYcoordinate denotes the y coordinate fo the coins.
    public int getXCoordinate(int position)
    {
        return positionCoordinates.get(position).getKey();
    }

    public int getYCoordinate(int position)
    {
        return positionCoordinates.get(position).getValue();
    }

    //getNextPosition check whether the coins are in between the grids 1 to 100.
    public int getNextPosition(int position){
        if(position>=1 && position<=100)
            return snakeLadderPosition.get(position);
        else return -1;
    }

//    public static void main(String[] args) {
//        Board board = new Board();
//        board.populatePositionCoordinates();
//        for(int i=0;i<board.positionCoordinates.size(); i++){
//            System.out.println(i + " # x: " + board.positionCoordinates.get(i).getKey() + " y: "+ board.positionCoordinates.get(i).getValue());
//        }
//    }
}
