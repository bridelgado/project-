import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class TestObjectProject extends DrawableObjectProject
{
    private float startX, startY;
	//takes in its position
   public TestObjectProject(float x, float y)
   {
      super(x,y);
      this.startX = 300; // starting X position
      this.startY = 300; // starting Y position
   }
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      gc.setFill(Color.BLACK);
      gc.fillOval(x-14,y-14,27,27);
      gc.setFill(Color.GRAY);
      gc.fillOval(x-13,y-13,25,25);
      gc.setFill(Color.GREEN);
      gc.fillOval(x-6,y-6,10,10);
   }
   
   public double getScore()
   {
      TestObjectProject startPoint = new TestObjectProject(300, 300);
      return distance(startPoint);
   }
   
}