import controller.MethodsController;
import core.score.TFIDF;
import model.MethodModel;
import model.ProgramModel;
import model.factor.AvgFactor;
import model.factor.EYEC;

import java.io.IOException;

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
//            for (String s : tf_idf.getWordVector()) {
//                System.out.printf("%1s\t", s);
//            }
//            System.out.println();
//            for (double[] docV : tf_idf.getTF_IDFMatrix()) {
//                for (double v : docV) {
//                    System.out.printf("%4f\t", v);
//                }
//                System.out.println();
//            }
            for (int i = 0; i < ProgramModel.getInstance().getMethodModel().size(); i++) {
                MethodModel m = ProgramModel.getInstance().getMethodModel().get(i);

                System.out.println("-------------------------------");
                System.out.println("Method: " + m.getMethodName());
                System.out.println();
                for (String s : m.getDictionary().keySet()) {
                    System.out.printf("%-24s\t", s);
                    System.out.printf("%-12f\n", tf_idf.getTF_IDFMatrix()[i][tf_idf.getWords().get(s)]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

