/*package org.gradle;

import java.io.*;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.prediction.*;

public class main {
  private static String modelClassName = "deep_model";

  public static void main(String[] args) throws Exception {
    hex.genmodel.GenModel rawModel;
    rawModel = (hex.genmodel.GenModel) Class.forName(modelClassName).newInstance();
    EasyPredictModelWrapper model = new EasyPredictModelWrapper(rawModel);
    //
    // By default, unknown categorical levels throw PredictUnknownCategoricalLevelException.
    // Optionally configure the wrapper to treat unknown categorical levels as N/A instead
    // and strings that cannot be converted to numbers also to N/As:
    //
    //     EasyPredictModelWrapper model = new EasyPredictModelWrapper(
    //         new EasyPredictModelWrapper.Config()
    //             .setModel(rawModel)
    //             .setConvertUnknownCategoricalLevelsToNa(true)
    //             .setConvertInvalidNumbersToNa(true)
    //     );

    RowData row = new RowData();
    
    
    row.put("0", "0");
     row.put("", "tcp");
     row.put("", "private");
     row.put("", "REJ");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0"); 
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "229");
     row.put("", "10");
     row.put("", "0");
     row.put("", "0");
     row.put("", "1");
     row.put("", "1");
     row.put("", "0.04");
     row.put("", "0.06");
     row.put("", "0");
     row.put("", "255");
     row.put("", "10");
     row.put("", "0.04"); 
     row.put("", "0.06");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "0");
     row.put("", "1");
     row.put("", "1");
     row.put("", "neptune");
     row.put("", "21");
    
     
     
     
     
     
    

    BinomialModelPrediction p = model.predictBinomial(row);
    System.out.println("Label (aka prediction) is flight departure delayed: " + p.label);
    System.out.print("Class probabilities: ");
    for (int i = 0; i < p.classProbabilities.length; i++) {
      if (i > 0) {
        System.out.print(",");
      }
      System.out.print(p.classProbabilities[i]);
    }
    System.out.println("");
  }
}*/