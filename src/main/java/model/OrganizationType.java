package model;

/**
 * The enum Organization type.
 */
public enum OrganizationType {
    PUBLIC(1),
    GOVERNMENT(2),
    TRUST(3),
    PRIVATE_LIMITED_COMPANY(4);

    int i;
    OrganizationType(int i) {
        this.i = i;
    }
    public int getValue() {
        return i;
    }

}