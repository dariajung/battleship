import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class BattleShipGame.
 */
public class BattleShipGame implements Game {

	/** The p1 and p2 boards. */
	private int[][] p1Board, p2Board, board;

	/** Players */
	private Player p1, p2, playerWin, generalPlayer;

	/** The ships to place represented as integers. */
	private ArrayList<Integer> shipsToPlace;

	/** The original placement of ships made for each player. */
	private ArrayList<Location> shipPlacementP1, shipPlacementP2;

	private ArrayList<Location> movesMadeP1, movesMadeP2;

	/** The winner. */
	private boolean check, hit, sunk;

	/** The ship. */
	private Location ship, target;

	/** Various int variables. */
	private int hitShipSize, hits, p1SunkShips, p2SunkShips;

	/**
	 * Instantiates a new battle ship game.
	 */
	public BattleShipGame() {
		shipPlacementP1 = new ArrayList<Location>();
		shipPlacementP2 = new ArrayList<Location>();
		movesMadeP1 = new ArrayList<Location>();
		movesMadeP2 = new ArrayList<Location>();
		p1Board = new int[SIZE][SIZE];
		p2Board = new int[SIZE][SIZE];
		board = new int[SIZE][SIZE];
		hit = false;
		sunk = false;
		hits = 0;
		p1SunkShips = 0;
		p2SunkShips = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Game#initialize(Player, Player)
	 */
	@Override
	public void initialize(Player p1, Player p2) {
		// TODO Auto-generated method stub
		this.p1 = p1;
		this.p2 = p2;

		p1Board = new int[SIZE][SIZE];
		p2Board = new int[SIZE][SIZE];
		board = new int[SIZE][SIZE];
		check = false;

		shipsToPlace = new ArrayList<Integer>();

		shipsToPlace.add(CARRIER);
		shipsToPlace.add(BATTLESHIP);
		shipsToPlace.add(SUBMARINE);
		shipsToPlace.add(CRUISER - 2); // Cruiser will be represented as a 1 on
		// the grid
		shipsToPlace.add(DESTROYER);

		System.out.println("Deploying ships for Player 1 ");
		for (int shipSize : shipsToPlace) {
			int size = shipSize;
			if (shipSize == 1) {
				size = 3;
			}
			System.out.println("Placing ship of size: " + size);
			ship = p1.placeShip(shipSize, check);
			shipPlacementP1.add(ship);
			setBoard(shipSize, p1);
			if(p1 instanceof HumanPlayer){
				System.out.println("\n"+ "Player 1's Board: ");
				printBoard(p1);
			}
		}
		
		System.out.println("\n" + "Player 1's Set Board: ");
		printBoard(p1);
		System.out.println("Player 1's ships have been deployed" + "\n");

		System.out.println("Deploying ships for Player 2 ");
		for (int shipSize : shipsToPlace) {
			int size = shipSize;
			if (shipSize == 1) {
				size = 3;
			}
			System.out.println("Placing ship of size: " + size);
			ship = p2.placeShip(shipSize, check);
			shipPlacementP2.add(ship);
			setBoard(shipSize, p2);
			if(p2 instanceof HumanPlayer){
				System.out.println("Player 2's Board: ");
				printBoard(p2);
			}
		}
		System.out.println("\n" + "Player 2's Set Board: ");
		printBoard(p2);
		System.out.println("\n"+ "Player 2's ships have been deployed" + "\n");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Game#playGame()
	 */
	@Override
	public Player playGame() {
		// TODO Auto-generated method stub

		while (p1SunkShips < 5 || p2SunkShips < 5) {
			System.out.println("\n" + "Player 1 is firing a missile" + "\n");
			target = p1.getTarget();
			int p1TargetX = target.getX();
			int p1TargetY = target.getY();
			movesMadeP1.add(target);

			if (p2Board[p1TargetX][p1TargetY] > 0) {
				hit = true;
				hitShipSize = p2Board[p1TargetX][p1TargetY];
				p2Board[p1TargetX][p1TargetY] = (-1)
						* p2Board[p1TargetX][p1TargetY];
			}

			if (hit == false) {
				sunk = false;
			}

			if (hit == true) // check if sunk
			{
				sunk = checkSunkFor(p1, hitShipSize);
				// System.out.println("P2's board hit only:");
				// printBoard(p2);
			}

			if (hit == true && sunk == true) {
				p1SunkShips++; // number of ships of p2 that player
				// 1 has sunk
				// System.out.println("P2's board hit and sunk:");
				// printBoard(p2);
			}

			p1.setResult(hit, sunk);
			System.out.println("Player1 has sunk " + p1SunkShips
					+ "/5 of player 2's ships");
			if (p1SunkShips == 5) {
				playerWin = p1;
				System.out.println("Player 2's board: ");
				printBoard(p2);
				System.out.println("\n" + "Player 1 won!");
				// System.out.println("Player1's board");
				// printBoard(p1);
				return playerWin;
			}

			System.out.println("\n" + "Player 2 is firing a missile" + "\n");
			target = p2.getTarget();
			int p2TargetX = target.getX();
			int p2TargetY = target.getY();
			movesMadeP2.add(target);

			if (p1Board[p2TargetX][p2TargetY] > 0) {
				hit = true;
				hitShipSize = p1Board[p2TargetX][p2TargetY];
				p1Board[p2TargetX][p2TargetY] = (-1)
						* p1Board[p2TargetX][p2TargetY];
			} else {
				hit = false;
				sunk = false;
			}

			if (hit == true) // check if sunk
			{
				sunk = checkSunkFor(p2, hitShipSize);
				// System.out.println("P2's board hit only:");
				// printBoard(p1);
			}

			if (hit == false) {
				sunk = false;
			}

			if (hit == true && sunk == true) {
				p2SunkShips++; // number of ships of p2 that player
				// 1 has sunk
				// System.out.println("P1's board hit and sunk:");
				// printBoard(p1);
			}

			p2.setResult(hit, sunk);
			System.out.println("Player2 has sunk " + p2SunkShips
					+ "/5 of player 1's ships");

			if (p2SunkShips == 5) {
				playerWin = p2;
				System.out.println("Player 1's board: ");
				printBoard(p1);
				System.out.println("Player 2 won!");
				// System.out.println("player2's board");
				// printBoard(p2);
				return playerWin;
			}
			System.out.println("P1's board:");
			printBoard(p1);

			hit = false;
			sunk = false;
		}

		return playerWin;
	}

	/**
	 * Sets the board.
	 * 
	 * @param i
	 * 
	 */
	public void setBoard(int i, Player p) {
		int boardIter = i;
		if (boardIter == 1) {
			boardIter = 3;
		}
		int shipSize = i;
		generalPlayer = p;

		if (generalPlayer == p1) {
			board = p1Board;
		}

		if (generalPlayer == p2) {
			board = p2Board;
		}

		boolean horizontal = ship.isShipHorizontal();

		if (horizontal == false) {
			int yCoor = ship.getY();
			while (boardIter > 0) {
				board[ship.getX()][yCoor] = shipSize;
				boardIter--;
				yCoor++;
			}
		}

		if (horizontal != false) {
			int xCoor = ship.getX();
			while (boardIter > 0) {
				board[xCoor][ship.getY()] = shipSize;
				boardIter--;
				xCoor++;
			}
		}
		for (int col = 0; col < SIZE; col++) {
			for (int r = 0; r < SIZE; r++) {
				if (board[r][col] != 0) {
					board[r][col] = board[r][col];
				} else {
					board[r][col] = 0;
				}
				// System.out.print(p1Board[r][col] + " ");
			}
			// System.out.println();
		}

		if (generalPlayer == p1) {
			p1Board = board;
		}

		if (generalPlayer == p2) {
			p2Board = board;
		}

	}

	/**
	 * gets the indicated board
	 * 
	 * @return the p1 board
	 */
	public int[][] getBoard(Player p) {
		generalPlayer = p;

		if (generalPlayer == p1) {
			board = p1Board;
		}

		if (generalPlayer == p2) {
			board = p2Board;
		}

		return board;
	}

	/**
	 * Prints the board.
	 */
	public void printBoard(Player p) {

		generalPlayer = p;
		if (generalPlayer == p1) {
			board = p1Board;
		}

		if (generalPlayer == p2) {
			board = p2Board;
		}

		for (int col = 0; col < SIZE; col++) {
			for (int r = 0; r < SIZE; r++) {
				System.out.print(board[r][col] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Check sunk for indicated player and the size of the ship that was hit.
	 * 
	 * @param p
	 *            the p
	 * @param hitSize
	 *            the hit size
	 * @return true, if successful
	 */
	public boolean checkSunkFor(Player p, int hitSize) {
		// System.out.println("hitSize: " + hitSize);
		generalPlayer = p;
		sunk = false;

		if (generalPlayer == p1) {
			board = p2Board;
			// System.out.println("Initial hits p1: " + hits);
		}

		if (generalPlayer == p2) {
			board = p1Board;
			// System.out.println("Initial hits p2: " + hits);
		}

		for (int col = 0; col < 10; col++) {
			for (int r = 0; r < 10; r++) {
				if (board[r][col] == -hitSize) {
					hits++;
				}
			}
		}

		if (hits == hitSize && hitSize != 1) {
			// System.out.println("hits: "+ hits);
			// System.out.println("hitSize "+ hitSize);
			sunk = true;
		}

		if (hitSize == 1) {
			// System.out.println(hits);
			if (hits == 3) {
				sunk = true;
			}
		}
		hits = 0;
		return sunk;
	}

}
