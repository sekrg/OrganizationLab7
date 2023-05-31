package model;

import java.time.Instant;
import java.util.Date;

/**
 * The type Organization.
 */
public class Organization {
    private int id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long annualTurnover; //Значение поля должно быть больше 0
    private Long employeesCount; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address officialAddress; //Поле не может быть null
    private String creator;

    /**
     * Instantiates a new Organization.
     *
     * @param id              the id
     * @param name            the name
     * @param creationDate    the creation date
     * @param coordinates     the coordinates
     * @param annualTurnover  the annual turnover
     * @param employeesCount  the employees count
     * @param type            the type
     * @param officialAddress the official address
     */
    public Organization(int id, String name, Date creationDate, Coordinates coordinates, long annualTurnover, Long employeesCount, OrganizationType type, Address officialAddress) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.type = type;
        this.officialAddress = officialAddress;
    }

    @Override
    public String toString() {
        return "Organization{" + "\n" +
                "id=" + id + "\n" +
                "creator=" + creator + "\n" +
                "name='" + name + '\'' + "\n" +
                "coordinates=" + coordinates + "\n" +
                "creationDate=" + creationDate + "\n" +
                "annualTurnover=" + annualTurnover + "\n" +
                "employeesCount=" + employeesCount + "\n" +
                "type=" + type + "\n" +
                "officialAddress=" + officialAddress + "\n" +
                '}' + "\n" ;
    }

    /**
     * Instantiates a new Organization.
     */
    public Organization() {
        creationDate = java.sql.Date.from(Instant.now());
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets annual turnover.
     *
     * @return the annual turnover
     */
    public long getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * Sets annual turnover.
     *
     * @param annualTurnover the annual turnover
     */
    public void setAnnualTurnover(long annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    /**
     * Gets employees count.
     *
     * @return the employees count
     */
    public Long getEmployeesCount() {
        return employeesCount;
    }

    /**
     * Sets employees count.
     *
     * @param employeesCount the employees count
     */
    public void setEmployeesCount(Long employeesCount) {
        this.employeesCount = employeesCount;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public OrganizationType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(OrganizationType type) {
        this.type = type;
    }

    /**
     * Gets official address.
     *
     * @return the official address
     */
    public Address getOfficialAddress() {
        return officialAddress;
    }

    /**
     * Sets official address.
     *
     * @param officialAddress the official address
     */
    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}