import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;


public class Interpreter {
	private Stack<Integer> stack;
	private Dir currentDirection;
	private Program program;
	private PC programCounter;
	private boolean quote;
	
	public static void main (String[] args) {
		Interpreter i = new Interpreter(args[0]);
		i.run();
	}

	Interpreter(String filename) {
		stack = new Stack<Integer>();
		currentDirection = Dir.EAST;
		parse(filename); //parse initializes program
		programCounter = new PC(0, 0);
		quote = false;
	}
	
	void parse(String filename) {
		int[] size = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			program = new Program(0, 0);
			String line;
			while ((line = reader.readLine()) != null) {
				program.addLine(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
	}
	
	void run() {
		char instr;
		while((instr = program.getInstruction(programCounter)) != '@') {
			execute(instr);
			programCounter.move(currentDirection);
		}
	}
	
	void execute(char instruction) {
		switch (instruction) {
		case '<': currentDirection = Dir.WEST; break;
		case '>': currentDirection = Dir.EAST; break;
		case '^': currentDirection = Dir.NORTH; break;
		case 'v': currentDirection = Dir.SOUTH; break;
		case '"': quote = !quote; break;
		default:
			if (quote) {
				System.out.print(instruction);
			}
		}
	}
	
}
