package model;
import java.sql.Date;

public class Banquet {
    private int BIN;
    private String Name;
    private Date DateTime;
    private String Address;
    private String Location;
    private String ContactStaffName;
    private boolean Available;
    private int quota;

    public Banquet(int BIN, String Name, Date DateTime, String Address, String Location, String ContactStaffName, boolean Available, int quota) {
        this.BIN = BIN;
        this.Name = Name;
        this.DateTime = DateTime;
        this.Address = Address;
        this.Location = Location;
        this.ContactStaffName = ContactStaffName;
        this.Available = Available;
        this.quota = quota;
    }
}
