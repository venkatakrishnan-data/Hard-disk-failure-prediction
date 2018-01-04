package org.gradle;

import java.io.*;
import java.util.Map;

import javax.servlet.http.*;
import javax.servlet.*;

import hex.genmodel.easy.prediction.BinomialModelPrediction;
import hex.genmodel.easy.prediction.RegressionModelPrediction;
import hex.genmodel.easy.*;

public class PredictServlet extends HttpServlet {
	// Set to true for demo mode (to print the predictions to stdout).
	// Set to false to get better throughput.
	static final boolean VERBOSE = true;

	static EasyPredictModelWrapper deepModel;

	static {

		rf_covType_v1 rawInterestRateModel = new rf_covType_v1();
		deepModel = new EasyPredictModelWrapper(rawInterestRateModel);

	}

	@SuppressWarnings("unchecked")
	private void fillRowDataFromHttpRequest(HttpServletRequest request, RowData row) {

		Map<String, String[]> parameterMap;
		parameterMap = request.getParameterMap();
		// System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ :"+parameterMap);
		if (VERBOSE)
			System.out.println();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			// System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ key :"+key);
			for (String value : values) {
				if (VERBOSE)
					System.out.println("Key " + key + " Value: " + value);
				if (value.length() > 0) {

					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					row.put(key, value);
				}

			}
		}
	}

	private BinomialModelPrediction predictBadLoan(RowData row) throws Exception {
		return deepModel.predictBinomial(row);
	}

	private String createJsonResponse(BinomialModelPrediction p, RegressionModelPrediction p2) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("  \"labelIndex\" : ").append(p.labelIndex).append(",\n");
		sb.append("  \"label\" : \"").append(p.label).append("\",\n");
		sb.append("  \"classProbabilities\" : ").append("[\n");
		for (int i = 0; i < p.classProbabilities.length; i++) {
			double d = p.classProbabilities[i];
			if (Double.isNaN(d)) {
				throw new RuntimeException("Probability is NaN");
			} else if (Double.isInfinite(d)) {
				throw new RuntimeException("Probability is infinite");
			}

			sb.append("    ").append(d);
			if (i != (p.classProbabilities.length - 1)) {
				sb.append(",");
			}
			sb.append("\n");
		}
		sb.append("  ],\n");
		sb.append("\n");
	    sb.append("  \"Category\" : ").append("  \"\" ").append("\n");
		sb.append("}\n");

		return sb.toString();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RowData row = new RowData();
		// fillRowDataFromHttpRequest(request, row);

		String myArray[] = ReadFile.value();

		row.put("0", myArray[1]);
		row.put("1", myArray[2]);
		row.put("2", myArray[3]);
		row.put("3", myArray[4]);
		row.put("4", myArray[5]);
		row.put("5", myArray[6]);
		row.put("6", myArray[7]);
		row.put("7", myArray[8]);
		row.put("8", myArray[9]);
		row.put("9", myArray[10]);
		row.put("10", myArray[11]);
		row.put("11", myArray[12]);

		
		try {

			System.out.println(" @@@@@@@@@@@@@  row: " + row);
			BinomialModelPrediction p = predictBadLoan(row);
			
			String arrayString = "";
			for(int i=1;i<myArray.length;i++) {
				arrayString+="-"+myArray[i];
			}
			p.label = p.label+"--"+arrayString;
			String s = createJsonResponse(p, null);
			response.getWriter().write(s);
			response.setStatus(HttpServletResponse.SC_OK);

			if (VERBOSE)
				System.out.println("prediction(Internal disk status)     : " + s);
			// if (VERBOSE)
			// System.out.println("prediction(Category Type): " + p2.value);
		} catch (Exception e) {
			// Prediction failed.
			System.out.println(e.getMessage());
			// response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, e.getMessage());
		}
	}
}
