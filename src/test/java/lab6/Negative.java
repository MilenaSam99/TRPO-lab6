package lab6;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.Post;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Negative {

    private String token;
    @BeforeClass
    public void beforeClass() {
        RestAssured.baseURI = "https://gorest.co.in/";
        token = "292x0zQQncXr4vFVA0LBUkE-7SPKBsQxtBK_";
    }

    //пустой токен
    @Test
    public void test1()
    {
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("\nПустой токен! ");
        String empty_token = "";
        given().auth().oauth2(empty_token)
                .when().get("public-api/users")
                .then().log().all()

                .statusCode(200);
        System.out.println("Результат: 401");
    }

    //некорректный токен
    @Test
    public void test2()
    {
        System.out.println("\nНекорретный токен ");
        String uncorrect_token = ".......";
        int number = 401;
        given().auth().oauth2(uncorrect_token)
                .when().get("public-api/users")
                .then().log().all()
                .body("_meta.code", equalTo(number))
                .statusCode(200);
        System.out.println("Результат: 401");
    }

    //регистрация на существующую почту
    @DataProvider(name = "data1")
    public Object[][] data1()
    {
        return new Object[][] {{"name1", "name2", "female", "2000-00-00", "roberto39@example.org", "+8 900 123-324-41"}};
    }

    @Test(dataProvider = "data1")
    public void test3(String first_name, String last_name, String gender, String dob, String email, String phone)
    {
        System.out.println("\nРегистрация на существующий мэил! ");
        Post my_post = new Post( first_name, last_name,gender, dob, email, phone);
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(my_post)
                .when().post("public-api/users")
                .then().log().all()
                .body("_meta.code", equalTo(422))

                .statusCode(200);
        System.out.println("Текущее значение: 422");
        System.out.println("Ожидаемое значение: 422");
    }

    //удаление по айди
    @DataProvider(name = "data2")
    public Object [][] data2()
    {
        return new Object[][] {{00}};
    }
    @Test(dataProvider = "data2")
    public void test4(int id)
    {
        System.out.println("Неправильное значений! ");
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .when().delete("public-api/users/" + id)
                .then().log().all()
                .body("_meta.code", equalTo(404))

                .statusCode(200);
        System.out.println("Результат: 404");
    }
}
