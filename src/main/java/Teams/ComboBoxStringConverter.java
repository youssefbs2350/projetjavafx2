package Teams;

import javafx.util.StringConverter;

import java.util.Map;

public class ComboBoxStringConverter extends StringConverter<Map.Entry<Integer, String>> {

    @Override
    public String toString(Map.Entry<Integer, String> entry) {
        if (entry != null) {
            return entry.getValue();
        } else {
            return null;
        }
    }

    @Override
    public Map.Entry<Integer, String> fromString(String string) {
        return null; // Pas nécessaire pour la conversion de chaîne en objet
    }
}
