import controller.MethodsController;
import core.generator.AutoDocGenerator;
import core.score.TFIDF;
import core.score.Ranked;
import model.MethodModel;
import model.ProgramModel;

import java.io.IOException;
import java.util.Map;

import static javafx.scene.input.KeyCode.K;
import static javafx.scene.input.KeyCode.V;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        String path = "src/main/resources/test.java";
        try {
            MethodsController methodsController = new MethodsController(path, "AvgFactor");
            String[] docs = new String[ProgramModel.getInstance().getMethodModel().size()];
            for (int i = 0; i < ProgramModel.getInstance().getMethodModel().size(); i++) {
                MethodModel m = ProgramModel.getInstance().getMethodModel().get(i);
                docs[i] = m.getMethodBody();
            }
            TFIDF tf_idf = new TFIDF(docs);
            Ranked ranked = new Ranked(ProgramModel.getInstance().getMethodModel().size());
            for (int i = 0; i < ProgramModel.getInstance().getMethodModel().size(); i++) {
                MethodModel m = ProgramModel.getInstance().getMethodModel().get(i);
                ranked.createMap(i);
                System.out.println("-------------------------------");
                System.out.println("Method: " + m.getMethodName());
                System.out.println();
                for (String s : m.getDictionary().keySet()) {
//                    System.out.printf("%s:%-24s\t", m.getTypeWord().get(s), s);
//                    System.out.printf("%-12f\n", tf_idf.getTF_IDFMatrix()[i][tf_idf.getWords().get(s)]);
                    ranked.setTopWord(i, s, tf_idf.getTF_IDFMatrix()[i][tf_idf.getWords().get(s)]);
                }
                // Generate Template Document
                AutoDocGenerator autoDocGenerator = new AutoDocGenerator();
                boolean[] isValid = new boolean[12];
                ranked.getTopWord(i).limit(10).forEach((v) -> {
//                    System.out.println(m.getTypeWord().get(v.getKey()) + " " + v);
                    switch (m.getTypeWord().get(v.getKey())) {
                        case "MethodName":
                            if (!isValid[0])
                                autoDocGenerator.autoDocumentMethodName(v.getKey());
                            isValid[0] = true;
                            break;
                        case "LocalVariable":
                            if (!isValid[1])
                                autoDocGenerator.autoDocumentLocalVriable(v.getKey());
                            isValid[1] = true;
                            break;
                        case "RetrunValue":
                            if (!isValid[2])
                                autoDocGenerator.autoDocumentReturnValue(m.getExpReturnValue(), v.getKey());
                            isValid[2] = true;
                            break;
                        case "Parameters":
                            if (!isValid[3])
                                autoDocGenerator.autoDocumentParameters(m.getExpParameters());
                            isValid[3] = true;
                            break;
                        case "LoopsFor":
                            if (!isValid[4])
                                autoDocGenerator.autoDocumentLoopsFor(m.getExpLoopFor(), v.getKey());
                            isValid[4] = true;
                            break;
                        case "LoopsWhile":
                            if (!isValid[5])
                                autoDocGenerator.autoDocumentLoopsWhile(m.getExpLoopWhile(), v.getKey());
                            isValid[5] = true;
                            break;
                        case "LoopsDo":
                            if (!isValid[6])
                                autoDocGenerator.autoDocumentLoopsDo(m.getExpLoopDo(), v.getKey());
                            isValid[6] = true;
                            break;
                        case "Assignment":
                            if (!isValid[7])
                                autoDocGenerator.autoDocumentAssignment(m.getExpAssign(), v.getKey());
                            isValid[7] = true;
                            break;
                        case "MethodInvocation":
                            if (!isValid[8])
                                autoDocGenerator.autoDocumentMethodInvocation(m.getExpMethodInvocation(), v.getKey());
                            isValid[8] = true;
                            break;
                        case "IF":
                            if (!isValid[9])
                                autoDocGenerator.autoDocumentIfCondition(m.getExpIfConditions(), v.getKey());
                            isValid[9] = true;
                            break;
                        case "SWITCH":
                            if (!isValid[10])
                                autoDocGenerator.autoDocumentSwitch(m.getExpSwith(), v.getKey());
                            isValid[10] = true;
                            break;
                        case "ErrorHandler":
                            if (!isValid[11])
                                autoDocGenerator.autoDocumentCatch(m.getExpCatch(), v.getKey());
                            isValid[11] = true;
                            break;
                    }
                });
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

