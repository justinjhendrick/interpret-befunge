import java.util.ArrayList;


public class Program {
	private ArrayList<String> instructions;
	private int width;
	private int height;
	
	Program (int _width, int _height) {
		width = _width;
		height = _height;
		instructions = new ArrayList<String>();
	}
	
	public void addLine (String line) {
		int len = line.length();
		if (len > width) {
			for(int i = 0; i < height; i++) {
				//add white spaces to ensure all lines same length
				instructions.set(i, 
						addWhiteSpace(len - width, instructions.get(i)));
			}
			width = len;
		} 
		if (width > len) {
			instructions.add(addWhiteSpace(width - len, line));
		} else {
			instructions.add(line);
		}
		height++;
	}
	
	private String addWhiteSpace(int n, String s) {
		for (int i = 0; i < n; i++) {
			s += " ";
		}
		return s;
	}
	
	public char getInstruction(PC pc) {
		int x = pc.getX();
		int y = pc.getY();
		char result;
		try {
			result = instructions.get(y).charAt(x);
		} catch (IndexOutOfBoundsException ioobe) {
			System.err.println("\nindex out of bounds at line " + 
					(y + 1) + ", character " + (x + 1) + ".");
			result = '\0';
			System.out.println("\tparsed instructions are:");
			System.out.println("\t" + instructions.toString());
			System.exit(1);
		}
		return result;
	}
}
