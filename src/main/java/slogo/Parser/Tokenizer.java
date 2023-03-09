package slogo.Parser;

import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    int curIndex = 0;
    String[] tokens;
    public Tokenizer(String input) {
        try {
            String noComments = removeComments(input);
            String processed = separateListDelimiters(noComments);
            tokens = Arrays.stream(processed.split("\\s+"))
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String separateListDelimiters(String input) {
        return input.replaceAll("[\\[\\]\\(\\)]", " $0 ");
    }

    public void toNextToken() {
        if (curIndex < tokens.length) {
            curIndex += 1;
        } else {
            // TODO: parameterize this error message by language
            throw new IndexOutOfBoundsException("Cannot get token beyond end of input");
        }
    }
    public String getCurToken() {
        if (curIndex >= tokens.length) {
            throw new IndexOutOfBoundsException("Cannot get token at/beyond end of input");
        }
        return tokens[curIndex];
    }
    public String peekNextToken() {
        if (curIndex < tokens.length - 1) {
            return tokens[curIndex + 1];
        } else {
            // TODO: parameterize this error message by language
            throw new IndexOutOfBoundsException("Cannot peek beyond end of file");
        }
    }
    public boolean isEndOfInput() {
        return curIndex >= tokens.length;
    }
    public boolean hasNext() {
        return curIndex >= 0 || curIndex < tokens.length - 1;
    }
    private String removeComments(String input) {
        String [] lineSplit = input.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lineSplit.length; i++){
            int commentIndex = lineSplit[i].indexOf("#");
            if (commentIndex == -1)
                sb.append(lineSplit[i]);
            else
                sb.append(lineSplit[i].substring(0, commentIndex));
            sb.append("\n");
        }
        return sb.toString();
    }
}
