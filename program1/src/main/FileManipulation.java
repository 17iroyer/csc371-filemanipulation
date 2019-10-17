/**
 * CSC371
 * Program Assignment 1
 * Ian Royer
 * 1/30/19
 */

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileManipulation {
  
  /**
   * Both the file Scanner and input Scanner for file and user input
   * Also, the desired input file (in this case fun.dat)
   */
  private static Scanner fileScan;
  private static Scanner userScan; 
  private static File fun = new File("/home/ir6921/csc371/program1/fun.dat");

  /**
   * Prompts user for input on the desired action,
   * then calls the appropriate method for execution
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    /**
     * Initializes the file and user scanners
     */
    fileScan = new Scanner(fun);
    userScan = new Scanner(System.in);
    
    System.out.println("Would you like to add an entry, or modify an existing one?");
    System.out.print("Enter 1 for add, 2 for modify: ");
    int choice = userScan.nextInt();
    
    switch(choice) {
    case 1:
      newEntry();
      break;
    case 2:
      editEntry();
      break;
    default:
      System.out.println("Invalid entry.");
      System.exit(0);
      break;
    }
  }
  
  /**
   * Takes care of making a new entry into our file
   * @param fileScan scanner for the selected file
   * @param userScan scanner for user input
   * @throws IOException 
   */
  public static void newEntry() throws IOException {
    /**
     * Strings for the name, age and time for the new entry
     * Also, a new file writer so we an add to the current file
     */
    String name, age , finalTime;
    FileWriter fw = new FileWriter(fun, true);
    
    name = getName();
    age = getAge();
    finalTime = getTime();
    
    fw.write(name + " " + age + " " + finalTime + "\n");
    fw.close();
  }
  
  /**
   * Takes care of editing existing entries in out file
   * @param fileScan scanner for the selected file
   * @param userScan scanner for the user input
   * @throws IOException 
   */
  public static void editEntry() throws IOException {
    /**
     * desired entry number to be edited, and a count variable for the loops
     * String to store the updated line after editing
     * String to store the file contents while the new entry is being added
     */
    int entryNum, count;
    String updatedLine = "";
    String masterString = "";
    FileWriter fw = new FileWriter(fun, true);
    count = 0;
    
    System.out.print("Enter the entry number to edit: ");
    entryNum = userScan.nextInt();
    
    while(count <= entryNum-2) {
      try {
        fileScan.nextLine();
      } catch(NoSuchElementException e) {;
        System.out.println("There is no such entry.");
        return;
      }
      count++;
    }
    String selectedLine = fileScan.nextLine();
    System.out.println(selectedLine);
    System.out.println("What part of the entry would you like to change?");
    System.out.println("Enter 1 for name, 2 for age, 3 for time: ");
    int choice = userScan.nextInt();
    
    switch(choice) {
    case 1:
      updatedLine = editName(selectedLine);
      break;
    case 2:
      updatedLine = editAge(selectedLine);
      break;
    case 3:
      updatedLine = editTime(selectedLine);
      break;
    default:
      System.out.println("Invalid choice.");
      System.exit(0);
      break;
    }
    
    count = 0;
    fileScan.close();
    fileScan = new Scanner(fun);
    while(count < entryNum-1 && fileScan.hasNextLine()) {
      masterString += fileScan.nextLine();
      count++;
    }
    masterString += updatedLine;
    fileScan.nextLine();
    while(fileScan.hasNextLine()) {
      masterString += fileScan.nextLine();
    }
    
    int start = 0, end = 20;
    fw.close();
    fw = new FileWriter(fun, false);
    while(start < masterString.length()) {
      fw.write(masterString.substring(start, end) + "\n");
      start += 20;
      end += 20;
    }
    
    fw.close();
  }
  
  /**
   * Edits the current line with the new name
   * @param selectedLine to be edited
   * @return the corrected line
   */
  private static String editName(String selectedLine) {
    String newName = getName();
    
    selectedLine = selectedLine.substring(10, 20);
    selectedLine = newName + selectedLine;
    
    return selectedLine;
  }

  /**
   * Edits the current line with the new age
   * @param selectedLine to be edited
   * @return the corrected line
   */
  private static String editAge(String selectedLine) {
    String newAge = getAge();
    
    String firstPart = selectedLine.substring(0, 11);
    String secondPart = selectedLine.substring(14, 20);
    selectedLine = firstPart + newAge + secondPart;
    
    return selectedLine;
  }

  /**
   * Edits the current line with the new time
   * @param selectedLine to be edited
   * @return the corrected line
   */
  private static String editTime(String selectedLine) {
    String newTime = getTime();
    
    selectedLine = selectedLine.substring(0, 15);
    selectedLine = selectedLine + newTime;
        
    return selectedLine;
  }
  
  /**
   * Gets a name from the user and properly formats is
   * @return formatted name
   */
  private static String getName() {
    /**
     * String to store the name that will be entered
     */
    String name;
    
    System.out.print("Please enter a name (10 character maximum, no spaces): ");
    name = userScan.next();
    if(name.length() > 10) {
      name = name.substring(0,  10);
    }
    if(name.length() < 10) {
      while(name.length() != 10) {
        name = name + " ";
      }
    }
    
    return name;
  }
  
  /**
   * Gets age from user and properly formats it
   * @return formatted age
   */
  private static String getAge() {
    /**
     * String to store the age that will be entered
     */
    String age;
    
    System.out.print("Please enter an age (3 character maximum): ");
    age = userScan.next();
    if(age.length() > 3) {
      age = age.substring(0,  2);
    }
    if(age.length() < 3) {
      while(age.length() != 3) {
        age = age + " ";
      }
    }
    
    return age;
  }
  
  /**
   * Gets input from the user and formats time correctly
   * @return correctly formatted time
   */
  private static String getTime() {
    /**
     * Strings to store the hours and minutes of the desired time
     */
    String time1, time2;
    
    System.out.println("Please enter a time (formatted ##:##)");
    System.out.print("Enter hours: ");
    time1 = userScan.next();
    if(time1.length() > 2) {
      time1 = time1.substring(0,  1);
    } else if(time1.length() < 2) {
      while(time1.length() != 2) {
        time1 = "0" + time1;
      }
    }
    
    System.out.print("Enter Minutes: ");
    time2 = userScan.next();
    if(time2.length() > 2) {
      time2 = time2.substring(0,  1);
    } else if(time2.length() < 2) {
      while(time2.length() != 2) {
        time2 = "0" + time2;
      }
    }
    
    return(time1 + ":" + time2);
  }
}
