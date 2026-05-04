package User;

import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserDataProvider {
    @DataProvider(name= "userData")
    public Object[][] getUserData(){
        return new Object[][]{
                {1,"kvico","luka","ksmith","smth@gmail.com","psswd124", "321321421",0},
                {2,"shaleqs","shalva","shalikodiani","smth1@gmail.com","psswd125", "321321421",0},
                {3,"undon","gio","samth","smth2@gmail.com","psswd123", "321321421",0},
        };
    }

}
