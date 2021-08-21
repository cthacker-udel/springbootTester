import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface requestInterface {

    /*

    Employee Requests

     */

    @POST("http://localhost:8080/employee")
    Call<Object> createEmployee(@Body Map<String,Object> map);

    @GET("http://localhost:8080/employee")
    Call<Object> getEmployee(@Query("name") String name);

    @GET("http://localhost:8080/employees")
    Call<Object> getEmployees();

    @DELETE("http://localhost:8080/employee/{employeeName}")
    Call<Void> deleteEmployee(@Path("employeeName") String employeeName);

    @DELETE("http://localhost:8080/employee/all")
    Call<Void> deleteAllEmployees();

    @GET("http://localhost:8080/employee/count")
    Call<Long> getEmployeeCount();

    @PUT("http://localhost:8080/employee/{employeeId}")
    Call<Void> updateEmployee(@Body Map<String,Object> body, @Path("employeeId") Integer employeeId);

    /*

    Server Requests

     */

    @POST("http://localhost:8080/admin/create/{collectionName}")
    Call<Void> createCollection(@Path("collectionName") String collectionName);

    @GET("http://localhost:8080/admin/list/collections")
    Call<Set<String>> getCollectionNames();

    @GET("http://localhost:8080/admin/get/{collectionName}")
    Call<Void> getCollection(@Path("collectionName") String collectionName);

    @GET("http://localhost:8080/admin/exists/collection/{collectionName}")
    Call<Void> doesCollectionExist(@Path("collectionName") String collectionName);

    /*

    Admin Requests

     */

    @POST("http://localhost:8080/admin")
    Call<Void> createAdmin(@Body Map<String,Object> body);

    @GET("http://localhost:8080/admin/all")
    Call<Void> listAllAdmin();

    @GET("http://localhost:8080/admin/names")
    Call<List<String>> getAdminNames();

    @PUT("http://localhost:8080/admin/{adminId}")
    Call<Void> updateAdmin(@Path("adminId") Integer adminId, @Body Map<String,Object> body);

    @GET("http://localhost:8080/admin/{adminId}")
    Call<Void> getAdmin(@Path("adminId") Integer adminId);

    @DELETE("http://localhost:8080/admin/{adminId}")
    Call<Void> removeAdmin(@Path("adminId") Integer adminId);

    @GET("http://localhost:8080/admin/count")
    Call<Long> getAdminCount();

    @DELETE("http://localhost:8080/admin")
    Call<Void> removeAllAdmin();

    @GET("http://localhost:8080/admin/name/{adminName}")
    Call<List<Object>> getAdminByName(@Path("adminName") String adminName);

    /*

    Soccer Player Requests

     */

    @GET("http://localhost:8080/soccer/name/first/{firstName}")
    Call<Object> getSoccerPlayerByFirstName(@Path("firstName") String firstName);

    @GET("http://localhost:8080/soccer/name/last/{lastName}")
    Call<Object> getSoccerPlayerByLastName(@Path("lastName") String lastName);

    @PUT("http://localhost:8080/soccer/name/last/{lastName}")
    Call<Object> updateByLastName(@Path("lastName") String lastName, @Body Map<String,Object> body);

    @GET("http://localhost:8080/soccer/yellow_cards/{yellowCards}")
    Call<Object> getSoccerPlayerByYellowCards(@Path("yellowCards") Integer yellowCards);

    @GET("http://localhost:8080/soccer/red_cards/{redCards}")
    Call<Object> getSoccerPlayerByRedCards(@Path("redCards") Integer redCards);

    @GET("http://localhost:8080/soccer/date/{DOB}")
    Call<Object> getSoccerPlayerByDOB(@Path("DOB") String DOB);

    @DELETE("http://localhost:8080/soccer/removeAll")
    Call<Object> removeAllSoccerPlayers();

    @POST("http://localhost:8080/soccer")
    Call<Object> addSoccerPlayer(@Body Map<String,Object> soccerPlayer);

    @POST("http://localhost:8080/user")
    Call<Object> addUser(@Body Map<String,Object> user);

    @GET("http://localhost:8080/user/username/{userName}")
    Call<Object> getUser(@Path("userName") String userName, @Query("auth") String auth);

    @GET("http://localhost:8080/user/apikey/{apiKey}")
    Call<Object> getUserApiKey(@Path("apiKey") String apiKey, @Query("password") String password);

    @GET("http://localhost:8080/user/username/{userName}")
    Call<Object> getUserUserName(@Path("userName") String userName, @Query("auth") String secretKey);

    @GET("http://localhost:8080/user/get/password/{thepass}")
    Call<Object> getUserPassword(@Path("thepass") String thePass, @Query("signature") String secretKey);

}
