package slogo.Parser;
import slogo.Node.Node;
public enum TokenType {
    CONSTANT {
        Node parse(Parser parser) {
            return parser.parseConstant();
        }
    }, VARIABLE {
        Node parse(Parser parser) {
            return parser.parseVariable();
        }
    }, GROUP_START {
        Node parse(Parser parser) {
            return parser.parseGroup();
        }
    }, GROUP_END {
        Node parse(Parser parser) {
            throw new RuntimeException("Invalid token");
        }
    }, POSSIBLY_COMMAND {
        Node parse(Parser parser) throws NoSuchMethodException {
            return parser.parseCommand();
        }
    };
    abstract Node parse(Parser parser) throws NoSuchMethodException;
}