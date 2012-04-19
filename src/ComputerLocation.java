
public class ComputerLocation implements Location {

	private int x;
	private int y;
	private boolean horizontal;
	private int shipSize;

	public ComputerLocation(int x, int y, boolean horizontal, int shipSize) {
		this.x = x;
		this.y = y;
		this.horizontal = horizontal;
		this.shipSize = shipSize;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	/**
	 * 
	 */
	public boolean isShipHorizontal() {
		return horizontal;
	}

	public int getShipSize() {
		return shipSize;
	}

}