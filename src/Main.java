/** Shane Peters
 * CS 457 Database Management Systems
 * Final Exam Project
 *
 * Tasks:
 *  Parse SQL Like input query
 *  Run Query on column store data from A.txt, B.txt, C.txt
 *  Print results in table format
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {

    //Class variables
//    String query = "SELECT A1, TS FROM A WHERE A1 = 22;";
//    String query = "SELECT C4 FROM C WHERE ((C2 = 137) OR (C1 = 117 and C3 = 202));";
    String query = "SELECT C4, C2 FROM C WHERE C1 = 117 and C3 = 202;";
    ArrayList<ArrayList<Integer>> A = new ArrayList<ArrayList<Integer>>(); // 2-D ArrayLists to represent the A, B, and C tables
    ArrayList<ArrayList<Integer>> B = new ArrayList<ArrayList<Integer>>(); // Lengths of both dimensions will be determined on the reading from input files
    ArrayList<ArrayList<Integer>> C = new ArrayList<ArrayList<Integer>>();
    static int timeStamp = 0;


    //initialization function
    public Main(){
        readData();
        parseQuery();
    }

    //read data from A.txt, B.txt, C.txt
    //Possible problems when turning in. Ask Dr. Vrbsky!! Where does java search for input files?
    public void readData(){
        //Method Variables
        File fileA = new File("A.txt");
        File fileB = new File("B.txt");
        File fileC = new File("C.txt");
        int numColumnsA;
        int numColumnsB;
        int numColumnsC;
        String currentRowA;
        String currentRowB;
        String currentRowC;
        String delims = "\\s+";



        try{
            //Ask Dr. Vrbsky about input and output because IDE's are all kinds of fucked up
            Scanner sc_a = new Scanner(fileA);
            Scanner sc_b = new Scanner(fileB);
            Scanner sc_c = new Scanner(fileC);

            //Looks at first row to determine #columns
            currentRowA = sc_a.nextLine();
            String[] tokensA = currentRowA.split(delims); //need to cast tokens to integer
            numColumnsA = tokensA.length;

            currentRowB = sc_b.nextLine();
            String[] tokensB = currentRowB.split(delims);
            numColumnsB = tokensB.length;

            currentRowC = sc_c.nextLine();
            String[] tokensC = currentRowC.split(delims);
            numColumnsC = tokensC.length;

            //Create columns for the amount of tokens
            for (int i = 0; i < numColumnsA; i++){
                A.add(new ArrayList<Integer>());
            }
            A.add(new ArrayList<Integer>());

            for (int i = 0; i < numColumnsB; i++){
                B.add(new ArrayList<Integer>());
            }
            B.add(new ArrayList<Integer>());

            for (int i = 0; i < numColumnsC; i++){
                C.add(new ArrayList<Integer>());
            }
            C.add(new ArrayList<Integer>());

            for (int i = 0; i < numColumnsA; i++){
                //System.out.println(Integer.parseInt(tokens[i]));
                A.get(i).add(Integer.parseInt(tokensA[i]));
            }
            A.get(numColumnsA).add(timeStamp);
            timeStamp++;

            for (int i = 0; i < numColumnsB; i++){
                //System.out.println(Integer.parseInt(tokens[i]));
                B.get(i).add(Integer.parseInt(tokensB[i]));
            }
            B.get(numColumnsB).add(timeStamp);
            timeStamp++;

            for (int i = 0; i < numColumnsC; i++){
                //System.out.println(Integer.parseInt(tokens[i]));
                C.get(i).add(Integer.parseInt(tokensC[i]));
            }
            C.get(numColumnsC).add(timeStamp);
            timeStamp++;

            while(sc_a.hasNextLine()){
                currentRowA = sc_a.nextLine();
                tokensA = currentRowA.split(delims);
                for(int i = 0; i < numColumnsA; i++){
                    A.get(i).add(Integer.parseInt(tokensA[i]));
                }
                A.get(numColumnsA).add(timeStamp);
                timeStamp++;
            }

            while(sc_b.hasNextLine()){
                currentRowB = sc_b.nextLine();
                tokensB = currentRowB.split(delims);
                for(int i = 0; i < numColumnsB; i++){
                    B.get(i).add(Integer.parseInt(tokensB[i]));
                }
                B.get(numColumnsB).add(timeStamp);
                timeStamp++;
            }

            while(sc_c.hasNextLine()){
                currentRowC = sc_c.nextLine();
                tokensC = currentRowC.split(delims);
                for(int i = 0; i < numColumnsC; i++){
                    C.get(i).add(Integer.parseInt(tokensC[i]));
                }
                C.get(numColumnsC).add(timeStamp);
                timeStamp++;
            }


            //Prints Table
//            for (int i = 0; i < C.get(0).size(); i++){
//                for (int j = 0; j < C.size(); j++){
//                    System.out.print(C.get(j).get(i)+ " ");
//                }
//                System.out.println("");
//            }

            sc_a.close();
            sc_b.close();
            sc_c.close();


        }catch(FileNotFoundException e){
            System.out.println(System.getProperty("user.dir"));
        }

    }

    //Reads queries from input.txt
    public void readQueries(){

    }

    //Parses the query into tokens so we can figure out what
    //the query is asking the system to do
    public void parseQuery(){
        //Method Variables
        String selectClause = "";
        String fromClause = "";
        String whereClause = "";
        String orderByClause = "";
        String currentClause = "";
        String[] tokens;
        String delims = "\\s+";
        ArrayList<String> select = new ArrayList<String>();
        ArrayList<String> from = new ArrayList<String>();
        ArrayList<String> where = new ArrayList<String>();
        ArrayList<String> orderBy = new ArrayList<String>();
        int selectLoc = Integer.MAX_VALUE, fromLoc = Integer.MAX_VALUE, whereLoc = Integer.MAX_VALUE, orderLoc = Integer.MAX_VALUE;

        //Removes commas from query
        query = query.replace(",","");

        //Splits query into tokens and finds where in the array the keywords are
        tokens = query.split(delims);
        for (int i = 0; i < tokens.length; i++){
            if(tokens[i].equals("SELECT")){selectLoc = i;}
            else if(tokens[i].equals("FROM")){fromLoc = i;}
            else if(tokens[i].equals("WHERE")){whereLoc = i;}
            else if(tokens[i].equals("ORDER BY")){orderLoc = i;}
        }

        //Separate the tokens into the separate clauses denoted by keyword
        for(int i = 0; i < tokens.length; i++){
            if(i < tokens.length && i < orderLoc && i < whereLoc && i < fromLoc){
                selectClause += tokens[i]+ " ";
                select.add(tokens[i]);
            }
            else if(i < tokens.length && i < orderLoc && i < whereLoc){
                fromClause += tokens[i] + " ";
                from.add(tokens[i]);
            }
            else if(i < tokens.length && i < orderLoc){
                whereClause += tokens[i] + " ";
                where.add(tokens[i]);
            }
            else if(i < tokens.length){
                orderByClause += tokens[i] + " ";
                orderBy.add(tokens[i]);
            }
        }

//        System.out.println(selectClause + "Select Length: " + select.size());
//        System.out.println(fromClause + "From Length: " + from.size());
//        System.out.println(whereClause + "Where Length: " + where.size());
//        System.out.println(orderByClause + "Order Length: " + orderBy.size());
        executeQuery(select, from, where, orderBy);


    }

    //Takes info from the tokenized clauses and uses it to execute query
    private void executeQuery(ArrayList<String> s, ArrayList<String> f, ArrayList<String> w, ArrayList<String> o){
        //Method Variables
        ArrayList<String> selectArray = s;
        ArrayList<String> fromArray = f;
        ArrayList<String> whereArray = w;
        ArrayList<String> orderArray = o;
        int sLength = selectArray.size();
        int fLength = fromArray.size();
        int wLength = whereArray.size();
        int oLength = orderArray.size();
        ArrayList<Integer> indexesToGet = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> currentTable = new ArrayList<ArrayList<Integer>>();

        //Decides What table to query
        if(fromArray.get(1).equals("A")){
            currentTable = A;
        }
        else if(fromArray.get(1).equals("B")){
            currentTable = B;
        }
        else if(fromArray.get(1).equals("C")){
            currentTable = C;
        }

//        System.out.println(currentTable.size());

        //Decides which columns to project
        for(int i = 1; i < sLength; i++){
            String currentChar = selectArray.get(i).substring(selectArray.get(i).length() - 1);
            if(currentChar.equals("S")){
                indexesToGet.add(currentTable.size() - 1);
            }
            else if(currentChar.equals("*")){

            }
            else{
                int current = Integer.parseInt(currentChar);
                indexesToGet.add(current - 1);
            }

        }

        //Convert where clause into java boolean expression
        if(wLength > 1){
            convertClause(whereArray);
        }


        //Run Query
//        for(int i = 0; i < currentTable.get(0).size(); i++){
//            for(int j = 0; j < indexesToGet.size(); j++){
//                System.out.print(currentTable.get(indexesToGet.get(j)).get(i) + " ");
//            }
//            System.out.println("");
//        }

        //Determines boolean statement from where clause
    }

    private void convertClause(ArrayList<String> c){
        //Method variables
        ArrayList<String> clause = c;
        String[] comparisonOperators = {"<",">","<=",">=","<>"};

        for(int i = 0; i < clause.size(); i++){
            System.out.println(clause.get(i));
        }
    }

    public void PrintTable(ArrayList<ArrayList<Integer>> T){
        //Prints Table
        for (int i = 0; i < T.get(0).size(); i++){
            for (int j = 0; j < T.size(); j++){
                System.out.print(T.get(j).get(i)+ " ");
            }
            System.out.println("");
        }
    }

    public static void main(String args[]){
        Main main = new Main();
    }


}
