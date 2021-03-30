package dom;

import java.util.*;
import java.util.stream.Collectors;

public class ElementNode extends Node {

    public ElementNode(String tagName, Map<String, String> attrs, ArrayList<Node> children) {
        super();
        nodeType = "element";
        this.tagName = tagName;
        this.attrs = attrs;
        this.children = children;
    }

    public ElementNode() {
        super();
    }

    public String getID() {
        String s=  attrs.get("id");
        return Objects.requireNonNullElse(s, "");
    }

    public Set<String> getClassName() {
        return Arrays.stream(attrs.get("class").split(" ")).collect(Collectors.toSet());
    }
}
