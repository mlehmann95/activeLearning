package com.github.TKnudsen.activeLearning.models.evaluation.classification;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.activeLearning.models.learning.ILearningModel;

public class ClassificationAccuracy implements INumericalFeaturesVectorModelEvaluation<String> {

	@Override
	public double getQuality(ILearningModel<Double, NumericalFeatureVector, String> model, List<NumericalFeatureVector> testData, String targetVariable) {
		double count = 0;
		double correct = 0;

		List<String> test = model.test(testData);
		if (test == null || test.size() == 0)
			return 0.0;

		for (int i = 0; i < testData.size(); i++) {
			if (testData.get(i) != null && testData.get(i).get(targetVariable) != null) {
				String label = testData.get(i).get(targetVariable).toString();
				if (label.equals(test.get(i)))
					correct++;
				count++;
			}
		}

		return correct / count;
	}
}
