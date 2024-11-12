package model;
import java.sql.Date;

public class Banquet {
    public int id, quota;
    public String name, address, location, contactStaffName;
    public Date date;
    public boolean available;

    Banquet(int id, String name, Date date, String address, String location, String contactStaffName, boolean available, int quota) {
        this.id = id;
        this.quota = quota;
        this.name = name;
        this.address = address;
        this.location = location;
        this.contactStaffName = contactStaffName;
        this.date = date;
        this.available = available;
    }

}
