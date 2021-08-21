import org.intellij.lang.annotations.RegExp;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;
import java.util.regex.Pattern;

public class mainClass {


    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static SecureRandom randomGen = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));


    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static boolean addEmployee() throws IOException {

        String name = "";
        String position = "";
        String dob = "";
        int id = 0;
        Scanner stdin = new Scanner(System.in);

        Map<String,Object> queries = new LinkedHashMap<>();

        do{
            System.out.println("\nEnter Employee Name (First only)");
            name = reader.readLine();
        }while(name.length() == 0);

        do{
            System.out.println("\nEnter Employee Position\n");
            position = reader.readLine();
        }while(position.length() == 0);

        do{
            System.out.println("\nEnter employee date of birth\n");
            dob = reader.readLine();
        }while(dob.length() == 0);

        id = ((Integer)Math.abs(randomGen.nextInt()));

        queries.put("Name",name);
        queries.put("Position",position);
        queries.put("DOB",dob);
        queries.put("ID",id);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.createEmployee(queries);

        Response<Object> response = call.execute();

        return response.isSuccessful();

    }

    public static boolean updateEmployee() throws IOException {

        String name = "";
        String position = "";
        String dob = "";
        int id = 0;
        Scanner stdin = new Scanner(System.in);

        Map<String,Object> queries = new LinkedHashMap<>();

        do{
            System.out.println("\nEnter employee id whom to replace details\n");
            id = stdin.nextInt();
        }while(id == 0);

        do{
            System.out.println("\nEnter Employee Name (First only)");
            name = reader.readLine();
        }while(name.length() == 0);

        do{
            System.out.println("\nEnter Employee Position\n");
            position = reader.readLine();
        }while(position.length() == 0);

        do{
            System.out.println("\nEnter employee date of birth\n");
            dob = reader.readLine();
        }while(dob.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        queries.put("Name",name);
        queries.put("Position",position);
        queries.put("DOB",dob);

        Call<Void> call = requestInterface.updateEmployee(queries,id);

        Response<Void> response = call.execute();

        return response.isSuccessful();


    }

    public static Long getEmployeeCount() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Long> call = requestInterface.getEmployeeCount();

        Response<Long> response = call.execute();

        System.out.println("\n---- Response Received : " + response.body() + "\n");

        return response.body();

    }

    public static boolean removeEmployee() throws IOException {

        String name = "";

        do{
            System.out.println("\nEnter the name of the employee to remove\n");
            name = reader.readLine();
        }while(name.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.deleteEmployee(name);

        Response<Void> response = call.execute();

        return response.isSuccessful();

    }

    public static boolean removeAllEmployees() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.deleteAllEmployees();

        Response<Void> response = call.execute();

        return response.isSuccessful();
    }

    public static Object getAllEmployees() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getEmployees();

        Response<Object> response = call.execute();

        return response.body();

    }


    public static boolean getEmployee() throws IOException {

        String name = "";


        do{
            System.out.println("\nEnter the name of the employee to get\n");
            name = reader.readLine();
        }while(name.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getEmployee(name);

        Response<Object> response = call.execute();

        return response.isSuccessful();

    }

    public static boolean createCollection() throws IOException {

        String collectionName = "";

        do{
            System.out.println("\nEnter the name of the collection you want to create\n");
            collectionName = reader.readLine();
        }while(collectionName.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.createCollection(collectionName);

        Response<Void> response = call.execute();

        System.out.println("\n----- Collection creation status : " + (response.isSuccessful()? "SUCCESS": "FAILED" + "-----\n"));

        return response.isSuccessful();

    }

    public static Object getCollectionNames() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Set<String>> call = requestInterface.getCollectionNames();

        Response<Set<String>> response = call.execute();

        if(response.isSuccessful()){
            System.out.println("\n---------- COLLECTION NAMES ----------\n");
            response.body().stream().forEach(e -> System.out.println("--> " + e));
            System.out.println("\n--------------------------------------\n");
        }

        return response.body();

    }

    public static boolean getCollection() throws IOException {

        String collectionName = "";

        do{

            System.out.println("\nEnter collection name to acquire\n");
            collectionName = reader.readLine();

        }while(collectionName.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.getCollection(collectionName);

        Response<Void> response = call.execute();

        return response.isSuccessful();

    }

    public static boolean collectionExists() throws IOException {

        String collectionName = "";

        do{

            System.out.println("\nEnter collection name to test if it exists in database\n");
            collectionName = reader.readLine();

        }while(collectionName.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.doesCollectionExist(collectionName);

        Response<Void> response = call.execute();

        return response.isSuccessful();

    }

    public static boolean createAdmin() throws IOException {

        String adminName;
        String adminClearance;
        String adminRole;
        String adminDOB;
        Integer adminId = -1;

        Scanner stdin = new Scanner(System.in);

        do{

            System.out.println("\nEnter Admin's Name");
            adminName = reader.readLine();

        }while(adminName.length() == 0);

        do{

            System.out.println("\nEnter Admin's ID #\n");
            adminId = stdin.nextInt();

        }while(adminId == -1);

        do{

            System.out.println("\nEnter Admin's Clearance\n");
            adminClearance = reader.readLine();

        }while(adminClearance.length() == 0);

        do{

            System.out.println("\nEnter Admin's Role\n");
            adminRole = reader.readLine();

        }while(adminRole.length() == 0);

        do{

            System.out.println("\nEnter Admin's DOB\n");
            adminDOB = reader.readLine();

        }while(adminDOB.length() == 0);

        Map<String,Object> body = new LinkedHashMap<>();
        body.put("Name",adminName);
        body.put("Clearance",adminClearance);
        body.put("Role",adminRole);
        body.put("DOB",adminDOB);
        body.put("ADMINID",adminId);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.createAdmin(body);

        Response<Void> response = call.execute();

        return response.isSuccessful();

    }

    public static boolean listAllAdmin() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.listAllAdmin();

        Response<Void> response = call.execute();

        return response.isSuccessful();

    }

    public static boolean updateAdmin() throws IOException {

        String newAdminName = "";
        String newAdminRole = "";
        String newAdminClearance = "";
        String newAdminDOB = "";
        Integer origAdminID = 0;

        do{

            System.out.println("\nPlease enter the Admin's ID to replace");
            try {
                origAdminID = Integer.parseInt(reader.readLine());
            }
            catch(Exception e){
            }

        }while(origAdminID == 0);

        do{

            System.out.println("\nPlease enter the new Admin's Name");
            newAdminName = reader.readLine();

        }while(newAdminName.length() == 0);

        do{

            System.out.println("\nPlease enter the new Admin's DOB");
            newAdminDOB = reader.readLine();

        }while(newAdminDOB.length() == 0);

        do{

            System.out.println("\nPlease enter the new Admin's Clearance");
            newAdminClearance = reader.readLine();

        }while(newAdminClearance.length() == 0);

        do{

            System.out.println("\nPlease enter the new Admin's Role");
            newAdminRole = reader.readLine();

        }while(newAdminRole.length() == 0);

        Map<String,Object> body = new LinkedHashMap<>();

        body.put("Name",newAdminName);
        body.put("DOB",newAdminDOB);
        body.put("ADMINID",origAdminID);
        body.put("Role",newAdminRole);
        body.put("Clearance",newAdminClearance);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.updateAdmin(origAdminID,body);

        Response<Void> response = call.execute();

        return response.isSuccessful();

    }

    public static boolean getAdmin() throws IOException {

        Integer adminId = 0;

        do{

            System.out.println("\nEnter the Id of the Admin who's details you want to acquire");
            adminId = Integer.parseInt(reader.readLine());

        }while(adminId == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.getAdmin(adminId);

        Response<Void> response = call.execute();

        return response.isSuccessful();

    }

    public static boolean removeAdmin() throws IOException {

        Integer adminId = 0;

        do{

            System.out.println("\nEnter the Id of the Admin whom to remove");
            adminId = Integer.parseInt(reader.readLine());

        }while(adminId == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.removeAdmin(adminId);

        Response<Void> response = call.execute();

        return response.isSuccessful();

    }

    public static Long getAdminCount() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Long> call = requestInterface.getAdminCount();

        Response<Long> response = call.execute();

        System.out.println("\n---- ADMIN COUNT : " + response.body() + "\n");

        return response.body();

    }

    public static boolean removeAllAdmin() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Void> call = requestInterface.removeAllAdmin();

        Response<Void> response = call.execute();

        return response.isSuccessful();
    }

    public static List<String> listAdminNames() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<List<String>> call = requestInterface.getAdminNames();

        Response<List<String>> response = call.execute();

        assert response.body() != null;
        response.body().forEach(System.out::println);

        return response.body();

    }

    public static List<Object> getAdminByName() throws IOException {

        String adminNameQuery = "";

        do{

            System.out.println("\nEnter the name of the admin you wish to retrieve from the database\n");
            adminNameQuery = reader.readLine();

        }while(adminNameQuery.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<List<Object>> call = requestInterface.getAdminByName(adminNameQuery);

        Response<List<Object>> response = call.execute();

        response.body().forEach(System.out::println);

        return response.body();

    }

    public static Object getSoccerPlayerByFirstName() throws IOException {

        String firstName = "";
        do{

            System.out.println("Enter in the soccer players first name");
            firstName = reader.readLine();

        }while(firstName.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getSoccerPlayerByFirstName(firstName);

        Response<Object> response = call.execute();

        System.out.println(response.body());

        return response.body();


    }

    public static Object getSoccerPlayerByLastName() throws IOException {

        String lastName = "";
        do{
            System.out.println("Enter in soccer player's last name");
            lastName = reader.readLine();
        }while(lastName.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getSoccerPlayerByLastName(lastName);

        Response<Object> response = call.execute();

        System.out.println(response.body());

        return response.body();

    }

    public static Object updateByLastName() throws IOException {

        String lastNameQuery = "";

        String newPosition = "";
        String newFirstName = "";
        String newDOB = "";
        Integer newYellowCards = -1;
        Integer newRedCards = -1;

        do{

            System.out.println("Enter the last name of the soccer player to update");
            lastNameQuery = reader.readLine();

        }while(lastNameQuery.length() == 0);

        do{

            System.out.println("Enter the new position of the soccer player");
            newPosition = reader.readLine();

        }while(newPosition.length() == 0);


        do{

            System.out.println("Enter the new date of birth of the soccer player");
            newDOB = reader.readLine();

        }while(newDOB.length() == 0);


        do{

            System.out.println("Enter the new First Name of the Soccer Player");
            newFirstName = reader.readLine();

        }while(newFirstName.length() == 0);

        do{

            System.out.println("Enter the amount of yellow cards");
            newYellowCards = Integer.parseInt(reader.readLine());

        }while(newYellowCards == -1);

        do{

            System.out.println("Enter the amount of red cards");
            newRedCards = Integer.parseInt(reader.readLine());

        }while(newRedCards == -1);

        Map<String,Object> body = new LinkedHashMap<>();

        body.put("firstName",newFirstName);
        body.put("lastName",lastNameQuery);
        body.put("DOB",newDOB);
        body.put("position",newPosition);
        body.put("yellowCards",newYellowCards);
        body.put("redCards",newRedCards);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.updateByLastName(lastNameQuery,body);

        Response<Object> response = call.execute();

        return response.body();

    }

    public static void createSoccerPlayer() throws IOException {

        String firstName = "";
        String lastName = "";
        String dob = "";
        String position = "";
        String YellowCards = "";
        String redCards = "";

        do{

            System.out.println("Enter the Soccer Player's FirstName");
            firstName = reader.readLine();

        }while(firstName.length() == 0);

        do{

            System.out.println("Enter last name");
            lastName = reader.readLine();

        }while(lastName.length() == 0);

        do{

            System.out.println("Enter DOB");
            dob = reader.readLine();

        }while(dob.length() == 0);

        do{

            System.out.println("Enter position");
            position = reader.readLine();

        }while(position.length() == 0);

        do{

            System.out.println("Enter yellow cards");
            YellowCards = reader.readLine();

        }while(YellowCards.length() == 0);

        do{

            System.out.println("Enter red cards");
            redCards = reader.readLine();

        }while(redCards.length() == 0);



    }

    public static Object getSoccerPlayerYellowCards() throws IOException {

        Integer yellowCards = -1;

        do{

            System.out.println("Enter the amount of yellow cards to query the database for");
            try{
                yellowCards = Integer.parseInt(reader.readLine());
            }
            catch(Exception e){
                System.out.println("Enter valid number");
            }

        }while(yellowCards.intValue() < 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getSoccerPlayerByYellowCards(yellowCards);

        Response<Object> response = call.execute();

        return response.body();
    }

    public static Object getSoccerPlayerByRedCards() throws IOException {

        Integer redCards = -1;

        do{

            System.out.println("\nEnter the amount of red cards to query the database for all soccer player files for");
            redCards = Integer.parseInt(reader.readLine());

        }while(redCards.intValue() < 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getSoccerPlayerByRedCards(redCards);

        Response<Object> response = call.execute();

        return response.body();

    }

    public static boolean properDOB(String dob){
        Pattern p = Pattern.compile("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]");
        return p.matcher(dob).matches();
    }

    public static Object getSoccerPlayerByDOB() throws IOException {

        String DOB = "";

        do{

            System.out.println("\nEnter the DOB to query the database for all players with similar DOB in format MM-DD-YYYY");
            DOB = reader.readLine();

        }while(DOB.length() == 0 && !properDOB(DOB));

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getSoccerPlayerByDOB(DOB);

        Response<Object> response = call.execute();

        return response.body();

    }

    public static Object removeAllSoccerPlayers() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        try {
            Call<Object> call = requestInterface.removeAllSoccerPlayers();

            Response<Object> response = call.execute();

            return response.body();
        }
        catch(Exception e){
            return null;
        }

    }

    public static Object addSoccerPlayer() throws IOException {

        requestInterface requestInterface = retrofit.create(requestInterface.class);


        String firstName = "";
        String lastName = "";
        String DOB = "";
        String position = "";
        Integer yellowCards = -1;
        Integer redCards = -1;

        do{

           System.out.println("Enter first name for soccer player");
           firstName = reader.readLine();

        }while(firstName.length() == 0);

        do{

            System.out.println("Enter last name for soccer player");
            lastName = reader.readLine();

        }while(lastName.length() == 0);

        do{

            System.out.println("Enter DOB for soccer player");
            DOB = reader.readLine();

        }while(DOB.length() == 0);

        do{

            System.out.println("Enter position for soccer player");
            position = reader.readLine();

        }while(position.length() == 0);

        do{

            System.out.println("Enter the amount of yellow cards for soccer player");
            yellowCards = Integer.parseInt(reader.readLine());

        }while(yellowCards.intValue() == -1);

        do{

            System.out.println("Enter the amount of red cards for soccer player");
            redCards = Integer.parseInt(reader.readLine());

        }while(redCards.intValue() == -1);


        Map<String,Object> soccerPlayer = new LinkedHashMap<>();
        soccerPlayer.put("firstName",firstName);
        soccerPlayer.put("lastName",lastName);
        soccerPlayer.put("DOB",DOB);
        soccerPlayer.put("position",position);
        soccerPlayer.put("yellowCards",yellowCards);
        soccerPlayer.put("redCards",redCards);

        try{

            Call<Object> call = requestInterface.addSoccerPlayer(soccerPlayer);
            Response<Object> response = call.execute();

            return response.body();
        }
        catch(Exception e){
            return null;
        }

    }

    public static Object addUser() throws IOException {

        String userName = "";
        String password = "";
        String apiKey = "";
        String secretKey = "";
        Map<String,Object> user = new LinkedHashMap<>();
        do{

            System.out.println("Enter userName");
            userName = reader.readLine();

        }while(userName.length() == 0);

        do{

            System.out.println("Enter password");
            password = reader.readLine();

        }while(password.length() == 0);

        do{

            System.out.println("Enter apiKey");
            apiKey = reader.readLine();

        }while(apiKey.length() == 0);

        do{

            System.out.println("Enter secretKey");
            secretKey = reader.readLine();

        }while(secretKey.length() == 0);

        user.put("userName",userName);
        user.put("password",password);
        user.put("apiKey",apiKey);
        user.put("secretKey",secretKey);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.addUser(user);

        Response<Object> response = call.execute();

        return response.isSuccessful();

    }

    public static Object getUser() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        String userName = "";
        String secretKey = "";

        do{

            System.out.println("Enter username of user to get");
            userName = reader.readLine();

        }while(userName.length() == 0);

        do{

            System.out.println("Enter secretKey of user");
            secretKey = reader.readLine();

        }while(secretKey.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        // should return false, not reversing secretkey

        Call<Object> call = requestInterface.getUser(userName,secretKey);

        Response<Object> response = call.execute();

        return response.isSuccessful();


    }

    public static Object getUserByApiKey() throws IOException {

        String apiKey = "";
        String password = "";

        do{

            System.out.println("Enter the apiKey of the user");
            apiKey = reader.readLine();

        }while(apiKey.length() == 0);

        do{

            System.out.println("Enter the password backwards of the user");
            password = reader.readLine();

        }while(password.length() == 0);

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getUserApiKey(apiKey,password);

        Response<Object> response = call.execute();

        return response.body();


    }

    // secretkey : thisisasecretkey

    // yektercesasisiht

    public static Object getUserViaUsername() throws IOException {

        String userName = "";
        String secretKey = "";

        do{

            System.out.println("Enter user's username to search for");
            userName = reader.readLine();

        }while(userName.equalsIgnoreCase(""));

        do{

            System.out.println("Enter user's secret key to enter in as password to gain access to database");
            secretKey = reader.readLine();

        }while(secretKey.equalsIgnoreCase(""));

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getUserUserName(userName,secretKey);

        Response<Object> response = call.execute();

        return response.body();

    }

    public Object getUserViaPassword() throws IOException {

        String secretKey = "";
        String password = "";

        do{

            System.out.println("Enter the secret key of the user in reverse for authorization to access database");

            secretKey = reader.readLine();

        }while(secretKey.equalsIgnoreCase(""));

        do{

            System.out.println("Enter the password of the user you are searching for");
            password = reader.readLine();

        }while(password.equalsIgnoreCase(""));

        requestInterface requestInterface = retrofit.create(requestInterface.class);

        Call<Object> call = requestInterface.getUserPassword(password,secretKey);

        Response<Object> response = call.execute();

        return response.body();

    }



    public static void printMenu(){

        System.out.println("-=-=-=MENU-=-=-=\n\n--- Employee Methods ---\n1)Add Employee\n2)Get Employee\n3)Get All Employees\n4)Remove Employee\n5)Remove All Employees\n6)Employee Count\n7)Update Employee\n--- Server Methods ---\n8)Create collection\n9)Get Collection Names\n10)Get Collection Object\n11)Test Collection Existence\n--- Admin Methods ---\n12)Create Admin\n13)List All Admin\n14)Update Admin\n15)Get Admin\n16)Remove Admin\n17)Get Admin Count\n18)Remove All Admin\n19)List Admin Names\n20)Get Admin(s) by Name\n--- Soccer Methods\n21)Get Soccer Player by First Name\n22)Get Soccer Player by Last Name\n23)Update Soccer Player by Last Name\n24)Get Player(s) by # of yellow cards\n25)Get Player(s) by # of red cards\n26)Get Player(s) by DOB\n27)Remove all soccer players\n28)Add Soccer Player\n--- User Methods\n29)Add User\n30)Get User(Auth required)\n31)Get User Via ApiKey(Auth required)\n32)Get user via username\n33)Exit Program\n-=-=-=-=-=-=-=-=");

    }

    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        int input = 0;
        Scanner stdin = new Scanner(System.in);
        while(true){

            do{
                printMenu();
                input = stdin.nextInt();
            }while(input == 0);

            switch(input){

                case 1:
                    addEmployee();
                    break;

                case 2:
                    getEmployee();
                    break;

                case 3:
                    getAllEmployees();
                    break;
                case 4:
                    removeEmployee();
                    break;
                case 5:
                    removeAllEmployees();
                    break;
                case 6:
                    getEmployeeCount();
                    break;
                case 7:
                    updateEmployee();
                    break;
                case 8:
                    createCollection();
                    break;
                case 9:
                    getCollectionNames();
                    break;
                case 10:
                    getCollection();
                    break;
                case 11:
                    collectionExists();
                    break;
                case 12:
                    createAdmin();
                    break;
                case 13:
                    listAllAdmin();
                    break;
                case 14:
                    updateAdmin();
                    break;
                case 15:
                    getAdmin();
                    break;
                case 16:
                    removeAdmin();
                    break;
                case 17:
                    getAdminCount();
                    break;
                case 18:
                    removeAllAdmin();
                    break;
                case 19:
                    listAdminNames();
                    break;
                case 20:
                    getAdminByName();
                    break;
                case 21:
                    getSoccerPlayerByFirstName();
                    break;
                case 22:
                    getSoccerPlayerByLastName();
                    break;
                case 23:
                    updateByLastName();
                    break;
                case 24:
                    getSoccerPlayerYellowCards();
                    break;
                case 25:
                    getSoccerPlayerByRedCards();
                    break;
                case 26:
                    getSoccerPlayerByDOB();
                    break;
                case 27:
                    removeAllSoccerPlayers();
                    break;
                case 28:
                    addSoccerPlayer();
                    break;
                case 29:
                    addUser();
                    break;
                case 30:
                    getUser();
                    break;
                case 31:
                    getUserByApiKey();
                    break;
                case 32:
                    getUserViaUsername();
                    break;
                default:
                    break;

            }

        }

        //Map<String,Object> map = new LinkedHashMap();
        //map.put("Name","Peter");
        //map.put("Position","Clerk");
        //map.put("DOB","10/23/45");

        //requestInterface requestInterface = retrofit.create(requestInterface.class);

        //Call<Object> call = requestInterface.createEmployee(map);

        //Response<Object> response = call.execute();

        //Call<Void> call = requestInterface.deleteEmployee("Peter");

        //Response<Void> response = call.execute();

        //int x = 10;

    }

}
