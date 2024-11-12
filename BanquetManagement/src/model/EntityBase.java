package model;
import java.sql.*;

public class EntityBase {
    protected final DataBaseConnection dbConnection;

    public EntityBase() {
        this.dbConnection = new DataBaseConnection();
    }

}
