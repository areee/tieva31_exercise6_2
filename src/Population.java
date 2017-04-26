/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Calendar;
import java.util.Random;

/**
 * Population class is a simple, generative model of a 
 * collection of Person objects. It builds a pseudo-random
 * collection of Person objects for a given country. The
 * country name is used as a seed for random generation of
 * Person objects and therefore the same People are generated
 * for each country every time.
 * 
 * Once Population instance has been created, nextPerson and
 * previousPerson methods can be used to get Person objects
 * from the ordered (generative) collection.
 * 
 * @author demo
 */
public class Population {
    static String[][] FIRST_NAME_TEMPLATE = new String[][] {
        new String[]{"D", "R", "J"},
        new String[]{"on", "aughn", "ey"},
        new String[]{"", "ald", "os"}
    };

    static String[][] LAST_NAME_TEMPLATE = new String[][] {
        new String[]{"W", "C", "S"},
        new String[]{"olver", "amp", "eig"},
        new String[]{"sue", "ton", "son"}
    };

    static String[][] TOWN_TEMPLATE = new String[][] {
        new String[]{"Lon", "Wol", "Mil", "Til"},
        new String[]{"", "tin", "ver"},
        new String[]{"ton", "ton", "don", "ville", "burgh"}
    };

    static String[] COUNTRIES = new String[] {"China", "India", "USA", "Japan", "Indonesia", "Nigeria"};
    

    public static void main(String[] args) {
        Population p = new Population("Britain");
        for (int i = 0; i < 10; i++)
            System.out.println(p.nextPerson());
    }
	
    long seed;
    String country;
    
    /**
     * Constructor creates a population model for the given country.
     * 
     * @param country 
     */
    public Population(String country) {
        this.country = country;
        seed = country.hashCode();
    }
    
    /**
     * Returns a person object and increases the current person index.
     * @return Person object 
     */
    public Person nextPerson() {
        seed++;
        return getPerson();
    }

    /**
     * Returns a person object and decreases the current person index.
     * @return 
     */
    public Person previousPerson() {
        seed--;
        return getPerson();
    }
    
    protected Person getPerson() {
        Random random = new Random(seed);
        Person r = new Person();
        String firstName = "";
        for (String[] options : FIRST_NAME_TEMPLATE)
            firstName += options[random.nextInt(options.length)];
        r.setFirstName(firstName);
        String lastName = "";
        for (String[] options : LAST_NAME_TEMPLATE)
            lastName += options[random.nextInt(options.length)];
        r.setLastName(lastName);
        r.setBirthCountry(random.nextInt(10)<9?country:COUNTRIES[random.nextInt(COUNTRIES.length)]);
        String town = "";
        for (String[] options : TOWN_TEMPLATE)
            town += options[random.nextInt(options.length)];
        r.setBirthTown(town);
        r.setBirthYear(Calendar.getInstance().get(Calendar.YEAR) - random.nextInt(100));
        r.setMaritalStatus(Person.MaritalStatus.values()[random.nextInt(Person.MaritalStatus.values().length)]);
        return r;
    }
}
