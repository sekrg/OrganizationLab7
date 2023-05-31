package model;

/**
 * The type Address.
 */
public class Address {
    /**
     * The Get zip code.
     */
    private String zipCode; //Поле не может быть null

    /**
     * Instantiates a new Address.
     *
     * @param zipCode the zip code
     */
    public Address(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets zip code.
     *
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets zip code.
     *
     * @param zipCode the zip code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Instantiates a new Address.
     */
    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "zipCode='" + zipCode + '\'' +
                '}';
    }
}

