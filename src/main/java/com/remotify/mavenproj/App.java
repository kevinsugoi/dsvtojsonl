package com.remotify.mavenproj;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	DsvToJsonConvert();
    }

    static void DsvToJsonConvert(){
    	
    	Scanner scanner = new Scanner(System.in);
    	System.out.print("Enter the file (file location + filename): ");
    	String filename = scanner.nextLine();

    	System.out.print("Enter the delimiter: ");
    	String delimiter = scanner.nextLine();
    	System.out.flush();
//    	File file = new File(filename)

    	ByteArrayOutputStream output = new ByteArrayOutputStream();
    	PrintStream ps = new PrintStream(output);
    	PrintStream past = System.out;
 
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			
			Iterator<String> it = stream.iterator();
			int c = 0;
			String headers = "";
			while(it.hasNext()) {
				String s = it.next();		
				
			    String[] header = headers.split("\\"+delimiter);  
			    
			    String[] lineString = s.split("\\"+delimiter);
			    
				if(c==0) {
					headers = s;
				} else {
				    int headerCounter = 0;
				    String headerValueHolder = "";
				    int doubleQuoteCounter = 0;
				    System.setOut(ps);
				    for (String i : lineString) {
					    for (int ch = 0+headerCounter; ch <= header.length; ch++) {
					      if(ch==0 && doubleQuoteCounter==0) {
					    	  System.out.print("{");
					      }
					      
					      if(!i.equals("")) {

					    	  if(header[ch].equals("firstName") || header[ch].equals("middleName") || header[ch].equals("lastName")) {
				    		  int indexOfN = i.indexOf('\"');
					    		  
					    		  if(indexOfN!=-1) {
					    			  if(doubleQuoteCounter==0) {
						    			  doubleQuoteCounter++;
						    			  headerCounter--;
						    			  headerValueHolder = i;
					    			  } else {
						    			  i = headerValueHolder + delimiter + i;
						    			  i = i.replace("\"", "");
						    			  doubleQuoteCounter = 0;
					    			  }
					    		  }

					    	  }
					    	  
					    	  if(header[ch].equals("dateOfBirth")) {
					    		  int indexOfI = i.indexOf('/');
					    		  if(indexOfI!=-1)
					    			  i = i.replace("/", "-");
					    		
					    		  if(i.substring(2,3).equals("-"))
					    			  i = i.substring(6) + "-" + i.substring(3,5) + "-" + i.substring(0,2);
					    	  }
					    	  
					    	  if(!header[ch].equals("salary") && doubleQuoteCounter==0) {
						    	  System.out.print("\""+header[ch]+"\": \""+i+"\"");  
					    	  }else if(header[ch].equals("salary")){
						    	  System.out.print("\""+header[ch]+"\": "+i);
					    	  }
					    	  
					    	  if(ch!=header.length-1 && doubleQuoteCounter==0)
					    		  System.out.print(",");
					    		  
					      }
					      if(ch==header.length-1) {
						      System.out.println("}");					    	  
					      }
				    	  break;
					    }
					    	headerCounter++;
				    	}	
				    System.out.flush();
				    System.setOut(past);				
				}			    

			    c++;
			}
			System.out.print(output.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (FileWriter file = new FileWriter("output.jsonl")){
			file.write(output.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
}
