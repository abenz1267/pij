package entities.location;

import com.j256.ormlite.table.DatabaseTable;
import entities.GenericSingle;

/**
 * Location entity
 *
 * @author Andrej Benz
 */
@DatabaseTable()
public class Location extends GenericSingle {

    @Override
    public String toString() {
        return this.getName();
    }
}
