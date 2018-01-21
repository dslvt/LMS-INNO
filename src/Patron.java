public class Patron extends User {
    public boolean isFacultyMember;
    public int debt;

    public Patron(String name, String phoneNumber, String address){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public Patron(){

    }
}
