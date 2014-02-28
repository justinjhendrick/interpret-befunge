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
	
	void addLine (String line) {
		int len = line.length();
		if (len > width) {
			int nWhiteSpaces = len - width;
			for(int i = 0; i < height; i++) {
				//add white spaces to ensure all lines same length
				instructions.set(i, 
						instructions.get(i) + (nWhiteSpaces * ' '));
			}
			width = len;
		}
		instructions.add(line);
		height++;
	}
	
	char getInstruction(PC pc) {
		int x = pc.getX();
		int y = pc.getY();
		return instructions.get(y).charAt(x);
	}
}
