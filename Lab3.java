import java.util.Scanner;

public class Lab3{
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
                    ); 
    boolean sentinel = false;
    while( !sentinel ){
      System.out.println("Enter help for help or input: ");
      String inputString = input.nextLine();
      if( inputString.trim().length() == 0 ){
        sentinel = true;
        continue;
      }
      try{
        commands = Funcs.parse(inputString, keywords);
        for( int itor : commands ){
          switch( itor ){
            
            case 0: 
              displayHelp();
              break;
              
            case 1: 
              runW1(input);
              break;
              
            case 3: 
              sentinel = true;
              continue;
          }
        }
      } catch ( KeyWordInputException kwie ){
        System.err.println(kwie); 
      }
    }
    System.out.println("Thanks for running LAB3, have a nice day!\n\tLAB3 EXIT");
  }
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
    String buffer = "";
    int width = Integer.parseInt(inputString.trim());
    boolean[][] allSets = Funcs.gen( width );
    System.out.println("Loading " + (int)(Math.pow(2,width)*Math.pow(2,width)) + 
                       ((width > 3) ? " sets, this may take a long while." : " sets." ));
    for(int x = 0; x < allSets.length; x++)
      for(int y = 0; y < allSets.length; y++){
      try{
        Funcs.debug("Trying sets:\n\tx:" + Funcs.modelToString("\t\t",allSets[x])
                      +"\n\ty:"+Funcs.modelToString("\t\t",allSets[y]));

        if( W1.W(allSets[x], allSets[y]) )
          buffer += displayModel(true, x, y, allSets);
        else
          buffer += displayModel(false, x, y, allSets);
      } catch ( inputDimmensionMismatch idm ){
        System.out.println( idm ); 
      }
    }
    System.out.print(buffer);
    
  }
  
  public static String displayModel(boolean isModel, int x, int y, boolean[][] sets){
    String result = "";
    result += ( (( isModel ) ? "Model " : "Countermodel ") + "found at ("+x+","+y+"):")+"\n";
    
    for( int counter = 0; counter < 2; counter++){
      result += ( ((counter == 0) ? 
                             ("\tx:"+ Funcs.modelToString( "\t\t", sets[x] ) ) 
                             :
                             ("\ty:"+ Funcs.modelToString( "\t\t", sets[y] ) ) 
                          )
                        ) + "\n";
      
    }
    return result;
  }
  
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
  public static boolean W (boolean[] pIn, boolean qIn[]) throws inputDimmensionMismatch{
    p = pIn;
    q = qIn;
    
    class LogicalFuncs {
      
    };
    boolean w = Funcs.imply( f1(), 
       
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