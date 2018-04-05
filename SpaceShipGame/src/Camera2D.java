// ************ //
// CAMERA CLASS //
// ************ //
public class Camera2D {

	// ******************* //
	// MY STATIC VARIABLES //
	// ******************* //
	static double x;
	static double y;
	
	// ************************************* //
	// STATIC ALLOWS ME TO ACCESS THE METHOD //
	//     ANYWHERE I WANT IN THE PROJECT    //
	// ************************************* //
	public static void moveUpBy(int dy){
		y -= dy;
	}
	
	public static void moveDownBy(int dy){
		y += dy;
	}
	public static void moveLeftBy(int dy){
		x -= dy;
	}
	public static void moveRightBy(int dy){
		x += dy;
	}
}