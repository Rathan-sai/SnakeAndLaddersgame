package com.example.demo4;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

//Created a rectangle tile for each box in the board and used to move the coins.
public class Tile extends Rectangle {
    public Tile (int size){
        setWidth(size);
        setHeight(size);
        setFill(Color.YELLOW);
        setStroke(Color.BLACK);
    }
//    Circle tile(){
//
//    }
}
