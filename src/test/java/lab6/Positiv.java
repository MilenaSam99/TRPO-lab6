package lab6;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import models.Post;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Positiv {

    private String token;
    private int id;
    @BeforeClass
    public void beforeClass()
    {
        RestAssured.baseURI = "https://gorest.co.in/";
        token = "292x0zQQncXr4vFVA0LBUkE-7SPKBsQxtBK_";
    }

    //выводим список всех пользователей
    @Test
    public void task1()
    {
        System.out.println("\nВыводим список всех пользователей! ");
        given().auth().oauth2(token)
                .when().get("public-api/users")
                .then().log().all()
                .statusCode(200);
    }

    //првайдер данных с именем Рик
    @DataProvider(name = "data1")
    public Object[][] data1() {return new Object[][]{{"Rikk"}};}

    //выводим пользователей, указанных по имени
    @Test(dataProvider = "data1")
    public void task2(String first_name)
    {
        System.out.println("\nВыводим пользователя с именем 'Рик'! ");
        given().auth().oauth2(token)
                .when().get("public-api/users?first_name=" + first_name)
                .then().log().all()
                .assertThat()

                .statusCode(200);
    }

    //создание новошо пользователя
    @DataProvider(name = "data2")
    public Object[][] data2()
    {
        return new Object[][] {{"Rikk", "Sanches", "male", "2000-10-11", Math.random() + "@example.com", "+8 (495) 252-34-62"}};
    }

    @Test(dataProvider = "data2")
    public void task3( String first_name, String last_name, String gender, String dob, String email, String phone)
    {
        System.out.println("\nСоздаем нового пользователя! ");
        Post var = new Post(first_name, last_name,gender, dob, email, phone);
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(var)
                .when().post("public-api/users")
                .then().log().all()

                .statusCode(302);
    }

    //получаем пользователя по ID
    @DataProvider(name = "data3")
    public Object[][] data3() {return new Object[][]{{"0"},{"31364"}};}

    @Test(dataProvider = "data3")
    public void task4(String id)
    {
        System.out.println("\nПользователь ");
        given().auth().oauth2(token)
                .when().get("public-api/users/" + id)
                .then()
                .log().all()
                .statusCode(200);
    }

    //изменение пользователя по ID
    @DataProvider(name = "data4")
    public Object[][] data4()
    {
        return new Object[][] {{31364,"Morti", "Sanches", "male", "2010-10-11", Math.random() + "@example.com", "+8 (495) 252-34-62"}};
    }

    @Test(dataProvider = "data4")
    public void task5(int id, String first_name, String last_name, String gender, String dob, String email, String phone )
    {
        System.out.println("Измененый пользователь! ");
        Post var =  new Post( first_name, last_name,gender, dob, email, phone);
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(var)
                .when().put("public-api/users/" + id)
                .then()
                .log().all()

                .statusCode(200);
    }

    //удаление пользователя по ID
    @DataProvider(name = "data5")
    public Object[][] data5()
    {
        Post my_Post = new Post( "Name", "Surname","female", "dob", Math.random()+"@gmail.com", "phone");
        JsonPath response =
                given().auth().oauth2(token).contentType(ContentType.JSON)
                        .body(my_Post)
                        .when().post("public-api/users")
                        .then().extract().jsonPath();
        int id=response.getInt("result.id");
        return  new Object[][]{
                {id}
        };
    }

    @Test(dataProvider = "data5")
    public void task6(int id) {
        System.out.println("\nУдаление пользователя! ");
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .when().delete("public-api/users/" + id)
                .then().log().all()
                .body("result", equalTo(null))

                .statusCode(200);
    }
}
