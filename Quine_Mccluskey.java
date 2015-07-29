/* This code implements the Quine Mccluskey method (Tabular Form) for simplifying boolean expressions 
 *                     Author : ARKA PRAVA BASU 
 *                     DEPT OF COMPUTER SC. AND ENGINEERING
 *                     ROLL NO - 13/CS/32
 *                     NIT DURGAPUR
 */
import java.io.*;
import java.util.*;
public class Quine_Mccluskey
{
    public static void initialise(int a[][],int val)
    {
        int i,j;
        for(i=0;i<a.length;i++)
        for(j=0;j<a[i].length;j++)
        a[i][j]=val;
    }
    public static void initialise_single(int a[],int val)
    {
        int i;
        for(i=0;i<a.length;i++)
        a[i]=val;
    }
    public static void main(String args[])throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        Random rand=new Random(32);// generate random numbers according to roll ; seed is my roll number ie 32
        int min[],nmin,nvar,i,j,k,a[][],x,y,pos=0,b[][],flag=0,count=0,c,pi[][],flag2=0,c2=0,checker[],flag1=0,dash[],c1=0,c3=0,decimal,no9;
        boolean check=false;
        System.out.println("enter no. of switching variables");
        nvar=Integer.parseInt(br.readLine());
        System.out.println("permitted minterms 0 to "+(int)(Math.pow(2,nvar)-1));
        nmin=(int)rand.nextInt((int)(.75*(Math.pow(2,nvar)-1)));
        System.out.println("The number of minterms is :- "+nmin);
        min=new int[nmin];
         for(i=0;i<nmin;)
        {
            c1=0;
            min[i]=(int)rand.nextInt((int)(Math.pow(2,nvar)));
            for(j=i-1;j>=0;j--)
            if(min[i]==min[j])
            c1++;
            if(c1==0)
            i++;
        }
        a=new int[nmin*(nmin+1)/2][nvar];
        b=new int[nmin*(nmin+1)/2][nvar];
        pi=new int[nmin*(nmin+1)/2][nvar];//this will hold the prime implicants
        checker=new int[nmin*(nmin+1)/2];
        initialise(a,-1);//initialises the matrix a with all -1
        for(i=0;i<nmin;i++)
        for(j=0;j<nvar;j++)
        a[i][j]=0;
        //store binary form of each minterm in matrix a[][]
        for(i=0;i<nmin;i++)
        {
            x=min[i];
            pos=nvar-1;
            while(x>0)
            {
                a[i][pos]=x%2;
                pos--;
                x/=2;
            }
        }
        System.out.println("The minterms entered :-");
        for(i=0;i<nmin;i++)
        {
            System.out.print(min[i]+" ");
        }
        System.out.println("\n----------------------------------------------------------------------------------------------------");
        /*System.out.println("binary forms of the minterms entered");
        for(i=0;i<nmin;i++)
        {
            for(j=0;j<nvar;j++)
            System.out.print(a[i][j]);
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");*/
        while(true)
        {
            count=0;
            flag=0;// flag is row contrller for matrix b
            initialise(b,-1);//creating new empty matrix b at the beginning of every pass
            initialise_single(checker,-1);
            for(i=0;i<a.length;i++)
            {
                if(a[i][0]==-1)
                break;
                for(j=i+1;j<a.length;j++)
                {
                    c=0;
                    if(a[j][0]==-1)
                    break;
                    for(k=nvar-1;k>=0;k--)
                    if(a[i][k]!=a[j][k])
                    {
                        pos=k;
                        c++;
                    }
                    if(c==1)
                    {
                        count++;
                        checker[i]++;
                        checker[j]++;
                        for(k=nvar-1;k>=0;k--)
                        b[flag][k]=a[i][k];
                        b[flag][pos]=9;
                        flag++;
                    }
                }
            }
            for(j=0;j<i;j++)
            {
                if(checker[j]==-1)
                {
                    for(k=0;k<nvar;k++)
                    pi[flag2][k]=a[j][k];
                    c3=0;
                    //now we will check if there is any repetation of pi s ; if repetation is found we will ignore 
                    for(x=flag2-1;x>=0;x--)
                    {
                        c1=0;
                        for(y=0;y<nvar;y++)
                        {
                            if(pi[x][y]!=pi[flag2][y])
                            c1++;
                        }
                        if(c1==0)
                        {
                            c3++;
                            break;
                        }
                    }
                    if(c3==0)
                    flag2++;
                }
            }
            if(count==0)//if in a table there is no term carried forward then we will stop
            break;//count 0 signifies that no elements are combined to move to next pass so process will terminate
            /*for(i=0;i<b.length;i++)
            {
                if(b[i][0]==-1)
                break;
                for(j=0;j<nvar;j++)
                {
                    if(b[i][j]==9)
                    System.out.print("_");
                    else
                    System.out.print(b[i][j]);
                }
                System.out.println();
            }
            System.out.println("------------------------------------------------------------");
            //this will display the new table created in every pass*/
            for(i=0;i<b.length;i++)
            
                for(j=0;j<b[i].length;j++)
                a[i][j]=b[i][j];
            //copy the matrix b to a so that b is ready to be initialised at the beginning of every pass
            flag1++;
        }
        /*System.out.println("THE PRIME IMPLICANTS ARE ( BINARY FORMS ) :-");
        for(i=0;i<flag2;i++)
        {
            for(j=0;j<nvar;j++)
            {
                if(pi[i][j]==9)
                System.out.print("_");
                else
                System.out.print(pi[i][j]);
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------");*/
        dash=new int[nvar];//this will store the value of dash of each pi
        initialise_single(dash,-1);
        a=new int[flag2][nmin];//this will now hold the pi coverage table 
        initialise(a,0);
        for(i=0;i<flag2;i++)
        {
            for(j=0;j<nmin;j++)
            {
                check=match(min[j],pi,i,nvar);
                if(check==true)
                a[i][j]=1;
            }    
        }
        /*System.out.println("THE PI COVERAGE CHART ");
        for(i=0;i<nmin;i++)
        System.out.print(min[i]+"\t");
        System.out.println();
        for(i=0;i<a.length;i++)
        {
            for(j=0;j<nmin;j++)
            {
                if(a[i][j]==1)
                System.out.print((char)(a[i][j]+87)+"\t");
                else
                System.out.print(" "+"\t");
            }
            System.out.println();
        }*/
        checker=new int[flag2];
        dash=new int[nmin];
        count=0;
        initialise_single(checker,-1);
        initialise_single(dash,-1);
        for(j=0;j<nmin;j++)
        {
            count=0;
            for(i=0;i<flag2;i++)
            {
                if(a[i][j]==1)
                {
                    pos=i;
                    count++;
                }
            }
            if(count==1)
            checker[pos]++;
        }
       /* System.out.println("THE ESSENTIAL PRIME IMPLICANTS ARE (BINARY FORMS):-");
        for(i=0;i<flag2;i++)
        {
            if(checker[i]!=-1)
            {
                for(j=0;j<nvar;j++)
                {
                    if(pi[i][j]==9)
                    System.out.print("_");
                    else
                    System.out.print(pi[i][j]);
                }
                System.out.println();
            }   
        }
        System.out.println("--------------------------------------------------------------------------------------------------");*/
        for(i=0;i<flag2;i++)
        {
            if(checker[i]!=-1)
            {
                for(j=0;j<nmin;j++)
                {
                    if(a[i][j]==1)
                    dash[j]++;
                }
                for(j=0;j<nmin;j++)
                a[i][j]=-1;
            }
        }
        for(j=0;j<nmin;j++)
        {
            if(dash[j]!=-1)
            {
                for(i=0;i<flag2;i++)
                a[i][j]=-1;
            }
        }
        /*System.out.println("THE PI COVERAGE CHART  after eliminating essential primes");
        for(i=0;i<nmin;i++)
        System.out.print(min[i]+"\t");
        System.out.println();
        for(i=0;i<a.length;i++)
        {
            for(j=0;j<nmin;j++)
            {
                if(a[i][j]==1)
                System.out.print((char)(a[i][j]+87)+"\t");
                else
                System.out.print(" "+"\t");
            }
            System.out.println();
        }*/
        while(true)
        {
            count=0;
            //remove column dominance
            for(j=0;j<nmin;j++)
            {
                for(k=j+1;k<nmin;k++)
                {
                    c1=0;
                    c2=0;
                    c3=0;
                    for(i=0;i<flag2;i++)
                    {
                        if(a[i][j]==1 && a[i][k]==1)
                        c1++;
                        if(a[i][j]==1 && a[i][k]==0)
                        c2++;
                        if(a[i][j]==0 && a[i][k]==1)
                        c3++;
                    }
                    if(c2>0 && c3>0)
                    {
                        break;
                    }
                    if(c1>0 && c2>0 && c3==0)
                    {
                        for(no9=0;no9<flag2;no9++)
                        a[no9][j]=-1;
                        count++;
                    }
                    if(c1>0 && c3>0 && c2==0)
                    {
                        for(no9=0;no9<flag2;no9++)
                        a[no9][k]=-1;
                        count++;
                    }
                    if(c1>0 && c2==0 && c3==0)
                    {
                        for(no9=0;no9<flag2;no9++)
                        a[no9][j]=-1;
                        count++;
                    }
                }
            }
            //remove row dominance
            for(i=0;i<flag2;i++)
            {
                for(j=i+1;j<flag2;j++)
                {
                    c1=0;
                    c2=0;
                    c3=0;
                    for(k=0;k<nmin;k++)
                    {
                        if(a[i][k]==1 && a[j][k]==1)
                        c1++;
                        if(a[i][k]==1 && a[j][k]==0)
                        c2++;
                        if(a[i][k]==0 && a[j][k]==1)
                        c3++;
                    }
                    if(c2>0 && c3>0)
                    break;
                    if(c1>0 && c2>0 && c3==0)
                    {
                        for(no9=0;no9<nmin;no9++)
                        a[j][no9]=-1;
                        count++;
                    }
                    if(c1>0 && c3>0 && c2==0)
                    {
                        for(no9=0;no9<nmin;no9++)
                        a[i][no9]=-1;
                        count++;
                    }
                    if(c1>0 && c2==0 && c3==0)
                    {
                        for(no9=0;no9<nmin;no9++)
                        a[j][no9]=-1;
                        count++;
                    }
                }
            }
            if(count==0)//if there is no more row or column dominance we will stop
            break;//count =0 signifies there is no existing column or row dominance so process will terminate 
        }
        /*System.out.println("pi chart after removing dominance");
        for(i=0;i<nmin;i++)
        System.out.print(min[i]+"\t");
        System.out.println();
        for(i=0;i<a.length;i++)
        {
            for(j=0;j<nmin;j++)
            {
                if(a[i][j]==1)
                System.out.print((char)(a[i][j]+87)+"\t");
                else
                System.out.print(" "+"\t");
            }
            System.out.println();
        }*/
        for(i=0;i<a.length;i++)
        {
            for(j=0;j<nmin;j++)
            if(a[i][j]==1)
            checker[i]++;
        }
        char bitvar[]=new char[nvar];//storing the switching variables according to number of minterms
        for(i=0;i<nvar;i++)
        bitvar[i]=(char)(65+i);
        System.out.print("in this problem the switching variables are :- ");
        for(i=0;i<nvar;i++)
        System.out.print(bitvar[i]+" ");
        System.out.println();
        System.out.println("The minterms in the final simplified expression is :");
        for(i=0;i<flag2;i++)
        {
            if(checker[i]!=-1)
            System.out.println(decode(pi,i,nvar,bitvar));
        }
    }
    public static String decode(int a[][],int row,int nvar,char bitvar[])//will convert the final essential pi and pi into switching variables
    {
        int i;
        String s="";
        for(i=0;i<a[row].length;i++)
        {
            if(a[row][i]==9)
            continue;
            else if(a[row][i]==1)
            s+=bitvar[i];
            else
            s+=bitvar[i]+"'";
        }
        return s;
    }
    public static boolean match(int min,int a[][],int row,int nvar)//this will identify the prime implicants with the minterms
    {
        int b[]=new int[nvar],i=nvar-1,c=0;
        initialise_single(b,0);
        while(min>0)
        {
            b[i]=min%2;
            min/=2;
            i--;
        }
        for(i=0;i<nvar;i++)
        {
            if(a[row][i]==9)
            continue;
            if(a[row][i]!=b[i])
            c++;
        }
        if(c==0)
        return true;
        return false;
    }
}