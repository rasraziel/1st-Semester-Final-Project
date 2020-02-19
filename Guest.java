import java.util.*;
import java.io.*;

public class Guest extends Person{
   
   //constructor
   public Guest(){}
   
   
   private ArrayList<Guest> guestList;
  
   
   //reads data from file and stores in ArrayList<Guest>
   public void setGuestList() throws FileNotFoundException {
      ArrayList<Guest> guestList = new ArrayList<Guest>();
      Scanner input = new Scanner(new File("guest.txt"));
      String addr = "";
      while (input.hasNext()) {
         Guest guest = new Guest();
         guest.setPersonID(input.next());
         guest.setSalutation(input.next());
         guest.setFirstName(input.next());
         guest.setLastName(input.next());
         guest.setTelNo(input.next());
         input.nextLine(); //Consumes the /n character so the user can give an address with spaces 
         String[] a = input.nextLine().split(" ");
         for(int i = 0; i < a.length; i++){
            addr += a[i];
         }
         guest.setAddress(addr);
         guest.setEmailAddress(input.next());
         guestList.add(guest);
      }
      this.guestList = guestList;
   }


   public void viewGuest() throws FileNotFoundException {
      System.out.println("HOTEL PLAZA GUESTS");
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Guest ID", "Salutation", "First Name", "Last Name", "Tel No", "Address", "Email Address");
      System.out.println("****************************************************************************************************************************************");
      for (int i=0; i<guestList.size(); i++) {
         System.out.println(guestList.get(i));
      }
         System.out.println("========================================================================================================================================"); 

   }
   
   
   
   public void addGuest() throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      Guest guest = new Guest();
      String word;
      
      System.out.println("Enter salutation: ");
      word = console.next();
      while(!word.matches("[a-zA-Z_]+")){
         System.out.println("Invalid salutation. Try Again:");
         word = console.next();
      }   
      guest.setSalutation(word);
      
      System.out.println("Enter first name");
      word = console.next();
      while(!word.matches("[a-zA-Z_]+")){
         System.out.println("Invalid First Name. Try Again:");
         word = console.next();
      } 
      guest.setFirstName(word);
      
      System.out.println("Enter last name: ");
      word = console.next();
      while(!word.matches("[a-zA-Z_]+")){
         System.out.println("Invalid Last Name. Try Again:");
         word = console.next();
      } 
      guest.setLastName(word);
      
      System.out.println("Enter telephone: ");
       word = console.next();
      while(!word.matches("[0-9]+")){
         System.out.println("Invalid Telephone number. Try Again:");
         word = console.next();
      } 
      guest.setTelNo(word);
      
      console.nextLine();
      
      System.out.println("Enter address ");
      word = "";
      String[] a = console.nextLine().split(" ");
         for(int i = 0; i < a.length; i++){
            word += a[i] + " ";
         }
      guest.setAddress(word);
      
      System.out.println("Enter email address ");
      word = console.next();
      guest.setEmailAddress(word);
      
      System.out.println(guest);
      guestList.add(guest);
   }
   
   public int searchByPersonID() {
      Scanner console = new Scanner(System.in); 
      System.out.println("Please enter the guest's ID:");
      String id = console.next();
      for (int i = 0; i < guestList.size(); i++) {
            if (guestList.get(i).getPersonID().equals(id)) {
               System.out.println(guestList.get(i));
               System.out.println(i);
               return i;
            }
      }
      return -1;      
   }
   
   public void editGuest() throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      int index = searchByPersonID();
      Guest guest = guestList.get(index);
      System.out.println(guest);
      System.out.println("Select option: ");
      System.out.println("[1] Change Telephone");
      System.out.println("[2] Change Address");
      System.out.println("[3] Cancel");
      String word;
      String option = console.next();
         switch (option) {
            case "1":
               System.out.print("Enter new Tel No: ");
               word = console.next();
                   while(!word.matches("[0-9]+")){
                     System.out.println("Invalid Telephone number. Try Again:");
                     word = console.next();
                   } 
               guest.setTelNo(word);
               break;
            case "2":
               System.out.println("Enter new Address: ");
               word = console.next();
                while(!word.matches("[0-9]+")){
                  System.out.println("Invalid Salary. Try Again:");
                  word = console.next();
                } 
            guest.setAddress(word);
            break;
            default:
               System.out.println("Selection Incorrect");
               break; 
         }
         System.out.println("Updated: " + guest);
   }

   public void saveToFile() throws FileNotFoundException {
      PrintStream output = new PrintStream(new File("guest.txt"));
      for (int i = 0; i < guestList.size(); i++) {
         output.println(guestList.get(i));
      }
   }   
   
   public void deleteGuest() {
      int index = searchByPersonID();
      if (index != -1) {
         guestList.remove(index);
      }    
   }

   
   //toString method
   @Override
   public String toString() {
      return  String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s",personID, salutation, firstName, lastName, telNo, address, emailAddress);         
   }   
}