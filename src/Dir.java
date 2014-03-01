import java.util.Random;



public enum Dir {
  	NORTH, SOUTH, EAST, WEST;
  	private static final Random RANDOM = new Random();
    public static Dir random()  {
      int r = RANDOM.nextInt(4);
      if (r == 0) {
      	return NORTH;
      } else if (r == 1) {
      	return SOUTH;
      } else if (r == 2) {
      	return EAST;
      } else {
      	return WEST;
      }
    }
}
