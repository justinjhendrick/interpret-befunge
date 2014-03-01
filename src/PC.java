
public class PC {
	private int x;
  private int y;
  private int width;
  private int height;
	
	PC(int _x, int _y, int _width, int _height) {
		x = _x;
		y = _y;
		width = _width;
		height = _height;
	}
	
	//0, 0 is top left
	//x increases east
	//y increases south
	public void move(Dir d) {
		if (d == Dir.NORTH) {
			y--;
			y = Interpreter.mod(y, height); //wraparound
		} else if (d == Dir.SOUTH) {
			y++;
			y = Interpreter.mod(y, height);
		} else if (d == Dir.EAST) {
			x++;
			x = Interpreter.mod(x, width);
		} else { //WEST
			x--;
			x = Interpreter.mod(x, width);
		}
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}