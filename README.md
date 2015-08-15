# Quine-McCluskey
Code for Quine McCluskey method of minimization of boolean expression. 
LANGUAGE USED : JAVA
HOW TO COMPILE AND RUN :  Open the source file using any java IDE (BlueJ , eclipse ,etc) . Compile the code and run .
                          There is primarily one input the number of variables . The number of minterms and the minterms
                          are randomly generated .
                          ( Code tested in BlueJ IDE and eclipse on Windows 8 ) 
                          
ABOUT THE CODE : The given JAVA code implements the Quine Mccluskey method for simplifying boolean expressions . I have 
used mainly 2D arrays mainly to implement the method along with some function calls . A simple overview of how the code 
works is given below :-
HOW IT WORKS :- First store the number of variables and number of minterms . Accordingly declare 2 two dimensional arrays 
each of size (NumberOfMinterms)C2 X NoOfVariables . While the first one holds the binary forms of the minterms entered the
other one hold the binary forms of the minterms with bit difference of 1 . Each time the contents of second matrix is 
copied to the first keeping track of the minterms which are not carried forward to next pass. Such minterms/combinations 
are stored in another matrix which is meant for storing the Prime Implicants .
 After storing the prime implicants the PI coverage chart is made , using yet another 2D array . The dimension of this 
matrix is (No. of PI )X(No of minterm) . Initialise the matrix with 0 symbolising empty . For each PI put 1(meaning a 
cross - X) under the correct minterm . We now check columnwise and presense of single 1 in  a column symbolises an
essential PI which is marked . All the columns of an essential PI is deleted ( -1 to symbolise deleted spaces ) .Repeated 
checking is made to remove row and column dominance . After that the row which has a 1 is taken in final expression for 
the simplified expression .
METHOD DESCRIPTIONS :- Descriptions of the method/function used in my code is as follows 
1 . initialise(int a[][],int val) - initialise matrix a[][] with the value in val 
2 . initialise_single(int a[],int val) - initialise array a[] with the value in val
3 . decode(int a[][],int row,int nvar,char bitvar[])-will convert the final essential pi and pi into switching variables
4 . match(int min,int a[][],int row,int nvar) - this method identifies the decimal notation of the PI and essential PI
     from their binary notations 
5 . the main() method takes all input computes PI and essential PI , removes row and column dominance and outputs the 
    minterms in final expression . 
     
LIMITATIONS OF THE CODE : 
1 . The primary limitation of the code is that it does not consider semi-cyclic conditions in the PI coverage chart .
2 . The use of array has a restriction on the number of minterms we can consider according to number of variables in the 
    problem . 
    EXAMPLE : If we are working on 8 varables the code can give results with upto a maximum of 210 minterms , for 10 
              variables it works fine for 700 minterms . Beyond a certain limit the code can throw a ArrayIndexOutOfBounds
              Exception .

Explanation :
1.  Random rand=new Random(32); => The use of roll number (ie , 32 ) while creating the Random object rand has a limitation
                                   of generation of identical integers every time you run the code , that is , every time 
                                   you give 10 variable as input you get the same set of minterms . To remove this situation 
                                   simply remove the argument (ie , 32) while creating the Random object.  
2.  nmin=(int)rand.nextInt((int)(.75*(Math.pow(2,nvar)-1))); => Generates number of minterm within the limit set by
                            the argument . The limit is 75% of permitted value to prevent ArrayIndexOutOfBounds
                            Exception .                              
PLEASE NOTE :- I have commented out the portions of the code that display the binary forms of the minterms entered , 
  the tables generated , the binary forms of PIs ,essential PIs and the PI coverage chart before and after removing 
  dominance . This has been done to keep a cleaner output terminal . If you wish to display them you can do so un-
  commenting the following lines :-
  LINES 72-79 : Displays the binary forms of the minterms 
  LINES 141-155 : Displays the table in every pass 
  LINES 163-175 : Displays the binary forms of the PI
  LINES 189-203 : Displays the PI coverage chart
  LINES 223-238 : Displays the binary forms of essential PI
  LINES 260-274 : Displays the PI chart after removing essential PI
  LINES 361-375 : Displays the PI chart after removing row and column dominance

