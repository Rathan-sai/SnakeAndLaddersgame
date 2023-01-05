package com.example.demo4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class SnakeAndLadder extends Application {

    //Initially started some final size of tilesize for the board with no.of board with height and width.
    public static final int tileSize = 40, height = 10, width = 10;

    //placed a lowerLine until where the board has to occupied.
    int lowerLine = tileSize*height;

    //dicevalue indicated the value of dice when the player rolls.
    public int diceValue;

    //rolledDiceValueLabel is a normal label on grid indicates a name of player with some changes.
    Label rolledDiceValueLabel;

    //whosturn label is used to indicate how has to roll the dice.
    Label whosTurn;

    //A global startbutton used to start the game.
    Button startbutton;

    //gameboard is a reference of the Board class which helps in placing players coins.
    private static Board gameboard = new Board();

    //Here using the Player class we defined two player firstPlayer and secondPlayer and named them.
    Player firstPlayer = new Player(tileSize-5, Color.BLUE, "Naruto");
    Player secondPlayer = new Player(tileSize-10, Color.RED, "Luffy");

    //Initial conditions for the players to access.
    boolean firstPlayer_turn = true, secondPlayer_turn = false, game_started = false;

    //bodypane used for to display images UI controls buttons and labels.
    Pane bodypane = new Pane();

    //createcontent is the pane or stage we add the scenes or our designed to it.
    public Pane CreateContent()
    {
        //create a root as pane reference and added a size to the pane.
        Pane root = new Pane();
        root.setPrefSize(width*tileSize, height*tileSize+130);

        //preferred some size to the bodypane
        bodypane.setPrefSize(width*tileSize, tileSize*height);

        //used Image class to place some pictures to the players in the board.
        Image firstPlayerPic = new Image("https://th.bing.com/th/id/OIP.stRt2QzoHdf4bFCvpWSfiAHaHa?pid=ImgDet&w=207&h=207&c=7");
        ImagePattern imgpic1 = new ImagePattern(firstPlayerPic);
        firstPlayer.getCoin().setFill(imgpic1);

        Image secondPlayerPic = new Image("https://th.bing.com/th/id/OIP.8SOezPH4v3VtmbOX9ee-mgHaEo?pid=ImgDet&rs=1");
        ImagePattern imgpic2 = new ImagePattern(secondPlayerPic);
        secondPlayer.getCoin().setFill(imgpic2);

        //used circle images to the coins which are defined to the where the player moves in the board.
//        bodypane.getChildren().addAll(startbutton, boardImage, firstPlayer.getCoin(), secondPlayer.getCoin(), rolledDiceValueLabel);
        bodypane.getChildren().addAll(gameBoard());
        Circle playerRectangle1 = new Circle(tileSize*2, tileSize*height+80, tileSize);
        Image playerImage1 = new Image("https://i.pinimg.com/originals/2a/cf/a9/2acfa974fc6a69a560882c8675834da8.png");
        ImagePattern img1 = new ImagePattern(playerImage1);
        playerRectangle1.setFill(img1);
        playerRectangle1.setStyle("-fx-background-color:#000000");

        Circle playerRectangle2 = new Circle(8*tileSize,tileSize*height+80, tileSize);
        Image playerImage2 = new Image("https://worstgen.alwaysdata.net/forum/data/avatars/o/0/834.jpg?1578516167");
        ImagePattern img2 = new ImagePattern(playerImage2);
        playerRectangle2.setFill(img2);
        playerRectangle2.setStyle("-fx-background-color:#000000");;

        //added all the content to the root to display our design in stage.
        root.getChildren().addAll(bodypane, players());
        root.getChildren().addAll(playerRectangle1, playerRectangle2);
        return root;
    }


    public Pane gameBoard(){
//        GridPane gridPane = new GridPane();
//        gridPane.setPrefSize(width*tileSize, height*tileSize);
        Pane gridPane = new Pane();
        gridPane.setPrefSize(width*tileSize, height*tileSize);

        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(i * tileSize);
                tile.setTranslateY(j * tileSize);
                gridPane.getChildren().add(tile);
            }
        }

        Image image = new Image("https://i.pinimg.com/originals/40/dc/01/40dc0137e12227698bad49469cdaa75b.jpg");
        ImageView boardImage = new ImageView();
        boardImage.setImage(image);
        boardImage.setFitHeight(height*tileSize);
        boardImage.setFitWidth(width*tileSize);

        gridPane.getChildren().addAll(boardImage, firstPlayer.getCoin(), secondPlayer.getCoin());

        return gridPane;
    }

    //created a pane to store the pictures of player buttons and and rolleddicelabel dice value.
    public Pane players(){

        //used buttons for the two players, mention their color, and size of the each button background color.
        Button playerOneButton = new Button("Uzumaki Naruto");
        Button playerTwoButton = new Button("Mokey.D Luffy");
        playerOneButton.setTextFill(Color.WHITE);
        playerTwoButton.setTextFill(Color.WHITE);
        playerOneButton.setTranslateX(tileSize-10);
        playerOneButton.setTranslateY(10);
        playerOneButton.setStyle("-fx-background-color:#FFA500");

        //added an action to the two players as clicked to dice rolled and coin moves according to the value of dice.
        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //As per the initial conditions we get how to game runs.
                //if game_started and firstPlayer_turn is ture the playerone will be able to roll dice
                //As we designed when playerone rolls the dice the button will disable and player to will be seen.
                if(game_started)
                {
                    if(firstPlayer_turn)
                    {
                        playerOneButton.setDisable(true);
                        playerTwoButton.setDisable(false);
                        whosTurn.setText("Luffy turn!");
                        setDiceValue();
                        firstPlayer.movePlayer(diceValue);
                        if(firstPlayer.playerWon() != null) {
                            rolledDiceValueLabel.setText(firstPlayer.playerWon());
                            firstPlayer_turn = false;
                            secondPlayer_turn = true;
                            game_started = false;
                            startbutton.setDisable(false);
                            startbutton.setText("Start again...!");
                            whosTurn.setText("Hokage");
                        }
                        firstPlayer_turn = false;
                        secondPlayer_turn = true;
                    }
                }
            }
        });
        playerTwoButton.setTranslateY(10);
        playerTwoButton.setTranslateX(7*tileSize-10);
        playerTwoButton.setStyle("-fx-background-color:#FF0000");
        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //As per the initial conditions we get how to game runs.
                //if game_started and secondPlayer_turn is ture the playerone will be able to roll dice
                //As we designed when secondplayer rolls the dice the its button will disable and playerone to will be seen.
                if(game_started)
                {
                    if(secondPlayer_turn)
                    {
                        playerOneButton.setDisable(false);
                        playerTwoButton.setDisable(true);
                        whosTurn.setText("Naruto turn!");
                        setDiceValue();
                        secondPlayer.movePlayer(diceValue);
                        if(secondPlayer.playerWon() != null) {
                            rolledDiceValueLabel.setText(secondPlayer.playerWon());
                            firstPlayer_turn = true;
                            secondPlayer_turn = false;
                            game_started = false;
                            startbutton.setDisable(false);
                            startbutton.setText("Play again.");
                            whosTurn.setText("King of Pirates");
                        }
                        firstPlayer_turn = true;
                        secondPlayer_turn = false;
                    }
                }
            }
        });

        //this start button used used to start the game we set an action to it
        //when it clicked it displays the rolleddicelabel as roll dice with first player name.
        startbutton = new Button("Start");
        startbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startbutton.setDisable(true);
                game_started = true;
                startbutton.setText("game 0n");
                rolledDiceValueLabel.setText("Roll Dice Naruto..!");
                firstPlayer.setCoinPosition(-1);
                secondPlayer.setCoinPosition(-1);
//                firstPlayer.setCoinPosition();
            }
        });

        //we set some position to the start button a color and size.
        startbutton.setTranslateX(170);
        startbutton.setTranslateY(65);
        startbutton.setTextFill(Color.BLACK);
        startbutton.setStyle("-fx-background-color:#008000");

        rolledDiceValueLabel = new Label("Start the Game");
        rolledDiceValueLabel.setTranslateY(35);
        rolledDiceValueLabel.setTranslateX(150);
        rolledDiceValueLabel.setFont(new Font("Arial", 15));
        rolledDiceValueLabel.setTextFill(Color.YELLOW);

        whosTurn = new Label();
        whosTurn.setTranslateX(150);
        whosTurn.setTranslateY(20);
        whosTurn.setFont(new Font("Arial", 15));
        whosTurn.setTextFill(Color.YELLOW);

        //grid pane used to store all this funtion UI and content returns to the stage or root.
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(tileSize*width,130);
        gridPane.setTranslateY(tileSize*height);
        gridPane.setStyle("-fx-background-color:#000000");

        gridPane.getChildren().addAll(playerOneButton, playerTwoButton, startbutton, rolledDiceValueLabel, whosTurn);
//        gridPane.getChildren().addAll(playerOneButton);
        return gridPane;
    }

    //the setDiceValue is used to generate random value between 1 and 6.
    private void setDiceValue(){
        diceValue = (int)(Math.random()*6 + 1);
        rolledDiceValueLabel.setText("Dice Value:" + diceValue);
    }

    //stage
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(SnakeAndLadder.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(CreateContent());
        stage.setTitle("Snake And Ladder!");
        stage.setScene(scene);
        stage.getIcons().add(new Image("https://image9.zibster.com/8429/7_20181208090823_10660355_large.png"));
        stage.show();
    }

    //This main function launch's our stage with scenes we designed.
    public static void main(String[] args) {
        launch();
    }
}