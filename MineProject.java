import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import java.util.Random;
//score is distance from start pos 
public class MineProject extends DrawableObjectProject {
   private Color currentColor;  // current color of the mine
   private long startTime;      //track the time for oscillation

   public MineProject(float x, float y) {
      super(x, y);
   
   }

   // draw the mine
   public void drawMe(float x, float y, GraphicsContext gc) {
      // black outline
      gc.setFill(Color.BLACK);
      gc.fillOval(x - 7, y - 7, 14, 14);
   
      // gray inner oval 
      gc.setFill(Color.GRAY);
      gc.fillOval(x - 6, y - 6, 12, 12);
   
      // oscillating color 
      double oscillation = Math.sin((System.currentTimeMillis() - startTime) / 1000.0 * Math.PI);
      int red = (int) (255 * (0.5 * (oscillation + 1))); // interpolate red
      int green = 1; 
      int blue =1;
   
      Color oscillatingColor = Color.rgb(red, green, blue,1);
      gc.setFill(oscillatingColor);
      gc.fillOval(x - 3, y - 3, 6, 6); //small circle
   }

   public void act(boolean AisPressed, boolean WisPressed, boolean SisPressed, boolean DisPressed) {
   
   }
}
