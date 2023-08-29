
import java.io.IOException;
import java.io.FileNotFoundException;

//Import necessary libraries for exception handling and file input/output operations

//Class that implements the pathfinding in the pyramid
public class PathFinder {
    private Map pyramidMap;
    
    // Instance variable for the map of the pyramid
    public PathFinder(String fileName) throws InvalidMapCharacterException, FileNotFoundException, IOException {
        pyramidMap = new Map(fileName);
    }

    // Method that returns the path through the pyramid
    public DLStack<Chamber> path() throws InvalidNeighbourIndexException {
    	// Create a stack to hold the chambers in the path
        DLStack<Chamber> stack = new DLStack<>();
     // Start from the entrance and add it to the stack
        Chamber start = pyramidMap.getEntrance();
        start.markPushed();
        stack.push(start);
        int N = pyramidMap.getNumTreasures();

        //Continue until all treasures are found or all possible paths are exhausted
        while (!stack.isEmpty()) {
            Chamber current = stack.peek();
            if (current.isTreasure() && N == 0) {
                break;
            }
            // If a chamber is a treasure, decrease the number of treasures left to find
            Chamber best = bestChamber(current);
            if (best != null) {
                best.markPushed();
                stack.push(best);
                if (best.isTreasure()) {
                    N--;
                }
             // If no more possible paths from a chamber, remove it from the stack
            } else {
                current = stack.pop();
                current.markPopped();
            }
        }

        return stack;
    }

    // Getter method for the pyramid map
    public Map getMap() {
        return pyramidMap;
    }
    
    // Method that checks if a chamber is dimly lit
    public boolean isDim(Chamber currentChamber) throws InvalidNeighbourIndexException {
        if (currentChamber == null || currentChamber.isSealed() || currentChamber.isLighted()) {
            return false;
        }
        for (int i = 0; i < 6; i++) {
        	// A chamber is considered dimly lit if it's not sealed or lighted and has at least one lighted neighbor
            Chamber neighbour = currentChamber.getNeighbour(i);
            if (neighbour != null && neighbour.isLighted()) {
                return true;
            }
        }
        return false;
    }
    
   // Method that returns the best chamber to move to from the current chamber
    public Chamber bestChamber(Chamber currentChamber) throws InvalidNeighbourIndexException {
        Chamber best = null;
        for (int i = 0; i < 6; i++) {
            Chamber neighbour = currentChamber.getNeighbour(i);
            if (neighbour == null || neighbour.isMarked()) {
                continue;
            }
          //If there is a neighbor chamber with a treasure, it's the best chamber
            if (neighbour.isTreasure()) {
                return neighbour;
            }
            //If there is a lighted neighbor chamber and there's no previously selected best chamber or
            //the best chamber is not lighted, it's the best chamber
            if (neighbour.isLighted() && (best == null || !best.isLighted())) {
                best = neighbour;
            }
            //If there is a dim chamber and there's no previously selected best chamber, it's the best chamber
            if (isDim(neighbour) && best == null) {
                best = neighbour;
            }
        }
        return best;
    }
}


//summary
/**
 * The PathFinder class implements the pathfinding logic for traversing the pyramid. 
 * It uses an approach of stack-based depth-first search algorithm to explore within the map. 
 * Chambers are pushed on the stack, one after the other as they are discovered, and are popped off when they are not.
 * unvisited neighboring chambers can be found by the `path()` method implements this logic.
 * The `bestChamber()` method uses perfect approximation to choose the next chamber to visit.
 * the priority order for the chambers is chambers with treasures, then lighted chambers, and finally dim chambers.
 * whether a chamber is dim. The `isDim()` method was created to encapsulate this logic.
 * The program was tested using multiple map files with different configurations 
 * to ensure robustness. All methods are individually tested to verify the working 
 * and the behavior of the program.
 */
