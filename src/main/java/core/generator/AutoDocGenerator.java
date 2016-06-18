package core.generator;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.*;
import core.scanner.LexicalAnalyzer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Auto doc generator.
 */
public class AutoDocGenerator {
    /**
     * Auto document method name.
     *
     * @param methodName the method name
     */
    public void autoDocumentMethodName(String methodName) {
        System.out.println("This Method " + splitCamelCase(methodName));
    }

    /**
     * Auto document local vriable.
     *
     * @param localVariable the local variable
     */
    public void autoDocumentLocalVriable(String localVariable) {
        System.out.println(localVariable + " is a local variable of this method which acts in the main process of the method");
    }

    /**
     * Auto document return value.
     *
     * @param returnValue the return value
     */
    public void autoDocumentReturnValue(String returnValue) {
        System.out.println("This method finally returns " + returnValue);
    }

    /**
     * Auto document parameters.
     *
     * @param parameters the parameters
     */
    public void autoDocumentParameters(List<Parameter> parameters) {
        System.out.print("This method gets ");
        for (Parameter p : parameters) {
            System.out.print(p.getType() + " " + p.getId().getName() + ", ");
        }
        System.out.println("as input");
    }

    public void autoDocumentLoopsFor(ArrayList<ForStmt> loops, String loop) {
        System.out.print("some main actions of the method is done ");
        for (ForStmt l : loops) {
            if (l.getInit().toString().contains(loop)) {
                System.out.println("for " + l.getCompare().toString().split("<")[1] + " iteration");
            }
        }
    }

    public void autoDocumentLoopsWhile(ArrayList<WhileStmt> loops, String loop) {
        System.out.print("some main actions of the method is done ");
        for (WhileStmt l : loops) {
            if (l.getCondition().toString().contains(loop)) {
                System.out.println("while " + l.getCondition().toString());
            }
        }
    }

    public void autoDocumentLoopsDo(ArrayList<DoStmt> loops, String loop) {
        System.out.print("some main actions of the method is done ");
        for (DoStmt l : loops) {
            if (l.getCondition().toString().contains(loop)) {
                System.out.println("while " + l.getCondition().toString());
            }
        }
    }

    public void autoDocumentAssignment(ArrayList<AssignExpr> assignExprs, String assign) {
        for (AssignExpr s : assignExprs) {
            if (s.getValue().toString().contains(assign)) {
                System.out.println(" Assign equation " + s.getValue() + " to " + s.getTarget());
            }
        }
    }

    public void autoDocumentMethodInvocation(ArrayList<MethodCallExpr> methodCallExprs, String methodInvocation) {
        methodCallExprs.stream().filter(m -> methodInvocation.equals(m.getName())).forEach(m -> System.out.println("This method call method " + m));
    }

    public void autoDocumentIfCondition(ArrayList<IfStmt> ifConditions, String ifCondition) {
        for (IfStmt i : ifConditions) {
            if (i.getCondition().toString().contains(ifCondition)) {
                System.out.println("decides based on " + i.getCondition() + " if it is true then " + i.getThenStmt());
                if (i.getElseStmt() != null) {
                    System.out.println("otherwise " + i.getElseStmt());
                }
            }
        }
    }

    public void autoDocumentSwitch(ArrayList<SwitchStmt> expSwitch, String switchCase) {
        for (SwitchStmt s : expSwitch) {
            if (s.getSelector().toString().contains(switchCase)) {
                System.out.println("Decides different actions based on the " + s.getSelector());
            }
        }
    }

    public void autoDocumentCatch(ArrayList<CatchClause> catchClauses, String catchClause) {
        for (CatchClause c : catchClauses) {
            if (c.getExcept().toString().contains(catchClause) || c.getCatchBlock().toString().contains(catchClause)) {
                System.out.println("This method handles error by using exception mechanisms");
            }
        }
    }

    /**
     * Split camel case string.
     *
     * @param s the s
     * @return the string
     */
    public String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
