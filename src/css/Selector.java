package css;

import java.util.ArrayList;
import java.util.Objects;

public class Selector {

    public String tagName;
    public String id;
    public ArrayList<String> className;

    public Selector(String id, ArrayList<String> className, String tagName) {
        this.id = id;
        this.className = Objects.requireNonNullElseGet(className, ArrayList::new);
        this.tagName = tagName;
    }

    public int getSpecificity() {

        System.out.println(id+"-"+tagName);

        int specificity = 0;
        if (!id.equals("")) {
            specificity += 6;
            return specificity;
        }
        if (className.size() != 0) {
            specificity += 3;
        }
        if (tagName.equals("*") || tagName.equals("")) {
            specificity += 1;
        } else {
            specificity += 2;
        }
        return specificity;

    }
}
