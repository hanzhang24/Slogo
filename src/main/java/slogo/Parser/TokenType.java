package slogo.Parser;
import slogo.Node.Node;
public enum TokenType {
    CONSTANT {
        Node parse(Parser parser) throws Exception {
            return parser.parseConstant();
        }
    }, VARIABLE {
        Node parse(Parser parser) throws Exception {
            return parser.parseVariable();
        }
    }, GROUP_START {
        Node parse(Parser parser) throws Exception {
            return parser.parseGroup();
        }
    }, GROUP_END {
        Node parse(Parser parser) throws Exception {
            throw new RuntimeException("Invalid token ]");
        }
    }, POSSIBLY_COMMAND {
        Node parse(Parser parser) throws Exception {
            return parser.parseCommand();
        }
    }, REPEATER_GROUP_BEGIN {
        Node parse(Parser parser) throws Exception {
            return parser.parseRepeatedCommand();
        }
    }, REPEATER_GROUP_END {
        Node parse(Parser parser) throws Exception {
            throw new RuntimeException("Invalid token )");
        }
    };
    abstract Node parse(Parser parser) throws Exception;
}