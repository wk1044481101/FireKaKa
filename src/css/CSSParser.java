package css;

import html.Parser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class CSSParser extends Parser {

    public CSSParser() {

    }

    public Stylesheet parse(String input) {
        this.input = input;
        return new Stylesheet(parseRules());
    }

    private ArrayList<Rule> parseRules() {

        ArrayList<Rule> rules = new ArrayList<>();
        while (!finish()) {
            rules.add(parseRule());
        }
        return rules;
    }

    private Rule parseRule() {
        return new Rule(parseSelectors(), parseDeclarations());
    }

    private ArrayList<Declaration> parseDeclarations() {

        ArrayList<Declaration> declarations = new ArrayList<>();
        assert currentChar() == '{';
        consumeChar();
        while (true) {
            consumeWhiteSpace();
            if (currentChar() == '}') {
                break;
            }
            String key = consumeWhile(c -> currentChar() != c, ':');
            assert consumeChar() == ':';
            consumeWhiteSpace();
            String value = consumeWhile(c -> currentChar() != c, ';');
            assert consumeChar() == ';';
            declarations.add(new Declaration(key,value));
        }
        assert consumeChar() == '}';
        return declarations;
    }

    private Selector parseSelector() {
        Selector selector = new Selector("", null, "");
        label:
        while (!finish()) {
            if (Pattern.matches("[A-Za-z0-9]", String.valueOf(currentChar()))) {
                selector.tagName = parseIdentifier();
            }
            switch (currentChar()) {
                case '#':
                    consumeChar();
                    selector.id = parseIdentifier();
                    break;
                case '.':
                    consumeChar();
                    selector.className.add(parseIdentifier());
                    break;
                case '*':
                    consumeChar();
                    selector.tagName = "*";
                    break;
                case ',':
                case ' ':
                    consumeChar();
                    break label;
            }
        }
        return selector;
    }

    private ArrayList<Selector> parseSelectors() {
        ArrayList<Selector> selectors = new ArrayList<>();
        while (true) {
            consumeWhiteSpace();
            if (finish() || startWith(1, new char[]{'{'})) {
                break;
            }
            selectors.add(parseSelector());
        }
        return selectors.stream().sorted(Comparator.comparingInt(Selector::getSpecificity)).collect(Collectors.toCollection(ArrayList<Selector>::new));
    }

    private String parseIdentifier() {
        StringBuilder stringBuilder = new StringBuilder();
        while (Pattern.matches("[A-Za-z0-9]", String.valueOf(currentChar()))) {
            stringBuilder.append(consumeChar());
        }
        return stringBuilder.toString();
    }

}
