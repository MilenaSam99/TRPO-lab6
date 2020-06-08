package models;

public class Post {

    private String first_name;
    private String last_name;
    private String gender;
    private String dob;
    private String email;
    private String phone;
    private String id;

    public Post( String first_name, String last_name, String gender, String dob, String email, String phone)
    {
        this.first_name=first_name;
        this.last_name=last_name ;
        this.gender=gender;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
    }
    //
    public String getFirst_name()
    {
        return  first_name;
    }
    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }
    //
    public String getLast_name()
    {
        return last_name;
    }
    public void setLast_name()
    {
        this.last_name = last_name;
    }
    //
    public String getGender()
    {
        return gender;
    }
    public void setGender()
    {
        this.gender = gender;
    }
    //
    public String getDob()
    {
        return dob;
    }
    public void setDob()
    {
        this.dob = dob;
    }
    //
    public String getEmail()
    {
        return  email;
    }
    public void setEmail()
    {
        this.email = email;
    }
    //
    public String getPhone()
    {
        return phone;
    }
    public void setPhone()
    {
        this.phone = phone;
    }
    //
    public String getId()
    {
        return  id;
    }
    public void setId()
    {
        this.id = id;
    }
}
