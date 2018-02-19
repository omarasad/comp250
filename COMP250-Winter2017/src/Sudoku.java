import java.util.*;
import java.io.*;
import java.util.LinkedList;

class Sudoku
{
    /* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
     * a standard Sudoku puzzle, SIZE is 3 and N is 9. */
    int SIZE, N;

    /* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
     * not yet been revealed are stored as 0. */
    int Grid[][];
    
    /*LinkedList<Integer> op; 
    public Sudoku(LinkedList<Integer> d){
    	for(int i=0; i<d.size(); i++){
    		this.op.add(d.get(i));
    	}
    }*/
    
    /* The solve() method should remove all the unknown characters ('x') in the Grid
     * and replace them with the numbers from 1-9 that satisfy the Sudoku puzzle. */
    public void solve(){    	
    	LinkedList<Integer> unknowns = new LinkedList<Integer>();
    	for(int row=0; row<N; row++){
    		for(int col=0; col<N; col++){
    			if(Grid[row][col]==0){
        			//LinkedList<Integer> options = new LinkedList<Integer>();
    				//System.out.println("unknown found @ [" + row + "]" + "[" + col + "]");
    				unknowns.add(row);
    				unknowns.add(col);
    				System.out.println(row  +" "+col);
    				
    				//Finding options
    				/*for(int currentValue=1; currentValue<=9; currentValue++){
    					//System.out.println("try " + currentValue);
    					if(valid(row,col,currentValue)==true){
    						//System.out.println("value " + currentValue + " works!");
    						//options.addLast(currentValue);
    					}
    				}*/
    			}
    		}
    	}
    	Grid = backTrack(Grid, unknowns);
    }
    
    public void printList(LinkedList<Integer> l){ 
    	System.out.println("unknowns are at index:");
    	for(int i=0; i<l.size()-1; i=i+2){
    		System.out.print("[" + l.get(i) + "]");
    		System.out.print("[" + l.get(i+1) + "]");
    		System.out.println();
    	}
    }
    static int row=0;
	static int col=1;
    public int[][] backTrack(int[][] a, LinkedList<Integer> b){
    	System.out.println("size of list: " + b.size());
    	System.out.println("row:" + row);
    	System.out.println("col: " + col);
    	
    	if(row >= b.size()-2||col >=b.size()-2){
    		System.out.println("no more unknown values!");
    		return a;
    	}
    	
    	else{
    	System.out.println("row else:" + row);
    	System.out.println("col else: " + col);
    		System.out.println("[" + b.get(row) + "][" + b.get(col) + "]");
    		for(int t=1; t<=9; t++){
    			if(valid(b.get(row),b.get(col),t)==true){
    				a[b.get(row)][b.get(col)]=t;
    				System.out.println("[" + b.get(row) + "][" + b.get(col) + "] = " + t);
    				row+=2;
    				col+=2;
    				backTrack(a,b);
    			}
    			else{
    				a[b.get(row)][b.get(col)]=0;
    			}
    		}
    		return a;
    	}
    }
    
    public void printOptions(int a, int b, LinkedList<Integer> d){
    	System.out.println("Options for index [" + a + "][" + b + "]");
    	for(int i=0; i<d.size();i++){
    		System.out.print(d.get(i) + " ");
    	}
    	System.out.println();
    }

    public boolean valid(int r, int c, int v){
    	//check that (int v) is not already in the row
    	for(int d=0; d<N; d++){
    		if(v == Grid[r][d]){
    			//System.out.println(v + "=" + " value in same row");
    			return false;
    		}
    	}
    	//check to see if (int v) is already in the column
    	for(int e=0; e<N; e++){
    		if(v==Grid[e][c]){
    			//System.out.println(v + "=" + " value in same column");
    			return false;
    		}
    	}
    	//check to see if (int v) is already in the same box
    		int startCol = (c/3)*3;
    		//System.out.println("startCol:" + startCol);
    		
    		int startRow = (r/3)*3;
    		//System.out.println("startRow:" + startRow);
    		System.out.println("startRow:" + startRow +"startCol:" +startCol +"r:"+r +"c:"+c);
    		for(int width = startCol; width<startCol+3; width++){
        		for(int height=startRow; height<startRow+3; height++){
        			if(v==Grid[height][width]){
        				//System.out.println(v + "=" + " value in the same box");
        				//System.out.println("value at index [" + width + "][" + height + "]" + Grid[width][height]);
        				return false;
        			}
        		}
    		}
    	return true;
    }
    /*****************************************************************************/
    /* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE FUNCTIONS BELOW THIS LINE. */
    /*****************************************************************************/
 
    /* Default constructor.  This will initialize all positions to the default 0
     * value.  Use the read() function to load the Sudoku puzzle from a file or
     * the standard input. */
    public Sudoku( int size )
    {
        SIZE = size;
        N = size*size;

        Grid = new int[N][N];
        for( int i = 0; i < N; i++ ) 
            for( int j = 0; j < N; j++ ) 
                Grid[i][j] = 0;
    }


    /* readInteger is a helper function for the reading of the input file.  It reads
     * words until it finds one that represents an integer. For convenience, it will also
     * recognize the string "x" as equivalent to "0". */
    static int readInteger( InputStream in ) throws Exception
    {
        int result = 0;
        boolean success = false;

        while( !success ) {
            String word = readWord( in );

            try {
                result = Integer.parseInt( word );
                success = true;
            } catch( Exception e ) {
                // Convert 'x' words into 0's
                if( word.compareTo("x") == 0 ) {
                    result = 0;
                    success = true;
                }
                // Ignore all other words that are not integers
            }
        }

        return result;
    }


    /* readWord is a helper function that reads a word separated by white space. */
    static String readWord( InputStream in ) throws Exception
    {
        StringBuffer result = new StringBuffer();
        int currentChar = in.read();
	String whiteSpace = " \t\r\n";
        // Ignore any leading white space
        while( whiteSpace.indexOf(currentChar) > -1 ) {
            currentChar = in.read();
        }

        // Read all characters until you reach white space
        while( whiteSpace.indexOf(currentChar) == -1 ) {
            result.append( (char) currentChar );
            currentChar = in.read();
        }
        return result.toString();
    }


    /* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
     * grid is filled in one row at at time, from left to right.  All non-valid
     * characters are ignored by this function and may be used in the Sudoku file
     * to increase its legibility. */
    public void read( InputStream in ) throws Exception
    {
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                Grid[i][j] = readInteger( in );
            }
        }
    }


    /* Helper function for the printing of Sudoku puzzle.  This function will print
     * out text, preceded by enough ' ' characters to make sure that the printint out
     * takes at least width characters.  */
    void printFixedWidth( String text, int width )
    {
        for( int i = 0; i < width - text.length(); i++ )
            System.out.print( " " );
        System.out.print( text );
    }


    /* The print() function outputs the Sudoku grid to the standard output, using
     * a bit of extra formatting to make the result clearly readable. */
    public void print()
    {
        // Compute the number of digits necessary to print out each number in the Sudoku puzzle
        int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

        // Create a dashed line to separate the boxes 
        int lineLength = (digits + 1) * N + 2 * SIZE - 3;
        StringBuffer line = new StringBuffer();
        for( int lineInit = 0; lineInit < lineLength; lineInit++ )
            line.append('-');

        // Go through the Grid, printing out its values separated by spaces
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                printFixedWidth( String.valueOf( Grid[i][j] ), digits );
                // Print the vertical lines between boxes 
                if( (j < N-1) && ((j+1) % SIZE == 0) )
                    System.out.print( " |" );
                System.out.print( " " );
            }
            System.out.println();

            // Print the horizontal line between boxes
            if( (i < N-1) && ((i+1) % SIZE == 0) )
                System.out.println( line.toString() );
        }
    }


    /* The main function reads in a Sudoku puzzle from the standard input, 
     * unless a file name is provided as a run-time argument, in which case the
     * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
     * outputs the completed puzzle to the standard output. */
    public static void main( String args[] ) throws Exception
    {
        InputStream in;
        if( args.length > 0 ) 
            in = new FileInputStream( args[0] );
        else
            in = System.in;

        // The first number in all Sudoku files must represent the size of the puzzle.  See
        // the example files for the file format.
        int puzzleSize = readInteger( in );
        if( puzzleSize > 100 || puzzleSize < 1 ) {
            System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
            System.exit(-1);
        }

        Sudoku s = new Sudoku( puzzleSize );

        // read the rest of the Sudoku puzzle
        s.read( in );

        // Solve the puzzle.  We don't currently check to verify that the puzzle can be
        // successfully completed.  You may add that check if you want to, but it is not
        // necessary.
        s.solve();

        // Print out the (hopefully completed!) puzzle
        s.print();
    }
}
