package model.entities;
import java.sql.Date;

public class Banquet {
    private int BIN; // should we use long here?
    private String Name;
    private Date DateTime; // should we use date here?
    private String Address;
    private String Location;
    private String ContactStaffName;
    private boolean Available;
    private int Quota;

    public Banquet(int BIN, String Name, Date DateTime, String Address, String Location, String ContactStaffName, boolean Available, int Quota) {
        this.BIN = BIN;
        this.Name = Name;
        this.DateTime = DateTime;
        this.Address = Address;
        this.Location = Location;
        this.ContactStaffName = ContactStaffName;
        this.Available = Available;
        this.Quota = Quota;
    }
}
