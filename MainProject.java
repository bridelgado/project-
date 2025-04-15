import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;
//main 
//must consider spacing of program for grid space
//want to keep track what grid space your in so uou can make mines as you move 
//
//AH extends AnimationTimer
//player at center at all times and everything else is relatove to player

public class MainProject extends Application
{
   FlowPane fp;
   Canvas theCanvas = new Canvas(600,600);
   AnimationHandler ah;
   TestObjectProject thePlayer= new TestObjectProject(300,300);//player object
   GraphicsContext gc;
   boolean AisPressed = false;//booleans for movement input
   boolean WisPressed = false;
   boolean SisPressed = false;
   boolean DisPressed = false;
   
   private int lastPlayerGridX = -1; //track the players last pos
   private int lastPlayerGridY = -1;
   
   private Random random = new Random();
   private int minesNum = 1; // num of mines for grid
   ArrayList<MineProject> mines = new ArrayList<>(); // list to hold the mines
   
   private int score = 0; // current game score
   private int highScore = 0;

  // highScore = 0; // high score

   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();

   public static void main(String[] args)
   {
      
      launch(args);
     /* try{
      FileOutputStream fos = new FileOutputStream ("mines.txt", false);
      PrintWriter pw = new PrintWriter(fos);
      if(highScore>score){
          highScore=score; 
      } 
      if (score==null){
         score=0;
      }
      pw.print(highScore);
      pw.close();
      }catch(FileNotFoundException fnfe){
      
      }*/
      
   }

   public void start(Stage stage)
   {
      fp = new FlowPane();
      fp.getChildren().add(theCanvas);
      gc = theCanvas.getGraphicsContext2D();
      drawBackground(300,300,gc); //background 
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
   
      // handlers
      theCanvas.setOnKeyPressed(new KeyListenerDown());
      theCanvas.setOnKeyReleased(new KeyListenerUp());
   
      ah = new AnimationHandler();
      ah.start(); //start animation 
   
      theCanvas.requestFocus(); // must
   }

   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
      //re-scale player position to make the background move slower
      playerx *= .1;
      playery *= .1;
   
      // tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      int xi = (int) x;
      int yi = (int) y;
   
      for(int i=xi-3;i<xi+4;i++) {
         for(int j=yi-3;j<yi+4;j++) {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
   
      playerx *= 2f;
      playery *= 2f;
      x = (playerx) / 400;
      y = (playery) / 400;
      xi = (int) x;
      yi = (int) y;
   
      for(int i=xi-3;i<xi+4;i++) {
         for(int j=yi-3;j<yi+4;j++) {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   public class AnimationHandler extends AnimationTimer
   {
      private float startX = 300;
      private float startY = 300;
   
      public void handle(long currentTimeInNanoSeconds)
      {
         gc.clearRect(0,0,600,600); //clear canvas
      
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc); //background
         thePlayer.draw(thePlayer.getX(), thePlayer.getY(),gc,true); //make player at middle
      
         thePlayer.act( AisPressed, WisPressed, SisPressed, DisPressed ); //udate the playes pos
      
         createMinesAroundPlayer(); // create new mines around the player
      
         //check for collisions with mines
         for (int i = 0; i < mines.size(); i++) {
            MineProject mine = mines.get(i);
            mine.draw(thePlayer.getX(), thePlayer.getY(), gc, false); // draw each mine
            mine.act(false,false,false,false); // update  mine
         
            // check distance between player and mine 
            if (thePlayer.distance(mine) < 20) {
               restartGame(); //restart the game if a collide
               break; // exit loop after a collision
            }
         }
      
         //score title
         score = (int) thePlayer.getScore();
         gc.setFill(Color.WHITE);
         gc.fillText("High Score: " + highScore, 10, 20); 
         gc.fillText("Score: " + score, 10, 40); 
      }
   }

   // down lisenter
   public class KeyListenerDown implements EventHandler<KeyEvent>
   {
      public void handle(KeyEvent event)
      {
         if (event.getCode() == KeyCode.W) {
            WisPressed=true;
         }
         if (event.getCode() == KeyCode.A) {
            AisPressed=true;
         }
         if (event.getCode() == KeyCode.S) {
            SisPressed=true;
         }
         if (event.getCode() == KeyCode.D) {
            DisPressed=true;
         }
      }
   }

   // UP listenter
   public class KeyListenerUp implements EventHandler<KeyEvent>
   {
      public void handle(KeyEvent event)
      {
         if (event.getCode() == KeyCode.A) {
            AisPressed=false;
         }
         if (event.getCode() == KeyCode.W) {
            WisPressed=false;
         }
         if (event.getCode() == KeyCode.D) {
            DisPressed=false;
         }
         if (event.getCode() == KeyCode.S) {
            SisPressed=false;
         }
      }
   }

   //creates mines around the player as they move
   private void createMinesAroundPlayer() {
      //calc the current grid position of the player
      int cgridx = (int)(thePlayer.getX() / 100);
      int cgridy = (int)(thePlayer.getY() / 100);
   
      //check if player has moved to a new grid
      if (cgridx != lastPlayerGridX || cgridy != lastPlayerGridY) {
         // updates the last grid position
         lastPlayerGridX = cgridx;
         lastPlayerGridY = cgridy;
      
         // add mines in the surrounding grids 
         for (int i = -6; i <= 6; i++) {
            for (int j = -6; j <= 6; j++) {
               int distance = Math.abs(i) + Math.abs(j);
               if (distance >= 6 && distance <= 6) { // Create mines around player
                  int gridX = (cgridx + i) * 100;
                  int gridY = (cgridy + j) * 100;
               
                  // create up to minesNum mines in this grid with a chance
                  for (int k = 0; k < minesNum; k++) {
                     if (Math.random() < 0.3) { // probality
                        MineProject newMine = new MineProject(gridX + (int)(Math.random() * 100), gridY + (int)(Math.random() * 100));
                        mines.add(newMine); // add new mine to the list
                     //     System.out.println("Mine at: " + gridX + ", " + gridY); // debug
                     }
                  }
               }
            }
         }
      }
   }

   //restarts the game it will reset player pos+clear mine + update high score 
   private void restartGame() {
      // update score if the current score is higher
      if (score > highScore) {
         highScore = score;
        // System.out.println("High Score: " + highScore);
      }
      thePlayer.setX(300); // reset player's X position
      thePlayer.setY(300); // reset player's Y position
      mines.clear(); // removes all  mines
      lastPlayerGridX = -1; //resets last grid pos
      lastPlayerGridY = -1;
      score = 0; //resets current score
   }
}
