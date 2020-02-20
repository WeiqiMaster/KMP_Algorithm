/**
 * By Peter Pan from Mr. Kirnic ICS4U class.
 * 2018/5/4
 * KMP Algorithm
 * The main class 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class KMPalgorithms
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Creating the string with 10,000,000 letters...... Please wait 5 seconds.");
		
		//outputArray(countOverlap(pattern));
		
		//Create a string with 10,000,000 random characters. 
		String parameter="qwertyuiopasdfghjklzxcvbnm";
		Random random=new Random();
		StringBuffer sBuffer=new StringBuffer();
		for (int i = 0; i <10000000; i++)
		{//insert a random letter from the parameter into the StringBuffer 10,000,000 times.
			int randomNumber=random.nextInt(26);
			sBuffer.append(parameter.charAt(randomNumber));
		}
		String text=sBuffer.toString();//Convert to string
		
		//print the string into a '.txt' file.
		File file=new File("randomText.txt");
		try
		{
			FileWriter fWriter=new FileWriter(file);
			BufferedWriter bWriter=new BufferedWriter(fWriter);
			bWriter.write(sBuffer.toString());
			bWriter.close();
		} catch (Exception e)
		{
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		//Ask user to input the pattern.
		while (true)
		{
			System.out
				.println("Please input the string(pattern) you want to search: (Input 0 to exit the program)");
			String pattern = sc.next();
			if (pattern.equals("0"))
			{
				System.out.println("You have exit the program.");
				break;//quit the program
			}
			
			int result = KMP(pattern, text);
			if (result == -1)
			{//if the function returns -1, then there is no such string in the text.
				System.out.println("There is no such string in the text");
			} else
			{
				System.out.println("The pattern exists in the text. The index is: "+result);
			}
		}
	
		sc.close();//close the scanner.
		
	}
	
	public static int KMP(String pattern, String text)
	{
		int[] overlay=countOverlap(pattern);//get the overlap of the pattern.
        int textIndex=0;
        int patternIndex=0;
        while(textIndex < text.length() && patternIndex < pattern.length())
        {
        	
            if(text.charAt(textIndex) == pattern.charAt(patternIndex))//check if the current char of pattern  matches the current char of the text.
            {
            	if (patternIndex==pattern.length()-1)//check if the patternIndex has been the last one.
				{
					break;//get out of the while loop.
				}
            	textIndex++;
                patternIndex++;//move the index of text and pattern.
            }else
            {
                if(patternIndex!=0)
                {//patternIndex is not 0.
                	patternIndex = overlay[patternIndex-1];
                }else
                {//patternIndex is 0.
                	textIndex++;
                }
            }
        }
        
            if(textIndex == text.length()&&patternIndex!=pattern.length()-1)
            {
                return -1;//If there is no such string until the end of the text, then return -1.
            }else 
            {
				return textIndex-patternIndex;// return the index where the first letter of the pattern appears in the text. 
			}
        
    }
	
	private static int[] countOverlap(String stringPattern)//computing the overlap array of the pattern
	{
		int[] overlay = new int[stringPattern.length()];
		overlay[0] = 0;//the first element is always 0.
		int j = 0;//the first parameter
		int i = 1;//the start index of 
		while (i < stringPattern.length())
		{
			if (stringPattern.charAt(j) == stringPattern.charAt(i))
			{//the letter which has an index of j is equal to the letter which has an index of i.
				overlay[i] = j + 1;
				j++;
				i++;
			} else//the letter which has an index of j is not equal to the letter which has an index of i.
			{
				while (j>=1&&stringPattern.charAt(j) != stringPattern.charAt(i))
				{
					//make the j equals the value of the element of the overlay array which has an index of j-1, 
					//until the letter which has an index of j is equal to the letter which has an index of i.
					j=overlay[j-1];
				}
				overlay[i]=j+1;
				if (j==0&&stringPattern.charAt(j) != stringPattern.charAt(i))
				{
					//when j is equal to 0, and the letter which has an index of j is not equal to the letter which has an index of i.
					overlay[i]=0;
					i++;
				}
			}
		}
		return overlay;//return the array.
	}
}