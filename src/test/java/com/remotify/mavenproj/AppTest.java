package com.remotify.mavenproj;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
    	
      	String inputDir1 = System.getProperty("user.dir") + "\\DSV input 1.txt";
    	String inputDir2 = System.getProperty("user.dir") + "\\DSV input 2.txt";

    	String delimiter1 = ",";
    	String delimiter2 = "|";
    	
    	ByteArrayOutputStream output = new ByteArrayOutputStream();
    	PrintStream ps = new PrintStream(output);
    	PrintStream past = System.out;
    	
		try (Stream<String> stream = Files.lines(Paths.get(inputDir1))) {
			
			Iterator<String> it = stream.iterator();
			int c = 0;
			String headers = "";
			while(it.hasNext()) {
				String s = it.next();		
				
			    String[] header = headers.split("\\"+delimiter1);  
			    
			    String[] lineString = s.split("\\"+delimiter1);
			    
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
						    			  i = headerValueHolder + delimiter1 + i;
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
			

			assertEquals("{\"firstName\": \"Wolfgang\",\"middleName\": \"Amadeus\",\"lastName\": \"Mozart\",\"gender\": \"Male\",\"dateOfBirth\": \"1756-01-27\",\"salary\": 1000}\r\n"
					+ "{\"firstName\": \"Albert\",\"lastName\": \"Einstein\",\"gender\": \"Male\",\"dateOfBirth\": \"1955-04-18\",\"salary\": 2000}\r\n"
					+ "{\"firstName\": \"Marie, Salomea\",\"middleName\": \"Skłodowska |\",\"lastName\": \"Curie\",\"gender\": \"Female\",\"dateOfBirth\": \"1934-07-04\",\"salary\": 3000}\r\n"
					+ "",output.toString());
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	ByteArrayOutputStream output2 = new ByteArrayOutputStream();
    	PrintStream ps2 = new PrintStream(output2);
    	PrintStream past2 = System.out;
    	
		try (Stream<String> stream = Files.lines(Paths.get(inputDir2))) {
			
			Iterator<String> it = stream.iterator();
			int c = 0;
			String headers = "";
			while(it.hasNext()) {
				String s = it.next();		
				
			    String[] header = headers.split("\\"+delimiter2);  
			    
			    String[] lineString = s.split("\\"+delimiter2);
			    
				if(c==0) {
					headers = s;
				} else {
				    int headerCounter = 0;
				    String headerValueHolder = "";
				    int doubleQuoteCounter = 0;
				    System.setOut(ps2);
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
						    			  i = headerValueHolder + delimiter2 + i;
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
				    System.setOut(past2);
				}
			    
			    

			    c++;
			}
			
			System.out.print(output2.toString());
			

			assertEquals("{\"firstName\": \"Wolfgang\",\"middleName\": \"Amadeus\",\"lastName\": \"Mozart\",\"gender\": \"Male\",\"dateOfBirth\": \"1756-01-27\",\"salary\": 1000}\r\n"
					+ "{\"firstName\": \"Albert\",\"lastName\": \"Einstein\",\"gender\": \"Male\",\"dateOfBirth\": \"1955-04-18\",\"salary\": 2000}\r\n"
					+ "{\"firstName\": \"Marie, Salomea\",\"middleName\": \"Skłodowska |\",\"lastName\": \"Curie\",\"gender\": \"Female\",\"dateOfBirth\": \"1934-07-04\",\"salary\": 3000}\r\n"
					+ "",output2.toString());
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
