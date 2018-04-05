import java.awt.*;
import java.util.*;
import Audio.AudioPlayer;

// **************** //
// CONTROLLER CLASS //
//**************** //
public class Controller 
{
	// ******************************************* //
	// LINKED LIST OF EVERY CLASS THAT WILL BE USE //
	// ******************************************* // 
	public LinkedList<Bullet> bulletForPlane = new LinkedList<Bullet>();
	public LinkedList<Bullet> bulletForSmallShip = new LinkedList<Bullet>();
	public LinkedList<Asteroid> asteroidArray = new LinkedList<Asteroid>();
	public LinkedList<SmallShip> smallShipArray = new LinkedList<SmallShip>();
	public LinkedList<BigShip> BigShipArray = new LinkedList<BigShip>();

	// **************************** //
	// ARRAY FOR MY EXPLOSION CLASS //
	// **************************** //
	public static ArrayList<Explosion> explosion; 

	// ************************** //
	// OBJECTS FROM OTHER CLASSES //
	// ************************** //
	Game       game;
	Ship       plane;
	Sprite     sprite;
	BigShip    bigShip;
	SmallShip  smallShip;
	Asteroid   Templeasteroid;
	Bullet     TemplebulletForPlane;
	Bullet 	   TemplebulletForSmallShip;

	// ********************************** //
	// OBJECTS FROM THE AUDIOPLAYER CLASS //
	// ********************************** //
	private AudioPlayer explode;
	private AudioPlayer bigExplode;
	private AudioPlayer softExplode;
	private AudioPlayer gameover;

	// ********************** //
	// VARIABLE BIGSHIP SCORE //
	// ********************** //
	private int bigShipScore = 200;

	// **************************** //
	// TIMERS TO CONTROL MY BIGSHIP //
	// **************************** //
	private int timer1       = 50;
	private int timer2       = 45;

	// ******************************************* //
	//  CLOCK VARIABLES TO CONTROL TO CONTROL THE  //
	// THE TIMING OF WHEN MY OBSTACLES ARE DISPLAY //
	// ******************************************* //
	private int clockForAsteroid    	     = 1000;
	private int clockForBigShipToAnimate     = 150;
	private int clockForBigShipToStartItsAI  = 20000;

	// ************************ //
	// IMAGE OBJECT FOR LEVEL 2 //
	// ************************ //
	Image level2;

	// ************************ //
	// CONSTRUCTOR OF THE CLASS //
	// ************************ //
	public Controller(Ship ship){
		this.plane   = ship;
		explosion    = new ArrayList<Explosion>();
		explode      = new AudioPlayer("/Music/explode.wav");
		gameover     = new AudioPlayer("/Music/gameover.wav");
		bigExplode   = new AudioPlayer("/Music/bigExplode.wav");
		softExplode  = new AudioPlayer("/Music/softexplosion.wav");
		level2       = Toolkit.getDefaultToolkit().getImage("Level2.png");
	}

	// ****************************************** //
	// METHOD THAT CREATES EVERYTHING AND ADDS IT //
	// ****************************************** //
	public void generatesRandomEverything(int amount){

		if(clockForAsteroid > 0){
			// *********************** //
			// GENERATES THE ASTEROIDS //
			// *********************** //
			if(asteroidArray.isEmpty()){
				int asteroidsAmount = new Random().nextInt(20) + 10;
				for(int i = 0; i < asteroidsAmount; i++) {
					Asteroid asteroid = new Asteroid(randomPositionForX(), amount - 100);
					asteroid.setVelY(random());
					addAsteroid(asteroid);
				}
			}
		}

		if(clockForAsteroid < 0 && clockForBigShipToStartItsAI > 0){
			// *************************** //
			// GENERATES THE ENEMIES SHIPS //
			// *************************** //
			if (smallShipArray.isEmpty()) {
				int smallShipAmount = 740;
				for (int i = 0; i < smallShipAmount; i+= 30) {
					SmallShip smallShipObject = new SmallShip(i, 50);
					smallShipObject.setVelY(1);
					addEnemyShip(smallShipObject);

					// ****************************** //
					// SMALLSHIP SHOOTING AT THE USER //
					// ****************************** //
					TemplebulletForSmallShip = new Bullet(smallShipObject.getX(), smallShipObject.getY());
					addBullet(TemplebulletForSmallShip);
				}
			}
		}

		if(clockForBigShipToStartItsAI < 0){
			// ******************** //
			// GENERATE THE BIGSHIP //
			// ******************** //
			if (BigShipArray.isEmpty()) {
				for (int i = 0; i < 1; i++) {
					BigShip bigship = new BigShip(315, -250);
					addEnemyBigShip(bigship);
				}
			}	
		}
	}

	// ***************************************** //
	// GENERATE BULLETS AND ASTEROID AND BIGSHIP //
	//   GENERATES ALL ARTIFICIAL INTELLIGECE    //
	//		    CKECKS FOR ALL COLLISION  	     //
	// ***************************************** //
	public void tick(){

		// *************************************************** //
		// CLOCKS THAT DECIDES WHEN ALL MY OBSTACLES COMES OUT //
		// *************************************************** //
		clockForAsteroid--;
		clockForBigShipToStartItsAI--;

		// ************************************ //
		// LOOPS THROUGH ALL THE EXPLOSION ANIM //
		// ************************************ //
		for(int i = 0; i < explosion.size(); i++){
			boolean remove = explosion.get(i).update();
			if(remove){
				explosion.remove(i);
			}
		}

		// ************************************************ //
		// LOOPS THROUGH ALL THE BULLETS FOE THE SMALLSHIPS //
		// ************************************************ //
		for(int i = 0; i < bulletForSmallShip.size(); i++){
			TemplebulletForSmallShip = bulletForSmallShip.get(i);
			TemplebulletForSmallShip.setVelY(-2);
			TemplebulletForSmallShip.tick();

			// ******************************************************** //
			// REMOVES BULLETS WHEN ITS Y-POSITION ITS GREATER THAN 750 //
			// ******************************************************** //
			if(TemplebulletForSmallShip.getY() > 750) {
				removeBullet(TemplebulletForSmallShip);
			}
		}
		
		
		// ***************************** //
		// LOOPS THROUGH ALL THE BULLETS //
		// ***************************** //
		for(int i = 0; i < bulletForPlane.size(); i++){
			TemplebulletForPlane = bulletForPlane.get(i);
			TemplebulletForPlane.setVelY(40);
			TemplebulletForPlane.tick();

			// ***************************************************** //
			// REMOVES BULLETS WHEN ITS Y-POSITION ITS LETS THAN -5 //
			// ***************************************************** //
			if(TemplebulletForPlane.getY() < -5){
				removeBullet(TemplebulletForPlane);
			}
		}

		// ******************************* //
		// LOOPS THROUGH ALL THE ASTEROIDS //
		// ******************************* //
		for(int i = 0; i < asteroidArray.size(); i++){
			Templeasteroid = asteroidArray.get(i);
			Templeasteroid.tick();

			// ********************************************************* //
			// REMOVES ASTEROIDS WHEN ITS X-POSITION IS GREATER THAN 740 //
			// ********************************************************* //
			if(Templeasteroid.getY() > 740) {
				removeAsteroid(Templeasteroid);
			}
		}

		// **************************************** //
		// ARIFICIAL INTELLIGENCE FOR THE ASTEROIDS //
		// **************************************** //
// ********************************************************************************************************************************************************************************************* //
		for(int i = 0; i < asteroidArray.size(); i++) {

			// ************************************ //
			// VARIABLE THAT GETS ALL THE ASTEROIDS //
			// ************************************ //
			Templeasteroid = asteroidArray.get(i);
			Templeasteroid.tick();

			// ***************************************** //
			// COLLISION DETECTION FOR ASTEROIDS & PLANE //
			// ***************************************** //
			if(Templeasteroid.collisionBox.hasCollidedWith(plane.collisionBox)) {

				// ******************* //
				// EXPLOSION ANIMATION //
				// ******************* //
				explosion.add(new Explosion(Templeasteroid.x + 30, Templeasteroid.y + 30, 25, 50));

				// **************** //
				// HEALTH DECREASED //
				// **************** //
				Game.health -= 5;

				// **************************** //
				// IF PLAYER DIES THIS HAPPENDS //
				// **************************** //
				if(Game.health <= 0) {

					// ************************************ //
					// CHANGING THE GAME STATE TO GAME OVER //
					// ************************************ //
					Game.State = Game.STATE.END;
					gameover.play();

					// **************************** //
					// RESETING THE PLANES POSITION //
					// **************************** //
					plane.x = Game.WIDTH/2 - 38;
					plane.y = Game.HEIGHT - 100;

					// ******************* //
					// RESETING THE CLOCKS //
					// ******************* //
					clockForAsteroid   		    = 1000;
					clockForBigShipToAnimate    = 150;
					clockForBigShipToStartItsAI = 2000;
				}

				// ************************************ //
				// REMOVES ASTEROIDS THAT HIT THE PLANE //
				// ************************************ //
				removeAsteroid(asteroidArray.get(i));
			}

			// ***************************************** //
			// COLLISION DETETION FOR ASTEROIDS & BULLET //
			// ***************************************** //
			for(int k = 0; k < bulletForPlane.size(); k ++){
				TemplebulletForPlane = bulletForPlane.get(k); 
				if(TemplebulletForPlane.collisionBox.hasCollidedWith(Templeasteroid.collisionBox)){
					Game.score += 1;
					explosion.add(new Explosion(Templeasteroid.x + 30, Templeasteroid.y + 30, 25, 50));
					softExplode.play();
					removeAsteroid(Templeasteroid);
					removeBullet(TemplebulletForPlane);
				}
			}
		}
// ********************************************************************************************************************************************************************************************* //
		// **************************************** //
		// ARIFICIAL INTELLIGENCE FOR THE SMALLSHIP //
		// **************************************** //
// ********************************************************************************************************************************************************************************************* //
		for (int i = 0; i < smallShipArray.size(); i++) {
			smallShip = smallShipArray.get(i);
			smallShip.enemyMoveDown();

			/*
			smallShip.setVelY(random());

			if(smallShip.getX() < plane.getX()) {
				smallShip.enemyMoveRight();
			}

			else if(smallShip.getX() > plane.getX()) {
				smallShip.enemyMoveLeft();
			}

			else if(smallShip.getX() == plane.getX()) {
				smallShip.enemyMoveDown();
			}
			*/
			// *********************************************** //
			// COLLISION DETECTION FOR THE ENEMY SHIPS & PLANE //
			// *********************************************** //
			if (smallShip.collisionBox.hasCollidedWith(plane.collisionBox)) {
				Game.health -= 2;
				removeEnemyShip(smallShip);
			}
			
			if(smallShip.getY() > 740){
				removeEnemyShip(smallShip);
			}
			
			// ****************************************************************** //
			// COLLISION DETECTION FOR THE BULLETS OF THE SMALLSHIPS & SMALLAHIPS //
			// ****************************************************************** //
			for (int k = 0; k < bulletForSmallShip.size(); k++) {
				TemplebulletForSmallShip = bulletForSmallShip.get(k);
				if (TemplebulletForSmallShip.collisionBox.hasCollidedWith(smallShip.collisionBox)) {
						   // DO NOTHING //
					// JUST MAKE THEM MOVE DOWN //
				}
			}
			
			// ****************************************************** //
			// COLLISION DETECTION FOR THE PLANE BULLETS & SMALLAHIPS //
			// ****************************************************** //
			for (int k = 0; k < bulletForPlane.size(); k++) {
				TemplebulletForPlane = bulletForPlane.get(k);
				if (TemplebulletForPlane.collisionBox.hasCollidedWith(smallShip.collisionBox)) {
					Game.score += 1;
					explosion.add(new Explosion(smallShip.x + 30, smallShip.y + 30, 25, 50));
					removeBullet(TemplebulletForPlane);
					removeEnemyShip(smallShip);
					explode.play();
					
					// ************** //
					// SOME DEBUGGING //
					// ************** //
					if(TemplebulletForPlane == TemplebulletForSmallShip) {
						System.out.println("They are both equal");
					}
					else {
						System.out.println("No the bullets are different");
					}
				}
			}
		}
		
		
		
// ********************************************************************************************************************************************************************************************* //
		// ************************************** //
		// ARIFICIAL INTELLIGENCE FOR THE BIGSHIP //
		// ************************************** //
// ********************************************************************************************************************************************************************************************* //
		// ********************************************************** //
		// A TIMER FOR ME TO KNOW WHEN THE BIG SHIP COMES TO THE GAME //
		// ********************************************************** //
		if(clockForBigShipToStartItsAI < 0){
			for (int i = 0; i < BigShipArray.size(); i++) {

				// ********************************************************** //
				// SETTING A TIMER FOR THE BIGSHIP TO MAKE IT STOP GOING DOWN //
				// ********************************************************** //
				if(timer1 <= 0) bigShip.velY = 0;
				else timer1--;

				// ************************************************************************ //
				// TELL THE SECOND TIMER (THE ONE THAT MAKES IT MOVE SIDE TO SIDE) TO START //
				// ************************************************************************ //
				if(timer1 <= 0) timer2--;
				
				// *************************************** //
				// MAKES THE BIGSHIP COME OUT FROM NOWHERE //
				// *************************************** //
				clockForBigShipToAnimate--;
				if(clockForBigShipToAnimate > 0) {
					bigShip.velX = 0;
					bigShip.velY = 2;
				}

				// *********************************** //
				// MAKES THE BIGSHIP MOVE SIDE TO SIDE //
				// *********************************** //
				bigShip = BigShipArray.get(i);
				bigShip.x += bigShip.velX;
				bigShip.collisionBox.moveRightBy(bigShip.velX);
				bigShip.y += bigShip.velY;
				bigShip.collisionBox.moveDownBy(bigShip.velY);

				// *************************************************************************** //
				// BIGSHIP WATCH OUT YOUR BOUNDARIES BUT KEEP MOVING SIDE TO SIDE WITH A SPEED //
				// *************************************************************************** //
				if(timer2 <= 0){
					if(bigShip.velX == 0)  bigShip.velX = 4;
					if(bigShip.x > 665) bigShip.velX = -4;
					if(bigShip.x < 0) bigShip.velX = 4;
				}
			}

			// ********************************************* //
			// COLLISION DETECTION FOR THE BIG SHIPS & PLANE //
			// ********************************************* //
			for (int i = 0; i < BigShipArray.size(); i++) {
				if (bigShip.collisionBox.hasCollidedWith(plane.collisionBox)) {
					// ********************************************************************************** //
					// THIS SHOULD NEVER HAPPEND BECAUSE THE PLANE DOESNT HAVE THE ABILITY TO GO UP THERE //
					// ********************************************************************************** //
				}

				// *************************************************** //
				// COLLISION DETECTION OF THE BULLETS AND THE BIGSHIIP //
				// *************************************************** //			
				for (int k = 0; k < bulletForPlane.size(); k++) {
					TemplebulletForPlane = bulletForPlane.get(k);
					if (TemplebulletForPlane.collisionBox.hasCollidedWith(bigShip.collisionBox)) {
						removeBullet(TemplebulletForPlane);
						Game.score += 10;
						bigShipScore -= 4;
						bigExplode.play();
						explosion.add(new Explosion(bigShip.x + 75, bigShip.y + 75, 50, 100));
						if(bigShipScore <= 0 ){
							removeEnemyBigShip(bigShip);
							for(int man = 1; man <= 100; man++) {
								explosion.add(new Explosion(bigShip.x + 75, bigShip.y + 75, 35, 80));
							}
						}
					}
				}
			}
		}
	}

	// ***************************************** //
	// ADDS BIGSHIP FROM THE BIGSHIP LINKED LIST //
	// ***************************************** //
	public void addEnemyBigShip(BigShip bigShip) {
		BigShipArray.add(bigShip);
	}

	// *************************** // 
	// REMOVES BIGSHIP FROM MEMORY //
	// *************************** //
	public void removeEnemyBigShip(BigShip bigShip) {
		BigShipArray.remove(bigShip);
	}

	// ********************************************* //
	// ADDS SMALLSHIP FROM THE SMALLSHIP LINKED LIST //
	// ********************************************* //
	public void addEnemyShip(SmallShip enemyShip) {
		smallShipArray.add(enemyShip);
	}

	// ***************************** //
	// REMOVES SMALLSHIP FROM MEMORY //
	// ***************************** //
	public void removeEnemyShip(SmallShip enemyShip) {
		smallShipArray.remove(enemyShip);
	}

	// *************************************** //
	// ADDS BULLET FROM THE BULLET LINKED LIST //
	// *************************************** //
	public void addBullet(Bullet shot) {
		bulletForPlane.add(shot);
	}

	// **************************** //
	// REMOVES A BULLET FROM MEMORY //
	// **************************** //
	public void removeBullet(Bullet shot) {
		bulletForPlane.remove(shot);
	}

	// ******************************************************** //
	// ADDS BULLET OF THE SMALLSHIP FROM THE BULLET LINKED LIST //
	// ******************************************************** //
	public void addBulletForSmallShip(Bullet shot) {
		bulletForSmallShip.add(shot);
	}

	// ********************************************** //
	// REMOVES A BULLET OF THE SMALL SHIP FROM MEMORY //
	// ********************************************** //
	public void removeBulletForSmallShip(Bullet shot) {
		bulletForSmallShip.remove(shot);
	}

	// ********************************************** //
	// ADDS AN ASTEROID FROM THE ASTEROID LINKED LIST //
	// ********************************************** //
	public void addAsteroid(Asteroid shot) {
		asteroidArray.add(shot);
	}

	// ******************************* //
	// REMOVES AN ASTEROID FROM MEMORY //
	// ******************************* //
	public void removeAsteroid(Asteroid shot) {
		asteroidArray.remove(shot);
	}

	// *************************************************** //
	// RANDOM METHOD THAT RETURNS A RANDOM # BETWEEN 1 & 1 //
	// *************************************************** //
	public double random() {
		return Math.random() * 1 + 1;
	}

	// **************************************************** //
	// RANDOM METHOD THAT RETURNS A NUMBER FOR A X-POSITION //
	// **************************************************** //
	public double randomPositionForX() {
		return (int) (Math.random() * 720 + 30);
	}

	// ************************************************************ //
	// DRAWS BULLETS, ASTEROIDS, SMALLSHIPS & BIGSHIP TO THE SCREEN //
	// ************************************************************ //
	public void draw(Graphics g){
		// ***************************************** //
		// GOES THROUGH ALL THE BULLETS AND DRAWS IT //
		// ***************************************** //
		for(int i = 0; i < bulletForPlane.size(); i++){
			TemplebulletForPlane = bulletForPlane.get(i);
			TemplebulletForPlane.draw(g);
		}

		// ************************************************************ //
		// GOES THROUGH ALL THE BULLETS FOR THE SMALL SHIP AND DRAWS IT //
		// ************************************************************ //
		for(int i = 0; i < bulletForSmallShip.size(); i++){
			TemplebulletForSmallShip = bulletForSmallShip.get(i);
			TemplebulletForSmallShip.draw(g);
		}

		// ******************************************* //
		// GOES THROUGH ALL THE ASTEROIDS AND DRAWS IT //
		// ******************************************* //
		for(int i = 0; i < asteroidArray.size(); i++){
			Templeasteroid = asteroidArray.get(i);
			Templeasteroid.draw(g);
		}

		// ******************************************** //
		// GOES THROUGH ALL THE SMALLSHIPS AND DRAWS IT //
		// ******************************************** //
		for (int i = 0; i < smallShipArray.size(); i++) {
			smallShip = smallShipArray.get(i);
			smallShip.draw(g);
		}

		// *************************************************** //
		// GOES THROUGH ALL THE EXPLOSIONS ANIM AND DRAWS THEM //
		// *************************************************** //
		for(int i = 0; i < explosion.size(); i++){
			explosion.get(i).draw(g);
		}

		// ************************************* //
		// GOES THROUGH THE BIGSHIP AND DRAWS IT //
		// ************************************* //
		for (int i = 0; i < BigShipArray.size(); i++) {
			bigShip = BigShipArray.get(i);
			bigShip.draw(g);


			// **************************** //
			// DRAWING THE HEADS UP DISPLAY //
			// **************************** //
			if(bigShipScore > 0) {
				Font fnt1 = new Font("Arial", Font.BOLD, 15);
				g.setColor(Color.green);
				g.setFont(fnt1);
				g.drawString("Boss Score", 710, 20);
				g.setColor(Color.red);
				g.fillRect(750, 25, 10, 200);
				g.setColor(Color.yellow);
				g.fillRect(750, 25, 10, bigShipScore);
			}

			// *********************************************************** //
			// REMOVES THE BIGSHIP FROM THE GAME WHEN SCORE ITS SCORE IS 0 //
			// *********************************************************** //
			if(bigShipScore <= 0) {
				g.drawImage(level2, 150, 200, null);

			}
		}
	}
}