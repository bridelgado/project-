import javafx.scene.paint.*;
import javafx.scene.canvas.*;
//score is distance from start pos 
public abstract class DrawableObjectProject
{
 
   public DrawableObjectProject(float x, float y)
   {
      this.x = x;
      this.y = y;
   }

   //positions of object 
   private float x;
   private float y;
   //forces 
   private float xForce=0;//spped
   private float yForce=0;//speed
   


   //takes the position of the player and calls draw me with appropriate positions
   public void draw(float playerx, float playery, GraphicsContext gc, boolean isPlayer)
   {
      //the 300,300 places the player at 300,300, if you want to change it you will have to modify it here
      if(isPlayer)
       //  drawMe(playerx,playery,gc);
       drawMe(300,300,gc);//makes it stay in center of screen
      else
         drawMe(-playerx+300+x,-playery+300+y,gc);
   }
   
   //this method you implement for each object you want to draw. Act as if the thing you want to draw is at x,y.
   //NOTE: DO NOT CALL DRAWME YOURSELF. Let the the "draw" method do it for you. I take care of the math in that method for a reason.
   public abstract void drawMe(float x, float y, GraphicsContext gc);
   public void act(boolean AisPressed,boolean WisPressed,boolean SisPressed, boolean DisPressed  )
   {
     //for max speed control to max 5 or -5
      if (DisPressed){
         xForce += 0.1;//right force 
      }
      if (AisPressed){
         xForce -= 0.1;// left
      }
      if (WisPressed){
         yForce -= 0.1;//up 
      }
      if (SisPressed){
         yForce += 0.1;//down force 
      }
      
      
      if (xForce >5){ 
         xForce=5;
      }
      else if (xForce < -5){
         xForce = -5;
      }
      if (yForce < -5){
         yForce = -5;
      }
      else if (yForce > 5){
         yForce=5;
      }
   
     //update the pos
      x+= xForce;
      y+= yForce;
     
     //declerate
      if (xForce > 0.25 && !AisPressed && !DisPressed) {
         xForce -= 0.025f;
      } else if (xForce < -0.25 && !AisPressed && !DisPressed) {
         xForce += 0.025f;
      } else if (!AisPressed && !DisPressed) {
         xForce = 0;
      }
   
      if (yForce > 0.25 && !WisPressed && !SisPressed) {
         yForce -= 0.025f;
      } else if (yForce < -0.25 && !WisPressed && !SisPressed) {
         yForce += 0.025f;
      } else if (!WisPressed && !SisPressed) {
         yForce = 0;
      }
   
   }
   
   //gets distance to other darwble objects
   public double distance(DrawableObjectProject other)
   {
      return (Math.sqrt((other.x-x)*(other.x-x) +  (other.y-y)*(other.y-y) ));
   }

   //gets x and y forces
   public float getX(){ 
      return x; 
   }
   public float getY(){ 
      return y; 
   }
   
   public void setX(float x){//to set xforce 
      this.x = x; 
   }
   public void setY(float y){//to set yforce 
      this.y = y;
   }
   
   
   
}