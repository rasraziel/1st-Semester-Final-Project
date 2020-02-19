import java.util.*;                                //methods for managing Guests   (line: 29)
import java.text.*;                                //methods for managing Bookings (line: 180)
import java.util.concurrent.TimeUnit;              //methods for managing Rooms    (line: 517)
import java.io.*;                                  //total number of lines: 928

public class Receptionist {
   
   private ArrayList<Booking> bookingList = new ArrayList<Booking>();
   private ArrayList<Guest> guestList = new ArrayList<Guest>();
 
   public Receptionist() {}                                       
      
   public ArrayList<Booking> getBookingList() {                  
      return bookingList;
   } 
   
   public void setBookingList(ArrayList<Booking> bookingList) {  
      this.bookingList = bookingList;
   }
   
   public ArrayList<Guest> getGuestList() {                    
      return guestList;
   } 
   
   public void setGuestList(ArrayList<Guest> guestList) {    
      this.guestList = guestList;
   }
   
   //methods for managing Guests. 
   public void createGuestList() throws FileNotFoundException {    
      ArrayList<Guest> guestList = new ArrayList<Guest>();
      Scanner input = new Scanner(new File("guest.txt"));
      while (input.hasNext()) {
         Guest guest = new Guest();
         guest.setGuestID(input.next());
         guest.setSalutation(input.next());
         guest.setFirstName(input.next());
         guest.setLastName(input.next());
         guest.setTelNo(input.next());
         String streetNumber = input.next();
         String streetName = input.next();
         String city = input.next();
         String address = streetNumber + " " + streetName + " " + city + " "; 
         guest.setAddress(address);
         guest.setEmailAddress(input.next());
         guestList.add(guest);
      }
      this.guestList = guestList;
   }
   
   public void viewGuest() throws FileNotFoundException {
      System.out.println("HOTEL PLAZA GUESTS");
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Guest ID", "Salutation", "First Name", "Last Name", "Tel No", "Address", "Email Address");
      System.out.println("****************************************************************************************************************************************");
      for (int i = 0; i < guestList.size(); i++) {
         System.out.println(guestList.get(i));
      }
      System.out.println("========================================================================================================================================"); 
   }
   
   public void addGuest() throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      Guest guest = new Guest();
      System.out.print("Salutation: ");
      String word = console.next();
      while(!word.matches("[a-zA-Z_]+")){
         System.out.print("Invalid salutation. Try Again: ");
         word = console.next();
      }   
      guest.setSalutation(word);
      System.out.print("First name: ");
      word = console.next();
      while(!word.matches("[a-zA-Z_]+")){
         System.out.print("Invalid First Name. Try Again: ");
         word = console.next();
      } 
      word.toUpperCase();
      guest.setFirstName(word);
      System.out.print("Last name: ");
      word = console.next();
      while(!word.matches("[a-zA-Z_]+")){
         System.out.println("Invalid Last Name. Try Again: ");
         word = console.next();
      } 
      word.toUpperCase();
      guest.setLastName(word);
      System.out.print("Telephone: +45 ");
       word = console.next();
      while(!word.matches("[0-9]+")){
         System.out.print("Invalid Telephone number. Try Again: +45 ");
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
      System.out.print("Email: ");
      word = console.next();
      guest.setEmailAddress(word);
      guestList.add(guest);
   }
   
   public int searchByGuestID() {
      Scanner console = new Scanner(System.in); 
      System.out.print("Enter the guest's ID: ");
      String id = console.next();
      for (int i = 0; i < guestList.size(); i++) {
            if (guestList.get(i).getGuestID().equals(id)) {
               return i;
            }
      }
      return -1;      
   }
   
   public void searchByGuestIDMenu() {                           //will be used in the user interface!
      int i = searchByGuestID();
      while (i == -1) {
         System.out.println("Invalid. Input.");
         i = searchByGuestID();
      }
      System.out.println(guestList.get(i));
   }
   
   public void editGuest() throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      int index = searchByGuestID();
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
                     System.out.println("Invalid Telephone number. Try Again: +45 ");
                     word = console.next();
                   } 
               guest.setTelNo(word);
               break;
            case "2":
               System.out.println("Enter new Address (Street Name, Street Number, City): ");
               String streetName = console.next();
               String streetNumber = console.next();
               String city = console.next();
               String address = streetName + " " + streetNumber + " " + city + " ";
               guest.setAddress(address);
            break;
            default:
               System.out.println("Selection Incorrect");
               break; 
         }
         System.out.println("Updated: " + guest);
   }

   public void saveGuestsToFile() throws FileNotFoundException {
      PrintStream output = new PrintStream(new File("guest.txt"));
      for (int i = 0; i < guestList.size(); i++) {
         output.println(guestList.get(i));
      }
   }   
   
   public void deleteGuest() {
      int index = searchByGuestID();
      if (index != -1) {
         guestList.remove(index);
      } 
   }   
   
   //methods for managing bookings
   public void createBookingList() throws FileNotFoundException, ParseException {  //reads bookings from file
      ArrayList<Booking> bookingList = new ArrayList<Booking>();
      Scanner input = new Scanner(new File("bookings.txt"));
      while (input.hasNextLine()) {
         Booking booking = new Booking();
         String line = input.nextLine();
         Scanner data = new Scanner(line);
         booking.setBookingID(data.next());
         
         Guest guest = new Guest();
         guest.setFirstName(data.next());
         guest.setLastName(data.next());
         booking.setGuest(guest);

         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         booking.setBookingDate(sdf.parse(data.next()));
         booking.setCheckInDate(sdf.parse(data.next()));
         booking.setCheckOutDate(sdf.parse(data.next()));
         booking.setTotalPrice(data.nextDouble());
         ArrayList<Room> rooms = new ArrayList<Room>();
         while (data.hasNext()) {
            Room room = new Room();
            room.setRoomID(data.next());
            room.setRoomStatus(data.nextBoolean());
            room.setRoomType(data.nextInt());
            room.setInternetAccess(data.nextBoolean());
            room.setFloor(data.nextInt());
            room.setPricePerNight(data.nextDouble());
            room.setNumberOfBeds(data.nextInt());
            rooms.add(room);
         }
         booking.setRooms(rooms);
         bookingList.add(booking);
      }
      this.bookingList = bookingList;
   }
   
   public int searchByBookingID() {
      Scanner console = new Scanner(System.in); 
      System.out.print("Enter Booking ID: ");
      String id = console.next();
      for (int i = 0; i < bookingList.size(); i++) {
         if (bookingList.get(i).getBookingID().equals(id)) {
            return i;
         }
      }
      return -1;      
   }
      
   public void checkIn() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> rooms = attempt1.readRoomsFromFile();
      int index = searchByBookingID();
      while (index < 0) {
         System.out.print("Invalid Booking ID. Try again: ");
         index = searchByBookingID();
      } 
      ArrayList<Room> roomsInThisBooking = bookingList.get(index).getRooms();
      int n = roomsInThisBooking.size();
      int i;
      String roomID = "";
      for (i=0; i < n; i++) {
         roomID = roomsInThisBooking.get(i).getRoomID();
         for (int j = 0; j < rooms.size(); j++) {
            if (rooms.get(j).getRoomID().equals(roomID)) {
               rooms.get(j).setRoomStatus(false);
            }
         }
      }
      attempt1.saveInFile(rooms);     
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      System.out.println(printBooking(bookingList.get(index)));
      System.out.println("========================================================================================================================================"); 
      System.out.println("Guest checked-in successfully.");
   }  
   
   public void checkOut() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> rooms = attempt1.readRoomsFromFile();
      int index = searchByBookingID();
      while (index < 0) {
         System.out.print("Invalid Booking ID. Try again: ");
         index = searchByBookingID();
      } 
      ArrayList<Room> roomsInThisBooking = bookingList.get(index).getRooms();
      System.out.println(roomsInThisBooking);
      int n = roomsInThisBooking.size();
      int i;
      String roomID = "";
      for (i=0; i < n; i++) {
         roomID = roomsInThisBooking.get(i).getRoomID();
         for (int j = 0; j < rooms.size(); j++) {
            if (rooms.get(j).getRoomID().equals(roomID)) {
               rooms.get(j).setRoomStatus(true);
            }
         }
      }
      attempt1.saveInFile(rooms);                        
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      System.out.println(printBooking(bookingList.get(index)));
      System.out.println();
      System.out.println("========================================================================================================================================"); 
      System.out.println("Guest checked-out successfully.");
   }  

   public void createBooking() throws FileNotFoundException {
      Booking booking = new Booking();
      
      Date dateIN = booking.createCheckInDate();
      Date dateOUT = booking.createCheckOutDate();
      int lengthOfStay = booking.numberOfDays(dateIN, dateOUT);
      
      showAvailableRooms(dateIN, lengthOfStay);
      booking.addRooms();
      booking.computeTotalPrice();
      
      System.out.println("-----Enter Guest data-----");
      Scanner console = new Scanner(System.in);
      Guest guest = new Guest();
      System.out.print("Salutation: ");
      String word = console.next();
      while(!word.matches("[a-zA-Z_]+")){
         System.out.print("Invalid salutation. Try Again: ");
         word = console.next();
      }   
      guest.setSalutation(word);
      System.out.print("First name: ");
      word = console.next();
      while(!word.matches("[a-zA-Z_]+")){
         System.out.print("Invalid First Name. Try Again: ");
         word = console.next();
      } 
      word.toUpperCase();
      guest.setFirstName(word);
      System.out.print("Last name: ");
      word = console.next();
      while(!word.matches("[a-zA-Z_]+")){
         System.out.println("Invalid Last Name. Try Again: ");
         word = console.next();
      } 
      word.toUpperCase();
      guest.setLastName(word);
      System.out.print("Telephone: +45 ");
       word = console.next();
      while(!word.matches("[0-9]+")){
         System.out.print("Invalid Telephone number. Try Again: +45 ");
         word = console.next();
      } 
      guest.setTelNo(word);
      console.nextLine();
      System.out.print("Address: ");
      word = "";
      String[] a = console.nextLine().split(" ");
      for(int i = 0; i < a.length; i++){
            word += a[i] + " ";
      }
      guest.setAddress(word);
      System.out.print("Email: ");
      word = console.next();
      guest.setEmailAddress(word);
      
      booking.setGuest(guest);
      guestList.add(guest);
      
      bookingList.add(booking);
            
      System.out.println();
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      System.out.println(printBooking(booking));
      System.out.println("========================================================================================================================================"); 
   }
   
   public void saveBookingsToFile() throws FileNotFoundException {
      PrintStream output = new PrintStream(new File("bookings.txt"));
      for (int i = 0; i < bookingList.size(); i++) {
         output.println(bookingList.get(i));
      }
   }   
      
   public void extendBooking() {
      int index = searchByBookingID();
      while (index < 0) {
         System.out.print("Invalid Booking ID. Try again: ");
         index = searchByBookingID();
      } 
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      System.out.println(bookingList.get(index));
      System.out.println("========================================================================================================================================"); 
      System.out.println();
      bookingList.get(index).extendBooking(); 
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      System.out.println(printBooking(bookingList.get(index)));
      System.out.println("========================================================================================================================================"); 
   } 
   
   public void cancelBooking() {
      int index = searchByBookingID();
      while (index < 0) {
         System.out.print("Invalid Booking ID. Try again: ");
         index = searchByBookingID();
      } 
      bookingList.remove(index);
      System.out.println();
      System.out.println("The booking has been removed.");
   }
   
   public ArrayList<Booking> searchByCheckInDate(Date date) {
      //Booking booking = new Booking();
      ArrayList<Booking> filterByCheckInDate = new ArrayList<Booking>();
      int counter = 0;
      for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getCheckInDate().compareTo(date) == 0) {
               filterByCheckInDate.add(bookingList.get(i));
               counter++;
            }
      }
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      System.out.println("There are " + counter + " booking(s) on " + sdf.format(date));
      return filterByCheckInDate;    
   }
   
   public ArrayList<Booking> searchByCheckOutDate(Date date) {
      ArrayList<Booking> filterByCheckOutDate = new ArrayList<Booking>();
      int counter = 0;
      for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getCheckOutDate().compareTo(date) == 0) {
               filterByCheckOutDate.add(bookingList.get(i));
               counter++;
            }
      }
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      System.out.println("There are " + counter + " booking(s) on " + sdf.format(date));
      return filterByCheckOutDate;    
   }
   
   public ArrayList<Booking> searchByBookingDate(Date date) {
      ArrayList<Booking> filterByBookingDate = new ArrayList<Booking>();
      int counter = 0;
      for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getBookingDate().compareTo(date) == 0) {
               filterByBookingDate.add(bookingList.get(i));
               counter++;
            }
      }
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      System.out.println("There are " + counter + " booking(s) on " + sdf.format(date));
      return filterByBookingDate;    
   }
   
   public void printReceipt() {
      int a = searchByBookingID();
      ArrayList<Room> list = bookingList.get(a).getRooms();
      int numberOfRooms = list.size();   
      double totalPrice = bookingList.get(a).getTotalPrice();
      String receiptId = new RandomIdGenerator().getBase62(10);
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      System.out.println("************************************************");
      System.out.println("|~~~~~~~~~~~~~~~  HOTEL PLAZA  ~~~~~~~~~~~~~~~~|");
      System.out.println("************************************************");
      System.out.println();
      System.out.println("                    Receipt                     ");
      System.out.println();
      System.out.println("   Date: " + sdf.format(date));
      System.out.println();
      System.out.println("   Receipt ID: " + receiptId);
      System.out.println();
      System.out.println("   Number of rooms: " + numberOfRooms);
      System.out.println();
      System.out.println("   Net Amount: " + totalPrice);
      System.out.println();
      System.out.println("   Total Amount (including VAT 25%): " + totalPrice * 1.25);
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println("                  Thank you!                    ");
      System.out.println("************************************************");
   }
   
   
   public void showAvailableRooms(Date dateIN, int length) throws FileNotFoundException {
      ArrayList<ArrayList<Booking> > list = new ArrayList<ArrayList<Booking> >(length);
      Date date = dateIN;
      ArrayList<String> roomIDs = new ArrayList<String>();                                          
      for (int i = 0; i <= length; i++) {
         list.add(searchByCheckInDate(date));
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         cal.add(Calendar.DATE, 1);  
         date = cal.getTime();      
      }
      int counter = 0;
      for (int i = 0; i < length; i++) {
         for (int j = 0; j < list.get(i).size(); j++) {
            for (int k = 0; k < list.get(i).get(j).getRooms().size(); k++) {
               roomIDs.add(list.get(i).get(j).getRooms().get(k).getRoomID());   
            }
         }
      }
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<String> roomNumbers = attempt1.readRoomsIDFromFile();
      System.out.println("Available rooms for these days: ");
      roomNumbers.removeAll(roomIDs);
      System.out.println(roomNumbers);
   }
   
   public void capacity() {
      System.out.println(bookingList);
   
   
   }
   
   public String printBooking(Booking booking) {
      return String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s", booking.getBookingID(), booking.getGuest().getFirstName(), 
             booking.getGuest().getLastName(), booking.formatDate(booking.getCheckInDate()), booking.formatDate(booking.getCheckOutDate()), 
             booking.printRoomNumbers(), booking.getTotalPrice());
   }
   
   public void searchByBookingIDMenu() {
      int i = searchByBookingID();
      while (i == -1) {
         System.out.print("Invalid input. Try again: ");
         i = searchByBookingID();
      }
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      System.out.println(printBooking(bookingList.get(i)));
      System.out.println("========================================================================================================================================");   
   }
   
   public void searchByBookingDateMenu() {
      Booking booking = new Booking();
      System.out.print("Enter booking date (dd/mm/yyyy): ");
      ArrayList<Booking> array = searchByBookingDate(booking.enterDate());
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      for (int i = 0; i < array.size(); i++) {
         System.out.println(printBooking(array.get(i)));
      }
      System.out.println("========================================================================================================================================"); 
   }

   public void searchByCheckInDateMenu() {
      Booking booking = new Booking();
      System.out.print("Enter check-in date (dd/mm/yyyy): ");
      ArrayList<Booking> array = searchByCheckInDate(booking.enterDate());
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      for (int i = 0; i < array.size(); i++) {
         System.out.println(printBooking(array.get(i)));
      }
      System.out.println("========================================================================================================================================"); 
   }
   
   public void searchByCheckOutDateMenu() {
      Booking booking = new Booking();
      System.out.print("Enter check-out date (dd/mm/yyyy): ");
      ArrayList<Booking> array = searchByCheckOutDate(booking.enterDate());
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      for (int i = 0; i < array.size(); i++) {
         System.out.println(printBooking(array.get(i)));
      }
      System.out.println("========================================================================================================================================"); 
   }
   
   public void searchByGuestName() {
      ArrayList<Booking> filterByGuestName = new ArrayList<Booking>();
      int counter = 0;
      System.out.print("Enter the guest's first name: ");
      Scanner console = new Scanner(System.in);
      String firstName = console.next();
      System.out.print("Enter the guest's last name: ");
      String lastName = console.next();
      Guest guest = new Guest();
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Booking ID", "First Name", "Last Name", "Check-in", "Check-out", "Room(s)", "Net Price");                                                    
      System.out.println("****************************************************************************************************************************************");
      for (int i = 0; i < bookingList.size(); i++) {
         if (bookingList.get(i).getGuest().getFirstName().equals(firstName.toUpperCase()) && bookingList.get(i).getGuest().getLastName().equals(lastName.toUpperCase())) {
            System.out.print(printBooking(bookingList.get(i))); 
            counter++;
         }
      }  
      System.out.println("========================================================================================================================================");      
   }
   
   
   // methods for managinig rooms
   public void viewRooms() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> rooms = attempt1.readRoomsFromFile();
      System.out.println("HOTEL PLAZA ROOMS");
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Room ID", "Availability", "Type", "Internet Access", "Floor", "Price per night", "Number of beds");
      System.out.println("****************************************************************************************************************************************");
      for (int i=0; i<rooms.size(); i++) {
         if (rooms.get(i).getRoomStatus() && rooms.get(i).getInternetAccess()) {
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", rooms.get(i).getRoomID(), "available" , rooms.get(i).getRoomType(), "yes", rooms.get(i).getFloor(), rooms.get(i).getPricePerNight(), rooms.get(i).getNumberOfBeds());
         } else if (!rooms.get(i).getRoomStatus() && rooms.get(i).getInternetAccess()) {
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", rooms.get(i).getRoomID(), "unavailable" , rooms.get(i).getRoomType(), "yes", rooms.get(i).getFloor(), rooms.get(i).getPricePerNight(), rooms.get(i).getNumberOfBeds());
         } else if (rooms.get(i).getRoomStatus() && !rooms.get(i).getInternetAccess()) {
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", rooms.get(i).getRoomID(), "available" , rooms.get(i).getRoomType(), "no", rooms.get(i).getFloor(), rooms.get(i).getPricePerNight(), rooms.get(i).getNumberOfBeds());
         } else {
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", rooms.get(i).getRoomID(), "unavailable" , rooms.get(i).getRoomType(), "no", rooms.get(i).getFloor(), rooms.get(i).getPricePerNight(), rooms.get(i).getNumberOfBeds());
         }
      }
      System.out.println("========================================================================================================================================"); 
   }
   
   public void showAvailableRooms() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> rooms = attempt1.readRoomsFromFile();
      System.out.println("HOTEL PLAZA ROOMS AVAILABLE");
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Room ID", "Type", "Internet Access", "Floor", "Price per night", "Number of beds");
      System.out.println("****************************************************************************************************************************************");
      for (int i=0; i<rooms.size(); i++) {
         if (rooms.get(i).getInternetAccess()) {
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", rooms.get(i).getRoomID(), rooms.get(i).getRoomType(), "yes", rooms.get(i).getFloor(), rooms.get(i).getPricePerNight(), rooms.get(i).getNumberOfBeds());
         } else {
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", rooms.get(i).getRoomID(), rooms.get(i).getRoomType(), "no", rooms.get(i).getFloor(), rooms.get(i).getPricePerNight(), rooms.get(i).getNumberOfBeds());
         }
      }
      System.out.println("========================================================================================================================================"); 
   }   
   
   public void createNewRoom() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> rooms = attempt1.readRoomsFromFile();
      Scanner input = new Scanner(System.in);
      System.out.println("Do you want to create:");
      System.out.println("[1] Single room");
      System.out.println("[2] Double room");
      System.out.println("[3] Suite");
      System.out.println("[4] Other room");
      System.out.println("[5] Exit");
      if (input.hasNextInt()){
         int choice = input.nextInt();
         if (choice > 0 && choice < 6){
            switch (choice){
               case 1:
                  createSingle();
                  break; 
               case 2:
                  createDouble();
                  break; 
               case 3:
                  createSuite();
                  break;
               case 4:
                  createOtherRoom();
                  break;
               case 5:
                  break; 
               default:
                  System.out.println("Wrong input.");
                  createNewRoom();
                  break;                        
            }
         } else {
            System.out.println("Wrong input.");
         }  
      } else {
         System.out.println("Wrong input.");  
      }
      
   }

   public String createSingle() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> list = attempt1.readRoomsFromFile();
      Scanner input = new Scanner(System.in);
      System.out.println("Please type ID: ");
      String ID = "";
      if (input.hasNextInt()){
         ID = Integer.toString(input.nextInt());
         int check = checkID(ID);
         if (check == 0) {
            System.out.println("Try again.");
            createSingle();
         } else {
            Single single = new Single(ID);
            System.out.println("Does the room has internet access? (yes/no)");
            String answer = input.next();
            switch (answer){
               case "yes":
                  single.setInternetAccess(true);
                  break;
               case "no":
                  single.setInternetAccess(false);
                  break;
               default:
                  System.out.println("Wrong input. Answer has been set to yes.");
                  break;
            }
            System.out.println("Type the floor the room is located on: ");
            String word = input.next();
            while (!word.matches("[0-9]+")){
               System.out.println("Invalid floor number. Try Again:");
               word = input.next();
            }
            int floor = Integer.valueOf(word); 
            single.setFloor(floor);
            printRoom(single);
            list.add(single);
            attempt1.saveInFile(list);  
         }
      } else {
         System.out.println("Room's ID has to consist of numbers.");
         System.out.println("Try again: ");
         ID = createSingle();
      }
      return ID;
   }
  
   public String createDouble() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> list = attempt1.readRoomsFromFile();
      Scanner input = new Scanner(System.in);
      System.out.println("Please type ID: ");
      String ID = "";
      if (input.hasNextInt()){
         ID = Integer.toString(input.nextInt());
         int check = checkID(ID);
         if (check == 0) {
            System.out.println("Try again.");
            createDouble();
         } else {
            Double doubleRoom = new Double(ID);
            System.out.println("Does the room has internet access? (yes/no)");
            String answer = input.next();
            switch (answer){
               case "yes":
                  doubleRoom.setInternetAccess(true);
                  break;
               case "no":
                  doubleRoom.setInternetAccess(false);
                  break;
               default:
                  System.out.println("Wrong input. Answer has been set to yes.");
                  break;
            }
            System.out.println("Type the floor the room is located on: ");
            String word = input.next();
            while (!word.matches("[0-9]+")){
               System.out.println("Invalid floor number. Try Again:");
               word = input.next();
            }
            int floor = Integer.valueOf(word); 
            doubleRoom.setFloor(floor);
            printRoom(doubleRoom);
            list.add(doubleRoom);
            attempt1.saveInFile(list);  
         }
      } else {
         System.out.println("Room's ID has to consist of numbers.");
         System.out.println("Try again: ");
         ID = createDouble();
      }
      return ID;
   }  
   
   public String createSuite() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> list = attempt1.readRoomsFromFile();
      Scanner input = new Scanner(System.in);
      System.out.println("Please type ID: ");
      String ID = "";
      if (input.hasNextInt()){
         ID = Integer.toString(input.nextInt());
         int check = checkID(ID);
         if (check == 0) {
            System.out.println("Try again.");
            createSuite();
         } else {
            Suite suite = new Suite(ID);
            System.out.println("Does the room has internet access? (yes/no)");
            String answer = input.next();
            switch (answer){
               case "yes":
                  suite.setInternetAccess(true);
                  break;
               case "no":
                  suite.setInternetAccess(false);
                  break;
               default:
                  System.out.println("Wrong input. Answer has been set to yes.");
                  break;
            }
            System.out.println("Type the floor the room is located on: ");
            String word = input.next();
            while (!word.matches("[0-9]+")){
               System.out.println("Invalid floor number. Try Again:");
               word = input.next();
            }
            int floor = Integer.valueOf(word); 
            suite.setFloor(floor);
            printRoom(suite);
            list.add(suite);
            attempt1.saveInFile(list);  
         }
      } else {
         System.out.println("Room's ID has to consist of numbers.");
         System.out.println("Try again: ");
         ID = createSuite();
      }
      return ID;
   }  
  
   public String createOtherRoom() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> list = attempt1.readRoomsFromFile();
      Scanner input = new Scanner(System.in);
      System.out.println("Please type ID: ");
      String ID = "";
      if (input.hasNextInt()){
         ID = Integer.toString(input.nextInt());
         int check = checkID(ID);
         if (check == 0) {
            System.out.println("Try again.");
            createOtherRoom();
         } else {
            Room room = new Room(ID);
            System.out.println("Does the room has internet access? (yes/no)");
            String answer = input.next();
            switch (answer){
               case "yes":
                  room.setInternetAccess(true);
                  break;
               case "no":
                  room.setInternetAccess(false);
                  break;
               default:
                  room.setInternetAccess(true);
                  System.out.println("Wrong input. Answer has been set to yes.");
                  break;
            }
            System.out.println("Type the floor the room is located on: ");
            String word = input.next();
            while (!word.matches("[0-9]+")){
               System.out.println("Invalid floor number. Try Again:");
               word = input.next();
            }
            int floor = Integer.valueOf(word); 
            room.setFloor(floor);
            System.out.println("Type the price per night for the room.");
            word = input.next();
            while (!word.matches("[0-9]+")){
               System.out.println("Invalid input. Try Again:");
               word = input.next();
            }
            double price = Integer.valueOf(word); 
            room.setPricePerNight(price);
            System.out.println("How many beds are in the room?");
            word = input.next();
            while (!word.matches("[0-9]+")){
               System.out.println("Invalid input. Try Again:");
               word = input.next();
            }
            int beds = Integer.valueOf(word); 
            room.setNumberOfBeds(beds);
            printRoom(room);
            list.add(room);
            attempt1.saveInFile(list);  
         }
      } else {
         System.out.println("Room's ID has to consist of numbers.");
         System.out.println("Try again: ");
         ID = createOtherRoom();
      }
      return ID;
   }   
  
   public void printRoom(Room room){
      System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Room ID", "Availability", "Type", "Internet Access", "Floor", "Price per night", "Number of beds");
      if (room.getRoomStatus() && room.getInternetAccess()){
         System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", room.getRoomID(), "available", room.getRoomType(), "yes", room.getFloor(), room.getPricePerNight(), room.getNumberOfBeds());         
      } else if (!room.getRoomStatus() && room.getInternetAccess()){
         System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", room.getRoomID(), "unavailable", room.getRoomType(), "yes", room.getFloor(), room.getPricePerNight(), room.getNumberOfBeds());         
      } else if (room.getRoomStatus() && !room.getInternetAccess()){
         System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", room.getRoomID(), "available", room.getRoomType(), "no", room.getFloor(), room.getPricePerNight(), room.getNumberOfBeds());         
      } else {
         System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", room.getRoomID(), "unavailable", room.getRoomType(), "no", room.getFloor(), room.getPricePerNight(), room.getNumberOfBeds());         
      }
   }  
  
   public int checkID(String roomID) throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> rooms = attempt1.readRoomsFromFile();
      int check = 1;
      for (int i = 0; i < rooms.size(); i++) {
         if (rooms.get(i).getRoomID().equals(roomID)) {
            System.out.println("Room " + rooms.get(i).getRoomID() + " already exist on the " + rooms.get(i).getFloor() + " floor."); 
            check = 0;
         }
      }
      return check;   
   }
      
   public void editRoomPrice() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> rooms = attempt1.readRoomsFromFile();
      //System.out.println(rooms);
      Scanner console = new Scanner(System.in);
      System.out.print("Enter room's ID: ");
      String roomID = console.next();
      while (!roomID.matches("[0-9]+")){
         System.out.println("It wasn't an ID. Please try again.");
         roomID = console.next();
      }      
      int check = 1;
      int i;
      for (i = 0; i < rooms.size(); i++) {
         if (rooms.get(i).getRoomID().equals(roomID)) {
            printRoom(rooms.get(i));
            System.out.print("Enter new price: ");
            if (console.hasNextInt() || console.hasNextDouble()){
               double newPrice = console.nextDouble();
               rooms.get(i).setPricePerNight(newPrice);
               printRoom(rooms.get(i));
               attempt1.saveInFile(rooms);
               check = 0;
               break;
            } else {
               System.out.println("Wrong input.");
            }
            break;
         }    
      }
      if (i == rooms.size()){
         System.out.println("There's no such a room on the list.");
         System.out.println("Do you want to try again? (yes/no)");
         String answer = console.next();
         switch (answer) {
            case "yes":
               editRoomPrice();
               break;
            case "no":
               break;
            default:
               System.out.println("Selection Incorrect");
               break; 
         }
      }
   }     

   public void deleteRoom() throws FileNotFoundException {
      HandlingFile attempt1 = new HandlingFile();
      ArrayList<Room> rooms = attempt1.readRoomsFromFile();
      Scanner console = new Scanner(System.in);
      System.out.print("Enter room's ID: ");
      String roomID = console.next();
      while (!roomID.matches("[0-9]+")){
         System.out.println("It wasn't an ID. Please try again.");
         roomID = console.next();
      }
      int a = 1;
      boolean check = false;
      int i = -1;
      while ((i+1) < rooms.size() && !check) {
         check = rooms.get(i+1).getRoomID().equals(roomID);
         i++;
      }
      if (check){
         a = 0;
      }
      switch (a){
         case 0:
            System.out.println("Are you sure you want to delete room number " + roomID + "? (yes/no)");
            String answer = console.next();
            switch (answer){
               case "yes":
                  rooms.remove(i);
                  attempt1.saveInFile(rooms);
                  System.out.println("Room was deleted."); 
                  break;
               case "no":
                  break;
               default:
                  System.out.println("Wrong input. The room wasn't deleted.");
                  break;
            }
            break;
          case 1:
            System.out.println("The room wasn't found in the system.");
            System.out.println("DO you want to try again? (yes/no)");
            answer = console.next();
            switch (answer){
               case "yes":
                  deleteRoom();
                  break;
               case "no":
                  break;
               default:
                  System.out.println("Wrong input.");
                  break;
            }
            break;
      }
   }   
}
   

   
