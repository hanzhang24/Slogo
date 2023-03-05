package slogo.Parser;

import javafx.util.Pair;

import java.util.*;
import java.util.regex.Pattern;

public abstract class TypeChecker {

    private static boolean initialized = false;
    private static String syntaxPath = "Parser.TokenPatterns";
    private static List<Pair<TokenType, Pattern>> patterns;

    public static TokenType getType(String token) {
        if (!TypeChecker.initialized) {
            initPatternMap();
            TypeChecker.initialized = true;
        }

        for (Pair<TokenType, Pattern> p: TypeChecker.patterns) {
            Pattern pattern = p.getValue();
            TokenType tokenType = p.getKey();
            if (pattern.matcher(token).matches())
                return tokenType;
        }
        return TokenType.POSSIBLY_COMMAND;
    }
    private static void initPatternMap() {
        try{
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
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
