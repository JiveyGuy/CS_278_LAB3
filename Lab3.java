import java.util.Scanner;

/**
 * This program tests predicates W1 and W2 for models and counter models. 
 * @author  Jason Ivey
 * @version .0
 * @since   2019-02-11
 * 
 * * The runtime of this program for W1 is 0() = n^2
 * * The runtime of this program for W2 is 0() = 2^( (n^2)^2 )
 * 
 * INPUT w1 
 * INPUT 1
 * 
 * EXPECTED & RECIEVED OUTPUT MATCH = VALID
 * 
 * INPUT w2
 * INPUT 1
 * 
 * EXPECTED & RECIEVED OUTPUT MATCH = VALID
 * 
 */

public class Lab3{
  
  /**
   * main method parses CLI input and selects w1 or w2, can also show help and loop multiple time. 
   * @param     args String input from the command line agrs (not used here)
   */
  public static void main(String args[]){
    
    Scanner input = new Scanner( System.in );
    String[] keywords = { "help", "w1", "w2","exit" };
    int[] commands;
    
    System.out.print("---------------------------------------------------------------------------\n"
                       +",-.                    .         .   ,     .   .     ,     ,.  ,-.    ,--, \n"
                       +"|  \\ o                 |         |\\ /|     |   |     |    /  \\ |  )     /  \n"
                       +"|  | . ,-. ,-. ;-. ,-. |-  ,-.   | V | ,-: |-  |-.   |    |--| |-<     `.  \n"
                       +"|  / | `-. |   |   |-' |   |-'   |   | | | |   | |   |    |  | |  )      ) \n"
                       +"`-'  ' `-' `-' '   `-' `-' `-'   '   ' `-` `-' ' '   `--' '  ' `-'    `-'  \n"
                       +"                                                                           \n"
                       +".          ,                   ,                 .  . .   ,  ,-.  .  .     \n"
                       +"|          |                   |                 |\\ | |\\ /| (   ` |  |     \n"
                       +"|-. . .    | ,-: ,-. ,-. ;-.   | . , ,-. . .     | \\| | V |  `-.  |  |     \n"
                       +"| | | |    | | | `-. | | | |   | |/  |-' | |     |  | |   | .   ) |  |     \n"
                       +"`-' `-|   -' `-` `-' `-' ' '   ' '   `-' `-| p   '  ' '   '  `-'  `--`     \n"
                       +"    `-'                                  `-'                               \n"
                       +",--. ,--. ,-.    ,-.   ,-.   ,  ,-.                                        \n"
                       +"|    |    |  )      ) /  /\\ '| (   )                                       \n"
                       +"|-   |-   |-<      /  | / |  |  `-'|                                       \n"
                       +"|    |    |  )    /   \\/  /  |     /                                       \n"
                       +"'    `--' `-'    '--'  `-'   '  `-'                                        \n"
                       +"---------------------------------------------------------------------------\n\n"
                    ); // Ascii art output block, makes the program better (value statement). 
    
    boolean sentinel = false;
    while( !sentinel ){
      
      System.out.println("Enter help for help or input: ");
      String inputString = input.nextLine();
      
      if( inputString.trim().length() == 0 ){ //exit on no output
        sentinel = true;
        continue;
      }
      
      try{  
        commands = Funcs.parse(inputString, keywords); //return array of parsed commands
        
        if( commands.length != 1 ){
          System.err.println("Either you have too many commands, or you have given an input that matches multiple commands!\n\tTry again...");
          continue;
        }
        
        int itor = commands[0];
        switch( itor ){
          
          case 0: 
            displayHelp();
            break;
            
          case 1: 
            runW1(input);
            break;
            
          case 2:
            runW2(input);
            break;
            
          case 3: 
            sentinel = true;
            continue;
        }
        
      } catch ( KeyWordInputException kwie ){ //Catching bad input such as numbers and non-commands.
        System.err.println(kwie); 
      }
    }
    System.out.println("Thanks for running LAB3, have a nice day!\n\tLAB3 EXIT");
  }
  
  /**
   * runW1 method to drive W1 predicate, main functionality is to display info and accept input, then
   * calls Funcs gen to make all possible combinations. Once this is done it tests W1 for all combinations
   * and prints out all models and counter models. 
   * 
   * @param     input Scanner input from the main function
   */
  public static void runW1(Scanner input){
    
    System.out.print("======================================================================\n"
                       +"                     Welcome to predicate W1.                         \n"
                       +"W1 = (( Exists(x) p(x) ) ^ ( Exists(x) q(x) )) -> Exists(x)(p(x)^q(x))\n"
                       +"W1 can bre represented as the following five statements:\n"
                       +"\tW     = f1() -> Exists(x) f2(x)\n"
                       +"\tf1()  = f3() ^ f4()\n"
                       +"\tf2(x) = p(x) ^ q(x)\n"
                       +"\tf3()  = Exists(x) p(x)\n"
                       +"\tf4()  = Exists(x) q(x)\n"
                       +"This program can generate all models and counter models of a width:\n"
                       +"\tExample width = 1:\n"
                       +"\t\tset1 = 0\n"
                       +"\t\tset2 = 1\n"
                       +"\n"
                       +"\tExample width = 2:\n"
                       +"\t\tset1 = 0 0\n"
                       +"\t\tset2 = 0 1\n"
                       +"\t\tset3 = 1 0\n"
                       +"\t\tset4 = 1 1\n"
                       +"======================================================================\n"
                       +"width:");
    
    String inputString = input.nextLine();
    String buffer = ""; // buffer is used to load all output to JVM memory before printing reduces runtime by a lot
    
    int width = Integer.parseInt(inputString.trim());
    boolean[][] allSets = Funcs.gen( width );
    
    System.out.println("Loading " + (int)(Math.pow(2,width)*Math.pow(2,width)) + 
                       ((width > 3) ? " sets, this may take a long while." : " sets." )); //let user know the process is running
    
    for(int p = 0; p < allSets.length; p++)
      for(int q = 0; q < allSets.length; q++){
      try{
        
        Funcs.debug("Trying sets:\n\tp:" + Funcs.modelToString("\t\t",allSets[p])
                      +"\n\tq:"+Funcs.modelToString("\t\t",allSets[q]));
        
        if( W1.W(allSets[p], allSets[q]) )
          buffer += displayModel(true, p, q, allSets);
        else
          buffer += displayModel(false, p, q, allSets);
        
      } catch ( inputDimmensionMismatch idm ){ //happens when p and q are not the same dimmension. 
        System.out.println( idm ); 
      }
    }
    
    System.out.print(buffer);
    
  }
  
  /**
   * runW2 method to drive W2 predicate, main functionality is to display info and accept input, then
   * calls Funcs gen to make all possible combinations. Once this is done it tests W1 for all combinations
   * and prints out all models and counter models. Very similiar to runW1 but the predicate is different
   * and has longer runtime due to r.
   * 
   * @param     input Scanner input from the main function
   */
  public static void runW2(Scanner input){
    System.out.print("======================================================================\n"
                       +"                     Welcome to predicate W2.                         \n"
                       +"W2 = W2 = (( forAll(x), ( P(x) -> (Q(x) \\/ R(x)))) ^ \n"
                       +" ( Exists(x), ( Q(x) ^ R(x)))) -> (Exists(x), (P(x) ^ R(x)))\n"
                       +"W2 can bre represented as the following eight statements:\n"
                       +"\tW = f1() -> f2()\n"
                       +"\tf1() = f3() ^ f4()\n"
                       +"\tf2() = Exists(x) f5(x)\n"
                       +"\tf3() = All(x) P(x) -> f6(x)\n"
                       +"\tf4() = Exists(x) f7(x)\n"
                       +"\tf5(x) = P(x) ^ R(x)\n"
                       +"\tf6(x) = Q(x) \\/ R(x)\n"
                       +"\tf7(x) = Q(x) ^ R(x)\n"
                       +"This program can generate all models and counter models of a width:\n"
                       +"\tExample width = 1:\n"
                       +"\t\tset1 = 0\n"
                       +"\t\tset2 = 1\n"
                       +"\n"
                       +"\tExample width = 2:\n"
                       +"\t\tset1 = 0 0\n"
                       +"\t\tset2 = 0 1\n"
                       +"\t\tset3 = 1 0\n"
                       +"\t\tset4 = 1 1\n"
                       +"======================================================================\n"
                       +"width:");
    String inputString = input.nextLine();
    String buffer = "";
    int width = Integer.parseInt(inputString.trim());
    boolean[][] allSets = Funcs.gen( width );
    System.out.println("Loading " + (int)(Math.pow(2,width)*Math.pow(2,width)*Math.pow(2,width)) + 
                       ((width > 3) ? " sets, this may take a long while." : " sets." ));
    
    for(int p = 0; p < allSets.length; p++)
      for(int q = 0; q < allSets.length; q++)
      for(int r = 0; r < allSets.length; r++){
      
      try{
        Funcs.debug("Trying sets:\n\tp:" + Funcs.modelToString("\t\t",allSets[p])
                      +"\n\tq:"+Funcs.modelToString("\t\t",allSets[q])
                      +"\n\tr:"+Funcs.modelToString("\t\t",allSets[r]));
        
        if( W2.W(allSets[p], allSets[q], allSets[r]) )
          buffer += displayModel(true, p, q, r, allSets);
        else
          buffer += displayModel(false, p, q, r, allSets);
      } catch ( inputDimmensionMismatch idm ){
        System.out.println( idm ); 
      }
    }
    System.out.print(buffer);
    
  }
  
  /**
   * displayModel (overload method) Simply displays a set and it's status as a model or counter model. Changes "true" to "1" 
   * and "false" to "0" for cleaner out put.
   * 
   * @param     isModel 
   * @param     p The index of the p set.
   * @param     q The index of the q set.
   * @param     r The index of the r set.
   * @param     sets All possible sets.
   */
  public static String displayModel(boolean isModel, int p, int q, int r, boolean[][] sets){
    String result = "";
    result += ( (( isModel ) ? "Model " : "Countermodel ") + "found at ("+p+","+q+","+r+"):")+"\n";
    
    for( int counter = 0; counter < 3; counter++){
      result += ( ((counter == 0) ? 
                     ("\tp:"+ Funcs.modelToString( "\t\t", sets[p] ) ) 
                     :
                     ((counter == 1) ? 
                        ("\tq:"+ Funcs.modelToString( "\t\t", sets[q] )) :
                        ("\tr:"+ Funcs.modelToString( "\t\t", sets[r] )
                        ) 
                     )
                  ) + "\n");
      
    }
    return result;
  }
  
  /**
   * displayModel Simply displays a set and it's status as a model or counter model. Changes "true" to "1" 
   * and "false" to "0" for cleaner out put.
   * 
   * @param     isModel 
   * @param     p The index of the p set.
   * @param     q The index of the q set.
   * @param     sets All possible sets.
   */
  public static String displayModel(boolean isModel, int p, int q, boolean[][] sets){
    String result = "";
    result += ( (( isModel ) ? "Model " : "Countermodel ") + "found at ("+p+","+q+"):")+"\n";
    
    for( int counter = 0; counter < 2; counter++){
      result += ( ((counter == 0) ? 
                     ("\tp:"+ Funcs.modelToString( "\t\t", sets[p] ) ) 
                     :
                     ("\tq:"+ Funcs.modelToString( "\t\t", sets[q] ) ) 
                  )
                ) + "\n";
      
    }
    return result;
  }
  
  /**
   * displayHelp Displays all possible commands. and explains purpose of this program.
   */
  public static void displayHelp(){
    System.out.println( 
                       
                       "===========================================================================\n"
                         +"HELP for CS278 LAB3 by Jason Ivey, NMSU. This lab is meant to generate the\n"
                         +"models and counter models for the for the W1 and W2 of Lab3.\n"
                         +"---------------------------------------------------------------------------\n"
                         +"Commands:\n"
                         +"\t\"help\":\n"
                         +"\t\tDiplays the help info you see right now.\n"
                         +"\t\"w1\":\n"
                         +"\t\tStarts the process to generate models and counter models\n"
                         +"\t\tof W1, will ask for input width, the width can be defined\n"
                         +"\t\tas the number of characters needed to generate width amount\n"
                         +"\t\tof values, this is computed by #ofSets = 2 ^ width.\n"
                         +"\t\"w2\":\n"
                         +"\t\tStarts the process to generate models and counter models\n"
                         +"\t\tof W2, will ask for input width, the width can be defined\n"
                         +"\t\tas the number of characters needed to generate width amount\n"
                         +"\t\tof values, this is computed by #ofSets = 2 ^ width.\n"
                         +"\t\"exit\":\n\t\tUsed to exit the program, you can also just sent blank input to exit.\n"
                         +"===========================================================================");
  }
  
}

class W1{ 
  
  public static boolean[] p, q;
  
  /**
   * W main method of the W1 predicate class, does all logic. 
   * @param     pIn P set input.
   * @param     qIn Q set input.
   * @exception inputDimmensionMismatch
   */
  public static boolean W (boolean[] pIn, boolean qIn[]) throws inputDimmensionMismatch{
    p = pIn;
    q = qIn;
    
    class LogicalFuncs { //Nested class with all needed fucntions
      
      public boolean f1(){ // f3() ^ f4()
        Funcs.debug("f1() = " + f3() + " && " + f4() +" = " + (f3() && f4()) );
        return f3() && f4();
      }
      
      public boolean f2(int x){ // p ^ q
        Funcs.debug("f2() = " + p[x] + " && " + q[x] +" = " + (p[x] && q[x]) );
        return p[x] && q[x];
      }
      
      public boolean f3(){ // Exists p
        
        boolean f3Result = false;
        for( int x = 0; x < p.length; x++){
          f3Result = f3Result || p[x];
        }
        Funcs.debug("f3() = Exists " + Funcs.modelToString("", p) + " = " + f3Result );
        return f3Result;
      }
      
      public boolean f4(){ // exists q
        boolean f4Result = false;
        for( int x = 0; x < p.length; x++){
          f4Result = f4Result || q[x];
        }
        Funcs.debug("f3() = Exists " + Funcs.modelToString("", q) + " = " + f4Result );
        return f4Result;
      }
    };
    LogicalFuncs logicalFuncs = new LogicalFuncs();
    if( p.length != q.length )
      throw new inputDimmensionMismatch("Bad args W1.w a != q");
    
    boolean f2Result = false;
    for( int x = 0; x < p.length; x++){ // Exists f2 
      f2Result = f2Result || logicalFuncs.f2( x );
    }
    Funcs.debug("W = " + logicalFuncs.f1() + " -> " + f2Result +" = " +Funcs.imply( logicalFuncs.f1(), f2Result ));
    return Funcs.imply( logicalFuncs.f1(), f2Result ); // W func
    
  }
  
}

class W2{
  
  public static boolean[] p, q, r;
  /**
   * W main method of the W1 predicate class, does all logic. 
   * @param     pIn P set input.
   * @param     qIn Q set input.
   * @exception inputDimmensionMismatch
   */
  public static boolean W (boolean[] pIn, boolean qIn[], boolean rIn[]) throws inputDimmensionMismatch{
    p = pIn;
    q = qIn;
    r = rIn;
    class LogicalFuncs {
      
      public boolean f1(){
        Funcs.debug("f1 = " + f3() + " && " + f4() + " = " + (f3() && f4()));
        return f3() && f4();
      }
      
      public boolean f2(){
        boolean f2Result = false;
        for( int x = 0; x < p.length; x++)
          f2Result = f2Result || f5(x);
        Funcs.debug("f2 = Exists f5(x) = " + f2Result);
        return f2Result;
      }
      
      public boolean f3(){
        boolean f3Result = true;
        for( int x = 0; x < p.length; x++)
          f3Result = f3Result && ( Funcs.imply(p[x], f6(x) ) );
        Funcs.debug("f3 = All(x) P(x) -> f6(x)" + f3Result);
        return f3Result;
      }
      
      public boolean f4(){
        boolean f4Result = false;
        for( int x = 0; x < p.length; x++)
          f4Result = f4Result || f7(x);
        Funcs.debug("f4 = Exists f7(x) = " + f4Result);
        return f4Result;
      }
      
      public boolean f5(int x){
        Funcs.debug("f5 = " + p[x] + " && " + r[x]);
        return p[x] && r[x];
      }
      
      public boolean f6(int x){
        Funcs.debug("f6 = " + q[x] + " || " + r[x]);
        return q[x] || r[x];
      }
      
      public boolean f7(int x){
        Funcs.debug("f7 = " + q[x] + " && " + r[x]);
        return q[x] && r[x];
      }
      
    };
    
    LogicalFuncs logicalFuncs = new LogicalFuncs();
    Funcs.debug("W = " + logicalFuncs.f1() + " -> " + logicalFuncs.f2() + " = " 
                  + Funcs.imply( logicalFuncs.f1(), logicalFuncs.f2()) );
    boolean w = Funcs.imply( logicalFuncs.f1(), logicalFuncs.f2());
    return w;
  }
}

class Funcs{
  public static final boolean DEBUG = false;
  
  public static String modelToString(String append, boolean[] model){
    String result = append;
    for( boolean itor : model )
      result += ((itor)?"1 ":"0 ");
    return result;
  }
  
  public static void debug( String ... input ){
    if( DEBUG )
    for( String itor : input )
      System.out.println("DEBUG:: " + itor);
  }
  
  public static boolean imply( boolean a, boolean b){
    return !a || b; 
  }
  
  /**
   * gen Generates all posible boolean set for a given width.
   * @param     width The width of the generated sets
   * @returns   
   */
  public static boolean[][] gen( int width ){
    int exponent = Integer.toBinaryString( (int)(Math.pow(2, width-1) )).length();
    boolean[][] definitions = new boolean[(int)Math.pow(2, exponent)][exponent];
    for( int count = 0; count < definitions.length; count++){
      
      char[] countBin = Integer.toBinaryString( count ).toCharArray();
      
      for( int y = 0; y < countBin.length; y++ ){
        definitions[ count ][ exponent - 1 - y] = countBin[ countBin.length - y - 1] == 49;
      }
      
    }
    return definitions;
  }
  
  /**
   * Parse method to check for keywords in input
   * @param     input String input from the command line
   * @exception KeyWordInputException
   * @param     keywords[] String[] of the keywords to check for
   */
  
  public static int[] parse(String input, String[] keywords) throws KeyWordInputException{
    boolean hasMatch = false; //Checks if this input triggered a match
    String processingInput = ""; //Intermediate string
    int[] result; //file result
    String[] foundMatches;
    input = input.toLowerCase();
    
    for ( String keyword : keywords ) {
      if (input.length() != 0 && input.substring( 0 , Math.min(input.length(), keyword.length() ) ).equalsIgnoreCase(keyword.substring( 0 , Math.min(input.length(), keyword.length() ) )  ) ) //main comparison statement
      {
        hasMatch = true;
        processingInput += keyword + " ";
      }
    }
    
    
    if ( !hasMatch ){ //If this input has no match then declare that
      throw new KeyWordInputException("Failed to parse " + input +"\n may not exist in keywords or input chars may be different than ASCII.");
    }
    
    try { 
      foundMatches = processingInput.split(" +"); //Spliting on regular expression space
      result = new int[ foundMatches.length ];
    } catch ( Exception e ) {
      foundMatches = new String[1];
      foundMatches[0] = processingInput.substring(0, processingInput.length() - 1);
      result = new int[0];
    }
    
    for(int x = 0; x < foundMatches.length; x++){
      for( int y = 0; y < keywords.length; y++){
        if( foundMatches[x].equals( keywords[y] ) )
          result[x] = y;
      }
    }
    return result;
  }
}

// Custom exception specific to this lab
class inputDimmensionMismatch extends Exception 
{ 
  public inputDimmensionMismatch(String s) 
  { 
    // Call constructor of parent Exception 
    super(s); 
  } 
} 

// Custom exception specific to this lab
class KeyWordInputException extends Exception{
  public KeyWordInputException(String info){
    super(info);
  }
}
