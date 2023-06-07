package db;

import model.Address;
import model.Coordinates;
import model.Organization;
import model.OrganizationType;
import services.CurrentUserManager;
import sql.SQLConnection;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;


public class DataBaseProvider {
    private final SQLConnection sqlConnection;
    private final LocalDateTime creationDate;
    private final String pepper = "*61&^dQLC(#";
    private CurrentUserManager userManager;
    private Set<Organization> dataSet;

    public DataBaseProvider(CurrentUserManager userManager) {
        this.sqlConnection = new SQLConnection();
        this.userManager = userManager;
        this.dataSet = loadDataBase();
        this.creationDate = LocalDateTime.now();
    }

    private static String saltBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            stringBuilder.append((char) new Random().nextInt(33, 126));
        }
        return stringBuilder.toString();
    }

    public static String md5encoding(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static<T> void shuffle(List<T> list) {
        Random random = new Random();
        for (int i = list.size() - 1; i >= 1; i--)
        {
            int j = random.nextInt(i + 1);

            T obj = list.get(i);
            list.set(i, list.get(j));
            list.set(j, obj);
        }
    }
    public List<Organization> getList(){
        return dataSet.stream().toList();
    }
    public List<Organization> shuffleDataList(){
        List<Organization> shuffled = getList();
        shuffle(shuffled);
        return shuffled;
    }

    public int addOrganizationToDB(Organization request){
        int id = -1;
        try {
            request.setCreator(userManager.getUserName());

            String queryId = "SELECT coalesce(MAX(organization_id) +1, 1) FROM organizations";
            PreparedStatement statement0 = sqlConnection.getConnection().prepareStatement(queryId);
            ResultSet rs = statement0.executeQuery();
            int maxid = 0;
            while (rs.next()){
                maxid = rs.getInt("coalesce");
            }
            statement0.close();
            request.setCreator(userManager.getUserName());

            String query = "INSERT INTO organizations VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) returning organization_id";
            PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
            statement.setInt(1, maxid);
            statement.setString(2, request.getName());
            statement.setInt(3, addCoordinatesToDB(request.getCoordinates()));
            statement.setDate(4, new java.sql.Date(request.getCreationDate().getTime()));
            statement.setLong(5, request.getAnnualTurnover());
            statement.setLong(6, request.getEmployeesCount());
            statement.setString(7, request.getType().toString());
            statement.setString(8, request.getOfficialAddress().getZipCode());
            statement.setString(9, request.getCreator());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt("organization_id");
            }
            statement.close();
            request.setId(id);
            dataSet.add(request);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean updateOrganization(Organization request, int id){
        try {
            String query = "UPDATE organizations SET name = ?, coordinates_id = ?, creation_time = ?, " +
                    "annual_turnover = ?, employees_count = ?, type = ?, zip_code = ?, creator_name = ? " +
                    "WHERE organization_id = ?";
            PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
            statement.setString(1, request.getName());
            statement.setInt(2, addCoordinatesToDB(request.getCoordinates()));
            statement.setDate(3, new java.sql.Date(request.getCreationDate().getTime()));
            statement.setLong(4, request.getAnnualTurnover());
            statement.setLong(5, request.getEmployeesCount());
            statement.setString(6, request.getType().toString());
            statement.setString(7, request.getOfficialAddress().getZipCode());
            statement.setString(8, request.getCreator());
            statement.setInt(9, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows <= 0){
                return false;
            }
            statement.close();
            updateDataSet();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean removeOrganizationById(int id){
        try {
            String query = "DELETE from organizations WHERE organization_id = ? AND creator_name = ?";
            PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, userManager.getUserName());

            int affectedRows = statement.executeUpdate();
            if (affectedRows <= 0){
                return false;
            }
            dataSet.removeIf(p -> p.getCreator().equals(userManager.getUserName()) && p.getId() == id);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean clearOrganization(){
        int before = dataSet.size();
        dataSet.stream().filter(p -> p.getCreator().equals(userManager.getUserName()))
                .map(Organization::getId)
                .forEach(this::removeOrganizationById);
        dataSet.removeIf(p -> p.getCreator().equals(userManager.getUserName()));
        return (before - dataSet.size()) > 0;
    }

    public Coordinates getCoordinates(int id) {
        Coordinates coordinates = new Coordinates();
        try {
            String query = "SELECT coordinate_x, coordinate_y FROM coordinates WHERE coordinates_id = ?";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                coordinates.setX(resultSet.getDouble("coordinate_x"));
                coordinates.setY(resultSet.getDouble("coordinate_y"));
            }
            preparedStatement.close();
            return coordinates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int addCoordinatesToDB(Coordinates coordinates) {
        int coordinatesId = -1;
        try {
            String queryId = "SELECT coalesce(MAX(coordinates_id) +1, 1) FROM coordinates";
            PreparedStatement statement = sqlConnection.getConnection().prepareStatement(queryId);
            ResultSet rs = statement.executeQuery();
            int id = 0;
            while (rs.next()){
                id = rs.getInt("coalesce");
            }
            statement.close();

            String query = "INSERT INTO coordinates VALUES (?, ?, ?) RETURNING coordinates_id";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setDouble(2, coordinates.getX());
            preparedStatement.setDouble(3, coordinates.getY());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                coordinatesId = resultSet.getInt("coordinates_id");
            }
            preparedStatement.close();
            return coordinatesId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Set<Organization> updateDataSet() {
        dataSet = loadDataBase();
        return dataSet;
    }

    private Set<Organization> loadDataBase(){
        Set<Organization> dbSet = new LinkedHashSet<>();
        try {
            String query = "SELECT * FROM organizations";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Organization response = new Organization();
                response.setId(rs.getInt("organization_id"));
                response.setName(rs.getString("name"));
                response.setCoordinates(getCoordinates(rs.getInt("coordinates_id")));
                response.setCreationDate(rs.getDate("creation_time"));
                response.setAnnualTurnover(rs.getLong("annual_turnover"));
                response.setEmployeesCount(rs.getLong("employees_count"));
                response.setType(OrganizationType.valueOf(rs.getString("type")));
                response.setOfficialAddress(new Address(rs.getString("zip_code")));
                response.setCreator(rs.getString("creator_name"));
                dbSet.add(response);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dbSet;
    }

    private String getPassword(String userName) {
        String pass = "";
        try {
            String query = "SELECT password FROM users WHERE user_name = ?";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pass = (resultSet.getString("password"));
            }
            preparedStatement.close();
            return pass;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void userRegister(String username, String password) {
        try {
            String salt = saltBuilder();
            String query = "INSERT INTO users VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, md5encoding(pepper + password + salt));
            preparedStatement.setString(3, salt);


            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Ошибка! Ничего не изменилось.");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String getSalt(String userName) {
        String salt = "";
        try {
            String query = "SELECT salt FROM users WHERE user_name = ?";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                salt = (resultSet.getString("salt"));
            }
            preparedStatement.close();
            return salt;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkUserPassword(String username, String password) {
        String passwordHash = md5encoding(pepper + password + getSalt(username));
        assert passwordHash != null;
        return passwordHash.equals(getPassword(username));
    }
    public Set<String> getUserNameList() {
        Set<String> userList = new HashSet<>();
        try {
            String query = "SELECT user_name FROM users";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(resultSet.getString("user_name"));
            }
            preparedStatement.close();
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void InfoOfCollection() {
        System.out.println("Тип коллекции: " + dataSet.getClass()
                + " дата инициализации: " + creationDate
                + " количество элементов: " + dataSet.size());
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public CurrentUserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(CurrentUserManager userManager) {
        this.userManager = userManager;
    }

    public Set<Organization> getDataSet() {
        return dataSet;
    }
}

