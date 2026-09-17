package model;

public class Passenger extends Person {
    private String passengerId;
    private int age;
    private String gender;

    public Passenger(String passengerId, String name, int age, String gender) {
        super(name);
        this.passengerId = passengerId;
        this.age = age;
        this.gender = gender;
    }

    public String getPassengerId() { return passengerId; }
    public int getAge() { return age; }
    public String getGender() { return gender; }

    @Override
    public String getRoleDetails() {
        return "Role: Passenger";
    }

    public String toFileString() {
        return passengerId + "|" + name + "|" + age + "|" + gender;
    }
}