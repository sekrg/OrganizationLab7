package utils;


import model.Address;
import model.Coordinates;
import model.Organization;
import model.OrganizationType;

import java.sql.Date;
import java.time.Instant;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Utilities.
 */
public class Utilities {
    /**
     * Read organization organization.
     *
     * @return the organization
     */
    public static Organization readOrganization() {
        Scanner scanner = new Scanner(System.in);
        Organization organization = new Organization();
        System.out.println("Введите имя организации:");
        organization.setName(getName(scanner));
        System.out.println("Введите координаты организации(2 координаты double, Double):");
        organization.setCoordinates(new Coordinates(((double) getDoubleId(scanner)), (getDoubleId(scanner))));
        organization.setCreationDate(Date.from(Instant.now()));
        System.out.println("Введите годовой оборот организации");
        organization.setAnnualTurnover((getId(scanner)));
        System.out.println("Введите кол-во сотрудников:");
        organization.setEmployeesCount(((getLongId(scanner))));
        System.out.println("Введите тип организации(PUBLIC,GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY");
        organization.setType(getType(scanner));
        System.out.println("Введите адрес организации(имя):");
        organization.setOfficialAddress(new Address(getName(scanner)));
        return organization;
    }

    //<item><name>name</name></item>
    public static Organization fromStringToOrganization(String line) {
        Organization organization = new Organization();
        organization.setName(getNameFromXmlString(line));
        organization.setCoordinates(getCoordinatesFromXmlString(line));
        organization.setAnnualTurnover(getAnnualTurnoverFromXmlString(line));
        organization.setEmployeesCount(getEmployeesCountFromXmlString(line));
        organization.setType(getOrganizationTypeFromXmlString(line));
        organization.setOfficialAddress(getAddresFromXmlString(line));
        return organization;
    }

    private static String getNameFromXmlString(String line) {
        Pattern pattern = Pattern.compile("<name>(.*?)</name>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    private static Coordinates getCoordinatesFromXmlString(String line) {
        Pattern pattern = Pattern.compile("<Coordinates>(.*?)</Coordinates>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Coordinates.fromStringToCoordinates(matcher.group(1));
        } else {
            return null;
        }
    }

    private static long getAnnualTurnoverFromXmlString(String line) {
        Pattern pattern = Pattern.compile("<AnnualTurnover>(.*?)</AnnualTurnover>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        } else {
            return 0;
        }
    }

    private static long getEmployeesCountFromXmlString(String line) {
        Pattern pattern = Pattern.compile("<EmployeesCount>(.*?)</EmployeesCount>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        } else {
            return 0;
        }
    }

    private static OrganizationType getOrganizationTypeFromXmlString(String line) {
        Pattern pattern = Pattern.compile("<OrganizationType>(.*?)</OrganizationType>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return fromStringToOrganizationType(matcher.group(1));
        } else {
            return OrganizationType.PUBLIC;
        }
    }
    private static Address getAddresFromXmlString(String line) {
        Pattern pattern = Pattern.compile("<Address>(.*?)</Address>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return new Address(matcher.group(1));
        } else {
            return new Address("0");
        }
    }

    public static OrganizationType fromStringToOrganizationType(String line) {
        switch (line.toUpperCase()) {
            case ("PUBLIC") -> {
                return OrganizationType.PUBLIC;
            }
            case "GOVERNMENT" -> {
                return OrganizationType.GOVERNMENT;
            }
            case "TRUST" -> {
                return OrganizationType.TRUST;
            }
            case "PRIVATE_LIMITED_COMPANY" -> {
                return OrganizationType.PRIVATE_LIMITED_COMPANY;
            }
            default ->{
                return OrganizationType.PUBLIC;
            }
        }
    }

    private static long getId(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine();
            try {
                long l = Long.parseLong(s);
                if (l > 0) {
                    return l;
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input!");
            }
        }
    }

    private static Double getDoubleId(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine();
            try {
                Double l = Double.valueOf(s);
                if (s.length() > 0) {
                    return l;
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input!");
            }
        }
    }

    private static Long getLongId(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine();
            try {
                Long l = Long.valueOf(s);
                if (s.length() > 0 && l.longValue() > 0) {
                    return l;
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input!");
            }
        }
    }

    private static OrganizationType getType(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine();
            try {
                if (s != null && s.length() > 0) {
                    return OrganizationType.valueOf(s.toUpperCase(Locale.ENGLISH).trim());
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect input!");
            }
        }
    }

    private static String getName(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine();
            if (s != null && s.length() > 0) {
                return s;
            }

        }
    }
}