import java.util.ArrayList;


public class Program {
	private ArrayList<String> building_instr;
	private char[][] instructions;
	private int width;
	private int height;
	
	Program (int _width, int _height) {
		width = _width;
		height = _height;
		building_instr = new ArrayList<String>();
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void addLine (String line) {
		int len = line.length();
		if (len > width) {
			for(int i = 0; i < height; i++) {
				//add white spaces to ensure all lines same length
				building_instr.set(i, 
						addWhiteSpace(len - width, building_instr.get(i)));
			}
			width = len;
		} 
		if (width > len) {
			building_instr.add(addWhiteSpace(width - len, line));
		} else {
			building_instr.add(line);
		}
		height++;
	}
	
	private String addWhiteSpace(int n, String s) {
		for (int i = 0; i < n; i++) {
			s += " ";
		}
		return s;
	}
	
	//call when parsing is done and execution begins
	public void finalize() {
		instructions = new char[height][width];
		for(int r = 0; r < height; r++) {
			for(int c = 0; c < width; c++) {
				instructions[r][c] = building_instr.get(r).charAt(c);
			}
		}
		building_instr = null;
	}
	
	public char getInstruction(PC pc) {
		int x = pc.getX();
		int y = pc.getY();
		char result;
		try {
			result = instructions[y][x];
		} catch (IndexOutOfBoundsException ioobe) {
			System.err.println("\nindex out of bounds at line " + 
					(y + 1) + ", character " + (x + 1) + ".");
			result = '\0';
			System.out.println("\tparsed building_instr are:");
			System.out.println("\t" + building_instr.toString());
			System.exit(1);
		}
		return result;
	}
	
	
	//For use by 'p' and 'g'
	public void put(int x, int y, char v) {
		instructions[y][x] = v;
	}
	public char get(int x, int y) {
		return instructions[y][x];
	}
}
