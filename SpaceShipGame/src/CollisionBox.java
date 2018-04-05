import java.awt.Color;
import java.awt.Graphics;

// Class that creates a collision Box for any element I want
public class CollisionBox {
	double  x;
	double y;
	int width;
	int height;

	public CollisionBox(double x2, double y2, int w, int h) {
		this.x = x2;
		this.y = y2;

		this.width = w;
		this.height = h;
	}

	public boolean contains(double mx, double my) {
		return ((mx < x+width) && (mx > x) && (my > y) && (my < y+height));
	}

	public boolean hasCollidedWith(CollisionBox r) {
		return ((r.x <= x+width) && (r.x+r.width >= x) && (r.y+r.height >= y) && (r.y <= y+height));
	}

	public void moveBy(double dx, double dy) {
		x += dx;
		y += dy;
	}

	public void moveLeftBy(double dx) {
		x -= dx;
	}

	public void moveRightBy(double dx) {
		x += dx;
	}

	public void moveUpBy(double dy) {
		y -= dy;
	}

	public void moveDownBy(double dy) {
		y += dy;
	}

	public void draw(Graphics g) {	
		g.setColor(Color.green);
		g.drawRect((int)x,(int) y, width, height);

	}
}