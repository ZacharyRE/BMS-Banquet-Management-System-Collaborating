package model;

public class EntityManager {
    protected final DataBaseConnection dbConnection;

    public EntityManager() {
        dbConnection = new DataBaseConnection();
    }
}
