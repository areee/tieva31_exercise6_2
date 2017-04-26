import java.util.*;
import java.io.*;
// import java.text.*;

/**
 * This class is a basic object containing information
 * of a single person. First and last name, birth town and
 * country are stored as String. Birth year is an integer
 * and marital status is an enumeration.
 * 
 * The class static methods for loading and saving a
 * java.util.List<Person> collection.
 * 
 * @author Jaakko
 */
public class Person extends Observable {

    public static void main(String[] args) {
        String fileName = args[0];
        System.out.println("Loading persons from file " + fileName);
        List<Person> people = Person.loadPersons(fileName);
        for (Person p : people) {
            System.out.println(p);
        }
    }

    public enum MaritalStatus {
        SINGLE, COHABITING, MARRIED_REGISTERED, WIDOW
    };

    public static String PERSON_START_LINE = "PERSON";
    public static String FIRST_NAME_HEADER = "firstName:";
    public static String LAST_NAME_HEADER = "lastName:";
    public static String BIRTH_TOWN_HEADER = "birthTown:";
    public static String BIRTH_COUNTRY_HEADER = "birthCountry:";
    public static String BIRTH_YEAR_HEADER = "birthYear:";
    public static String MARITAL_STATUS_HEADER = "maritalStatus:";

    String firstName;
    String lastName;
    int birthYear;
    String birthTown;
    String birthCountry;
    MaritalStatus maritalStatus;

    @Override
    public String toString() {
        String r
                = (firstName != null ? firstName : "null") + " "
                + (lastName != null ? lastName : "null") + ", born "
                + Integer.toString(birthYear) + " in "
                + (birthTown != null ? birthTown : "null") + ", "
                + (birthCountry != null ? birthCountry : "null");
        if (maritalStatus != null) {
            r += ", relationship status is ";
            switch (maritalStatus) {
                case SINGLE:
                    r += "single";
                    break;
                case COHABITING:
                    r += "living together";
                    break;
                case MARRIED_REGISTERED:
                    r += "in registered relationship";
                    break;
                case WIDOW:
                    r += "widowed";
                    break;
            }
        }
        return r;
    }

    /**
     * This methods save the given list of persons to the file of given name.
     */
    public static void savePersons(List<Person> persons, String file) {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            for (Person p : persons) {
                writer.write(PERSON_START_LINE + "\n");
                writer.write(FIRST_NAME_HEADER + p.getFirstName() + "\n");
                writer.write(LAST_NAME_HEADER + p.getLastName() + "\n");
                writer.write(BIRTH_TOWN_HEADER + p.getBirthTown() + "\n");
                writer.write(BIRTH_COUNTRY_HEADER + p.getBirthCountry() + "\n");
                writer.write(MARITAL_STATUS_HEADER + p.getMaritalStatus() + "\n");
                writer.write(BIRTH_YEAR_HEADER + Integer.toString(p.getBirthYear()) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This methods reads a list of persons from given file and returns the
     * resulting Person list.
     */
    public static List<Person> loadPersons(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return Person.loadPersons(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This methods reads a list of persons from InputStream, closes the stream
     * and returns the resulting Person list.
     */
    public static List<Person> loadPersons(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            List<Person> r = new LinkedList<Person>();
            Person current = null;
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith(PERSON_START_LINE)) {
                    if (current != null) {
                        r.add(current);
                    }
                    current = new Person();
                }
                if (line.startsWith(FIRST_NAME_HEADER)) {
                    if (current != null) {
                        current.setFirstName(line.substring(FIRST_NAME_HEADER.length()).trim());
                    }
                }
                if (line.startsWith(LAST_NAME_HEADER)) {
                    if (current != null) {
                        current.setLastName(line.substring(LAST_NAME_HEADER.length()).trim());
                    }
                }
                if (line.startsWith(BIRTH_TOWN_HEADER)) {
                    if (current != null) {
                        current.setBirthTown(line.substring(BIRTH_TOWN_HEADER.length()).trim());
                    }
                }
                if (line.startsWith(BIRTH_COUNTRY_HEADER)) {
                    if (current != null) {
                        current.setBirthCountry(line.substring(BIRTH_COUNTRY_HEADER.length()).trim());
                    }
                }
                if (line.startsWith(BIRTH_YEAR_HEADER)) {
                    if (current != null) {
                        try {
                            int year = Integer.parseInt(line.substring(BIRTH_YEAR_HEADER.length()).trim());
                            current.setBirthYear(year);
                        } catch (NumberFormatException e) {
                            System.out.println("Error when parsing birth year " + line);
                        }
                    }
                }
                // marital status
                if (line.startsWith(MARITAL_STATUS_HEADER)) {
                    if (current != null) {
                        String status = line.substring(MARITAL_STATUS_HEADER.length()).trim();
                        for (MaritalStatus s : MaritalStatus.values()) {
                            if (status.equals(s.toString())) {
                                current.setMaritalStatus(s);
                                break;
                            } // if
                        } // for
                    }
                }
                line = reader.readLine();
            } // while more lines in the file
            if (current != null) {
                r.add(current);
            }
            reader.close();
            return r;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the value of maritalStatus.
     *
     * @return the value of maritalStatus
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Set the value of maritalStatus
     *
     * @param birthCountry new value of maritalStatus
     */
    public void setMaritalStatus(MaritalStatus status) {
        maritalStatus = status;
        setChanged();
    }

    /**
     * Get the value of birthCountry
     *
     * @return the value of birthCountry
     */
    public String getBirthCountry() {
        return birthCountry;
    }

    /**
     * Set the value of birthCountry
     *
     * @param birthCountry new value of birthCountry
     */
    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
        setChanged();
    }

    /**
     * Get the value of birthTown
     *
     * @return the value of birthTown
     */
    public String getBirthTown() {
        return birthTown;
    }

    /**
     * Set the value of birthTown
     *
     * @param birthTown new value of birthTown
     */
    public void setBirthTown(String birthTown) {
        this.birthTown = birthTown;
        setChanged();
    }

    /**
     * Get the value of birthDate
     *
     * @return the value of birthDate
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Set the value of birthDate
     *
     * @param birthDate new value of birthDate
     */
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
        setChanged();
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setChanged();
    }

    /**
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of lastName
     *
     * @param lastName new value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        setChanged();
    }

}
