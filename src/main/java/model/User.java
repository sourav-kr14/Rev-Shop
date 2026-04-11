package model;

public class User {
    private int userId;
    private  String email;
    private String password;
    private  String role;
    private String securityQuestion;
    private String securityAnswer;

    public User()
    {

    }

    public User(int userId,String email,String password,String role,String securityQuestion,String securityAnswer) {
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.securityQuestion=securityQuestion;
        this.securityAnswer=securityAnswer;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    @Override
    public  String toString()
    {
        return " User Details "+"User Id"+userId +"Email    "+email+"role   "+role +"Security Question  "+securityQuestion + "Security Answer   "+securityAnswer;
    }
}
