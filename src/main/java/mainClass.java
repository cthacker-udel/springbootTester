import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

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

    public static void printMenu(){

        System.out.println("-=-=-=MENU-=-=-=\n\n--- Employee Methods ---\n1)Add Employee\n2)Get Employee\n3)Get All Employees\n4)Remove Employee\n5)Remove All Employees\n6)Employee Count\n7)Update Employee\n--- Server Methods ---\n8)Create collection\n9)Get Collection Names\n10)Get Collection Object\n11)Test Collection Existence\n--- Admin Methods ---\n12)Create Admin\n13)List All Admin\n14)Update Admin\n15)Get Admin\n16)Remove Admin\n17)Get Admin Count\n18)Remove All Admin\n19)List Admin Names\n20)Get Admin(s) by Name\n--- Soccer Methods\n21)Get Soccer Player by First Name\n22)Exit Program\n-=-=-=-=-=-=-=-=");

    }

    public static void main(String[] args) throws IOException {

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
