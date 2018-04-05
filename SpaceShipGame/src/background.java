import java.awt.*;

// **************** //
// BACKGROUND CLASS //
//**************** //
public class background {
	
	// ****************** //
	// DECLARED VARIABLES //
	// ****************** //
	int x;
	int y;
	int yPosition = 740;
	int xPosition = 800;
	
	// *********************** //
	// AN OBJECT OF TYPE IMAGE //
	// *********************** //
	Image bg;
	
	// ************************ //
	// CONSTRUCTOR OF THE CLASS //
	// ************************ //
	public background(int x, int y, String filename) {
		this.x = x;
		this.y = y;
		
		// ********************************************************************** //
		// GETS THE IMAGE OF THE A BACKGROUND FROM FILE THAT FINISHES WITH ".PNG" //
		// ********************************************************************** //
		bg = Toolkit.getDefaultToolkit().getImage(filename + ".png");
	}
	
	// ********************************************* //
	// DRAWS AND DISPLAYS GRAPHICS OF THE BACKGROUND //
	// ********************************************* //
	public void draw(Graphics g) {
		g.drawImage(bg, x, y, xPosition, yPosition, null);
		
	}
}