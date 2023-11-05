import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Resume {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int experience;
    private String education;

    public Resume() {
        // Default constructor
    }

    public Resume(String name, String email, String phone, int experience, String education) {
        this.name = name;
        
        if (isEmailValid(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email address");
        }

        if (isPhoneNumberValid(phone)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Invalid phone number");
        }

        this.experience = experience;
        this.education = education;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isEmailValid(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (isPhoneNumberValid(phone)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 10) {
            return false;
        }

        char firstDigit = phoneNumber.charAt(0);
        return (firstDigit == '9' || firstDigit == '8' || firstDigit == '7');
    }

    public boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
