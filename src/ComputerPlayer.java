import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class ComputerPlayer. For the time being, the Computer is really stupid.
 */
public class ComputerPlayer implements Player {

	private int shipSize;
	private int xTarget, yTarget, randomRow, randomCol;
	private boolean error, horizontal, hit, sunk, lastHit, lastSunk;
	private int[][] computerBoard;
	private ComputerLocation ship, compAim;
	private int SIZE = 10;
	private ArrayList<ComputerLocation> movesMade;

	public ComputerPlayer() {
		computerBoard = new int[SIZE][SIZE];
		movesMade = new ArrayList<ComputerLocation>();

	}

	/**
	 * This method will place a ship on the grid. This method should guarantee
	 * correctness of location (no overlaps, no ships over the edge of the
	 * board, etc.)
	 * 
	 * @param size
	 *            the size of the ship to place
	 * @param retry
	 *            if an earlier call to this method returned an invalid
	 *            position, this method will be called again with retry set to
	 *            true.
	 * @return The Location of the ship
	 */
	public Location placeShip(int size, boolean retry)
			throws ArrayIndexOutOfBoundsException, InputMismatchException {

		shipSize = size;

		randomRow = (int) (Math.random() * (SIZE - 2));
		randomCol = (int) (Math.random() * (SIZE - 2));
		Random random = new Random();
		horizontal = random.nextBoolean();

		// System.out.println("RandomRow & RandomCol: " + randomRow + " "
		// + randomCol);

		if (randomRow < 0 || randomCol < 0
				|| (horizontal && randomRow + shipSize > 10)
				|| (!horizontal && randomCol + shipSize > 10))
			return placeShip(size, true);

		ship = new ComputerLocation(randomRow, randomCol, horizontal, shipSize);

		if (isValid() == false) {
			return placeShip(size, true);
		}
		setComputerBoard();

		return ship;

	}

	/**
	 * This method will get the new target to aim for.
	 * 
	 * @return The Location of the target
	 */
	public Location getTarget() throws ArrayIndexOutOfBoundsException,
	InputMismatchException {
		xTarget = (int) (Math.random() * (SIZE));
		yTarget = (int) (Math.random() * (SIZE));

		compAim = new ComputerLocation(xTarget, yTarget, false, 0);
		// System.out.println("compAim x: " + compAim.getX());
		// System.out.println("compAim y: " + compAim.getY());

		for (ComputerLocation c : movesMade) {
			// System.out.println("movesMade c's x: " + c.getX());
			// System.out.println("movesMade c's y: " + c.getY());
			if (c.getX() == compAim.getX() && c.getY() == compAim.getY()) {
				// System.out.println("Already shot there. Not valid" + "\n");
				// return getTarget();
				// infinite loop fix!
			}
		}

		movesMade.add(compAim);
		return compAim;
	}

	public void setComputerBoard() {
		int boardIter = shipSize;
		if (shipSize == 1) {
			boardIter = 3;
		}

		// System.out.println("BoardIter:" + boardIter);
		if (horizontal == false) {
			int yCoor = ship.getY();
			while (boardIter > 0) {
				computerBoard[ship.getX()][yCoor] = shipSize;
				boardIter--;
				yCoor++;
			}
		}

		if (horizontal == true) {
			int xCoor = ship.getX();
			while (boardIter > 0) {
				computerBoard[xCoor][ship.getY()] = shipSize;
				boardIter--;
				xCoor++;
			}
		}
		for (int col = 0; col < 10; col++) {
			for (int r = 0; r < 10; r++) {
				if (computerBoard[r][col] != 0) {
					computerBoard[r][col] = computerBoard[r][col];
				} else {
					computerBoard[r][col] = 0;
				}

				// System.out.print(computerBoard[r][col] + " ");
			}
			// System.out.println();
		}

	}

	/**
	 * Gets the computer board.
	 * 
	 * @return the computer board
	 */
	public int[][] getComputerBoard() {
		return computerBoard;
	}

	/**
	 * Prints the computer board.
	 */
	public void printComputerBoard() {
		System.out.println("Computer board: " + "\n");
		for (int col = 0; col < 10; col++) {
			for (int r = 0; r < 10; r++) {
				System.out.print(computerBoard[r][col] + " ");
			}
			System.out.println();
		}
		System.out.println();

	}

	/**
	 * This method will notify the Player of the result of the previous shot.
	 * 
	 * @param hit
	 *            true, if it was a hit; false otherwise
	 * @param sunk
	 *            true, if a ship is sunk; false otherwise
	 */
	public void setResult(boolean hit, boolean sunk) {
		this.hit = hit;
		this.sunk = sunk;

		if (hit == true) {
			System.out
			.println("The computer's missile has hit an opposing ship.");
		}

		if (hit == false && sunk == false) {
			System.out.println("The computer's missile missed.");
		}

		if (hit == true && sunk == true) {
			System.out.println("The computer sunk an enemy ship!");
		}
	}
	
	public boolean lastHit(boolean h, boolean s){
		lastHit = h;
		lastSunk = s;
		
		boolean use = false;
		
		if(lastHit == true && lastSunk == false){
			use = true;
		}
		
		return use;
	}

	/**
	 * Checks if is valid.
	 * 
	 * @return true, if is valid
	 */
	public boolean isValid() {

		boolean okay = true;
		// System.out.println("Horizontal? : " + horizontal);
		int checkIter = shipSize;
		if (shipSize == 1) {
			checkIter = 3;
		}
		// System.out.println("checkIter: " + checkIter);

		if (horizontal == false) {
			int yCoor = ship.getY();
			// System.out.println("Checkiter2 " + checkIter);
			while (checkIter > 0) {
				// System.out.println(computerBoard[ship.getX()][yCoor]);
				if (computerBoard[ship.getX()][yCoor] > 0) {
					okay = false;
					// System.out.println("Okay?: " + okay);
					return okay;
				}
				checkIter--;
				yCoor++;
			}
		}

		if (horizontal == true) {
			int xCoor = ship.getX();
			// System.out.println("xCoor: " + xCoor);
			// System.out.println("Checkiter2 " + checkIter);
			// System.out.println("Okay?1: " + okay);
			while (checkIter > 0) {
				if (computerBoard[xCoor][ship.getY()] > 0) {
					okay = false;
					// System.out.println("Okay?: " + okay);
					return okay;
				}
				checkIter--;
				xCoor++;
			}
		}

		else
			okay = true;
		// System.out.println("Valid? : " + okay);
		return okay;

	}

}
