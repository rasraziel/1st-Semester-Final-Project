package roomgenerator;
import java.util.*;   // for Scanner
import java.io.*;     // for File

public class RoomGenerator {

   public static void main(String[] args) throws FileNotFoundException {
   
   System.out.println("//roomID, status, roomType, internetAccess, floor, price per night, number of beds");
   System.out.println();
         
   ArrayList<Single> singleRooms = createSingleRooms(); //creating single rooms on floor 1-4
   ArrayList<Double> doubleRooms = createDoubleRooms(); //creating doublr rooms on floor 1-4
   ArrayList<Suite> suites = createSuites(); //creating suites on floor 1-4
   
   /*Suite pokoj = new Suite(2);
   System.out.println(pokoj);
   pokoj.editRooomPrice(); 
   
   System.out.println(pokoj);*/
   
   //System.out.println(singles.toString2());
   
   saveInFile(singleRooms, doubleRooms, suites);
      
   }
   
   public static ArrayList<Single> createSingleRooms() throws FileNotFoundException {
      ArrayList<Single> singleRooms = new ArrayList();
      for (int i = 1; i <= 4; i++){
         singleRooms.add(new Single(i));
      }
      System.out.println(singleRooms.toString());

      return singleRooms;      
   }
   
   public static ArrayList<Double> createDoubleRooms() throws FileNotFoundException {
      ArrayList<Double> doubleRooms = new ArrayList();
      for (int i = 1; i <= 4; i++){
         doubleRooms.add(new Double(i));
      }
      System.out.println(doubleRooms.toString());

      return doubleRooms;      
   }

   public static ArrayList<Suite> createSuites(){
      ArrayList<Suite> suites = new ArrayList();
      for (int i = 1; i <= 4; i++){
         suites.add(new Suite(i));
      }
      System.out.println(suites.toString());  
      
      return suites;    
   }
   
   public static void saveInFile(ArrayList<Single> singles, ArrayList<Double> doubles, ArrayList<Suite> suites) throws FileNotFoundException {
      PrintStream file = new PrintStream(new File("rooms.txt"));
      for (int i = 0; i < singles.size(); i++){
         file.println(singles.get(i));
         file.println(doubles.get(i));
         file.println(suites.get(i));
      }
      file.flush();
      file.close();
   }  

}