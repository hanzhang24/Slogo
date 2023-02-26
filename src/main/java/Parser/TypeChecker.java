package Parser;

import javafx.util.Pair;

import java.util.*;
import java.util.regex.Pattern;

public abstract class TypeChecker {
    private static String syntaxPath = "resources/Parser/TokenPatterns";
    private static List<Pair<TokenType, Pattern>> patterns;

    public static TokenType getType(String token) {
        for (Pair<TokenType, Pattern> p: TypeChecker.patterns) {
            Pattern pattern = p.getValue();
            TokenType tokenType = p.getKey();
            if (pattern.matcher(token).matches())
                return tokenType;
        }
        return TokenType.BAD_TYPE;
    }
    public static void initPatternMap() {
        ResourceBundle resources = ResourceBundle.getBundle(syntaxPath);
        Enumeration<String> iter = resources.getKeys();
        TypeChecker.patterns = new ArrayList<Pair<TokenType, Pattern>>();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            TokenType tokenType = TokenType.valueOf(key);
            String regex = resources.getString(key);
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            TypeChecker.patterns.add(new Pair<TokenType, Pattern>(tokenType, pattern));
        }
    }
}
