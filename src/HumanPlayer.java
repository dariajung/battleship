import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The Class HumanPlayer.
 */
public class HumanPlayer implements Player {

	private int shipSize, xPlace, yPlace, xTarget, yTarget;
	private boolean redo, horizontal;
	private HumanLocation ship, topLeftCoordinate, humanAim;
	private ArrayList<HumanLocation> setOfCoordinates, movesMade;
	private int[][] humanBoard;
	private int SIZE = 10;

	public HumanPlayer() {
		humanBoard = new int[SIZE][SIZE];
		setOfCoordinates = new ArrayList<HumanLocation>();
		movesMade = new ArrayList<HumanLocation>();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Player#placeShip(int, boolean)
	 */
	@Override
	public Location placeShip(int size, boolean retry)
			throws ArrayIndexOutOfBoundsException, InputMismatchException {
		// TODO Auto-generated method stub

		shipSize = size;
		redo = retry;

		Scanner in = new Scanner(System.in);
		String input = null;

		if (redo)
			System.out
					.println("Error with input. "
							+ "You either went off the grid or you tried to place ships in the same location."
							+ " Please try again." + "\n");

		System.out
				.println("Do you want to place the ship vertically or horizontally? "
						+ "Type v for vertical, h for horizontal: ");

		// System.out.println(input);

		if (in.hasNext()) {
			input = in.next();

			if (input.equals("v")) {
				horizontal = false;
			}

			else if (input.equals("h")) {
				horizontal = true;
			}
		}

		System.out.println("Please enter a number (0 - 9) "
				+ "to represent the X coordinate of your ship: ");
		xPlace = in.nextInt();

		System.out.println("Please enter a number (0 - 9) "
				+ "to represent the Y coordinate of your ship: ");
		yPlace = in.nextInt();

		if (xPlace < 0 || xPlace > 9 || yPlace < 0 || yPlace > 9
				|| (horizontal && xPlace + shipSize > 10)
				|| (!horizontal && yPlace + shipSize > 10))
			return placeShip(size, true);

		ship = new HumanLocation(xPlace, yPlace, horizontal, shipSize);
		// System.out.println("X: " + ship.getX());
		// System.out.println("Y: " + ship.getY());

		if (isValid() == false) {
			return placeShip(size, true);
		}
		setBoard();

		topLeftCoordinate = ship;
		setOfCoordinates.add(topLeftCoordinate);

		return ship;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Player#getTarget()
	 */
	@Override
	public Location getTarget() throws ArrayIndexOutOfBoundsException,
			InputMismatchException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out
				.println("Please enter a number (0 - 9) "
						+ "to represent the X coordinate of the TARGET you are shooting at: ");
		if (in.hasNextInt()) {
			xTarget = in.nextInt();
		}

		System.out
				.println("Please enter a number (0 - 9) "
						+ "to represent the X coordinate of the TARGET you are shooting at: ");
		if (in.hasNextInt()) {
			yTarget = in.nextInt();
		}

		if (xTarget < 0 || yTarget < 0 || xTarget > 9 || yTarget > 9) {
			System.out
					.println("Invalid target. Please re-enter a valid target."
							+ "\n");
			return getTarget();
		}

		humanAim = new HumanLocation(xTarget, yTarget, false, 0);
		// System.out.println("humanAim x: " + humanAim.getX());
		// System.out.println("humanAim y: " + humanAim.getY());

		for (HumanLocation l : movesMade) {
			// System.out.println("movesMade l's x: " + l.getX());
			// System.out.println("movesMadel's y: " + l.getY());
			if (l.getX() == humanAim.getX() && l.getY() == humanAim.getY()) {
				System.out.println("You have already fired on that target."
						+ "\n" + "Re-enter valid coordinates" + "\n");
				return getTarget();
			}
		}

		movesMade.add(humanAim);
		return humanAim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Player#setResult(boolean, boolean)
	 */
	@Override
	public void setResult(boolean hit, boolean sunk) {
		// TODO Auto-generated method stub

		if (hit == true) {
			System.out.println("Your missile has hit an opposing ship!");
		}

		if (hit == false && sunk == false) {
			System.out.println("Your missile missed.");
		}

		if (hit == true && sunk == true) {
			System.out.println("You sunk an enemy ship!");
		}
	}

	/**
	 * Sets the board.
	 */
	public void setBoard() {
		int boardIter = shipSize;
		if (shipSize == 1) {
			boardIter = 3;
		}

		if (horizontal == false) {
			int yCoor = ship.getY();
			while (boardIter > 0) {
				humanBoard[ship.getX()][yCoor] = shipSize;
				boardIter--;
				yCoor++;
			}
		}

		if (horizontal != false) {
			int xCoor = ship.getX();
			while (boardIter > 0) {
				humanBoard[xCoor][ship.getY()] = shipSize;
				boardIter--;
				xCoor++;
			}
		}
		for (int col = 0; col < 10; col++) {
			for (int r = 0; r < 10; r++) {
				if (humanBoard[r][col] != 0) {
					humanBoard[r][col] = humanBoard[r][col];
				} else {
					humanBoard[r][col] = 0;
				}
			}
		}
	}

	/**
	 * Prints the board.
	 */
	public void printBoard() {
		System.out.println("Your current board: " + "\n");
		for (int col = 0; col < 10; col++) {
			for (int r = 0; r < 10; r++) {
				System.out.print(humanBoard[r][col] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Gets the board.
	 * 
	 * @return the board
	 */
	public int[][] getBoard() {
		return humanBoard;
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
				// System.out.println(humanBoard[ship.getX()][yCoor]);
				if (humanBoard[ship.getX()][yCoor] > 0) {
					okay = false;
					// System.out.println("Okay?: " + okay);
					return okay;
				}
				checkIter--;
				yCoor++;
			}
		}

		if (horizontal != false) {
			int xCoor = ship.getX();
			// System.out.println("Checkiter2 " + checkIter);
			// System.out.println("Okay?1: " + okay);
			while (checkIter > 0) {
				if (humanBoard[xCoor][ship.getY()] > 0) {
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
