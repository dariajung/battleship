import java.util.InputMismatchException;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class Tester.
 */
public class Tester {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Player p1 = null, p2 = null;
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Welcome to BattleShip!" + "\n");
			System.out
			.println("For this game, do you want two human players, one human player and one computer player, "
					+ "or two computer players?"
					+ "\n"
					+ "(Type 1, 2, or 3): ");
			int decision = Integer.parseInt(input.next());

			switch (decision) {
			case 1:
				p1 = new HumanPlayer();
				p2 = new HumanPlayer();
				break;
			case 2:
				p1 = new HumanPlayer();
				p2 = new ComputerPlayer();
				break;
			case 3:
				p1 = new ComputerPlayer();
				p2 = new ComputerPlayer();
				break;
			}

			Game game = new BattleShipGame();
			game.initialize(p1, p2);
			game.playGame();
		}

		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
