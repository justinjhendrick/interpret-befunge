
public class PC {
	private int x;
  private int y;
	
	PC(int _x, int _y) {
		x = _x;
		y = _y;
	}
	
	//0, 0 is top left
	//x increases east
	//y increases south
	public void move(Dir d) {
		if (d == Dir.NORTH) {
			y--;
		} else if (d == Dir.SOUTH) {
			y++;
		} else if (d == Dir.EAST) {
			x++;
		} else { //WEST
			x--;
		}
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}