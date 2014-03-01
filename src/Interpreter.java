import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Scanner;
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
		program.finalize(); //parsing is done. execution begins.
		programCounter = new PC(0, 0);
		quote = false;
	}
	
	void parse(String filename) {
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
			//System.out.print(instr);
			execute(instr);
			programCounter.move(currentDirection);
		}
	}
	
	
	private void push(int v) {
		stack.push(v);
	}
	
	private int peek() {
		int result;
		try {
			result = stack.peek();
		} catch (EmptyStackException e) {
			result = 0;
		}
		return result;
	}
	
	private int pop() {
		int result;
		try {
			result = stack.pop();
		} catch (EmptyStackException e) {
			result = 0;
		}
		return result;
	}
	
  public static int mod(int a, int n) {
    return ((a % n) + n ) % n;
  }
	
	void execute(char inst) {
		int num;
		try {
			num = Integer.parseInt("" + inst);
		} catch (NumberFormatException nfe) {
			num = -1;
		}
		//string mode
		if (inst == '"') {
			quote = !quote;
		} else if (quote) {
			push((int)inst);
		
		//numbers and arithmetic
		} else if (num >= 0) { //0-9
	  	push(num);
	  } else if (inst == '+') {
	  	int a = pop();
	  	int b = pop();
	  	push(a + b);
	  } else if (inst == '-') {
	  	int a = pop();
	  	int b = pop();
	  	push(b - a);
	  } else if (inst == '*') {
	  	int a = pop();
	  	int b = pop();
	  	push(a * b);
	  } else if (inst == '/') {
	  	int a = pop();
	  	int b = pop();
	  	push(b / a);
	  } else if (inst == '%') {
	  	int a = pop();
	  	int b = pop();
	  	push(mod(b, a));
	  } else if (inst == '!') {
	  	int a = pop();
	  	if (a == 0) {
	  		push(1);
	  	} else {
	  		push(0);
	  	}
	  } else if (inst == '`') {
	  	int a = pop();
	  	int b = pop();
	  	if(b > a) {
	  		push(1);
	  	} else {
	  		push(0);
	  	}
	  	
	  //control flow
	  } else if (inst == '<') {
			currentDirection = Dir.WEST;
		} else if (inst == '>') {
			currentDirection = Dir.EAST;
		} else if (inst == '^') {
			currentDirection = Dir.NORTH;
		} else if (inst == 'v') {
			currentDirection = Dir.SOUTH;
		} else if (inst == '?') {
			currentDirection = Dir.random();
		} else if (inst == '_') {
			int a = pop();
			if (a == 0) {
				currentDirection = Dir.EAST;
			} else {
				currentDirection = Dir.WEST;
			}
		} else if (inst == '|') {
			int a = pop();
			if (a == 0) {
				currentDirection = Dir.SOUTH;
			} else {
				currentDirection = Dir.NORTH;
			}
		} else if (inst == '#') {
			programCounter.move(currentDirection);
			
		//stack operations
		} else if (inst == ':') {
			push(peek());
		} else if (inst == '\\') {
			int a = pop();
			int b = pop();
			push(a);
			push(b);
		} else if (inst == '$') {
			pop();
			
		//input output
		} else if (inst == '.') {
			System.out.print(pop());
		} else if (inst == ',') {
			System.out.print((char)pop());
		} else if (inst == '&') {
			Scanner console = new Scanner(System.in);
			System.out.print("& ");
			push(console.nextInt());
		} else if (inst == '~') {
			Console console = System.console();
			System.out.print("~ ");
			push((int)console.readLine().charAt(0));
			
		//put and get
		//file locations are 1 indexed
		//but array is 0 indexed
		} else if (inst == 'p') {
			int y = pop() - 1;
			int x = pop() - 1;
			int v = pop();
			program.put(x, y, (char)v);
		} else if (inst == 'g') {
			int y = pop() - 1;
			int x = pop() - 1;
			push((int)program.get(x, y));
			
		//white space or invalid
		} else if (inst == ' ') {
			//do nothing
		} else {
			System.err.println("invalid character");
			System.exit(1);
		}
	}
}
