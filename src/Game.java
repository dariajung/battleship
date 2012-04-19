/**
 * The game interface - this will control the Battleship game. 
 * It will keep track of 2 versions of the "board" - one for each player.
 * It will let players take turns.
 * It will announce hits, misses, and ships sunk (by calling the appropriate methods in the Player interface/class).
 * @author swapneel
 *
 */
public interface Game {
	
	int SIZE = 10;
	
	int CARRIER = 5;
	int BATTLESHIP = 4;
	int SUBMARINE = 3;
	int CRUISER = 3;
	int DESTROYER = 2;
	
	/**
	 * This method will initialize the game. 
	 * At the end of this method, the board has been set up and the game can be started
	 * @param p1 Player 1
	 * @param p2 Player 2
	 */
	void initialize(Player p1, Player p2);
	
	/**
	 * This is the start point of playing the game. 
	 * The game will alternate between the players letting them take shots at the other team.
	 * @return Player who won
	 */
	Player playGame();
}
