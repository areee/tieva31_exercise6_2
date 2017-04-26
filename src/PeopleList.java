
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;

/**
 *
 * @author ylhaart
 */
public class PeopleList extends AbstractListModel<String> {

    List<Person> people;

    public PeopleList() {
        ClassLoader cl = getClass().getClassLoader();
        try {
            people = Person.loadPersons(cl.getResource("res/people.txt").openStream());
        } catch (IOException ex) {
            Logger.getLogger(PeopleList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public int getSize() {
        return people.size();
    }

    @Override
    public String getElementAt(int index) {
        Person p = people.get(index);
        return p.getFirstName() + " " + p.getLastName();
    }

}
