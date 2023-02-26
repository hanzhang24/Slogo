package Parser;

import java.util.List;

public class Tokenizer {
    int curIndex = 0;
    String[] tokens;
    public Tokenizer(String input) {
        try {
            String noComments = removeComments(input);
            tokens = noComments.split("\\s+");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setCurIndex(int value) {
        if (value < 0 || value >= tokens.length) {
            //TODO: parameterize this error message by language
            throw new IndexOutOfBoundsException("Cannot set index to " + value);
        }
        curIndex = value;
    }
    public int getCurIndex() {
        return curIndex;
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
