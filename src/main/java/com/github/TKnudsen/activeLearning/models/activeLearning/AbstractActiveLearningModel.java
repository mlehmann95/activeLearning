package com.github.TKnudsen.activeLearning.models.activeLearning;

import com.github.TKnudsen.ComplexDataObject.data.entry.EntryWithComparableKey;
import com.github.TKnudsen.ComplexDataObject.data.features.numericalData.NumericalFeatureVector;
import com.github.TKnudsen.ComplexDataObject.data.ranking.Ranking;
import com.github.TKnudsen.activeLearning.models.learning.ILearningModel;
import com.github.TKnudsen.activeLearning.models.learning.classification.IClassifier;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractActiveLearningModel implements IActiveLearningModelClassification<Double, NumericalFeatureVector> {

    protected List<NumericalFeatureVector> trainingFeatureVectors;
    protected List<NumericalFeatureVector> learningCandidateFeatureVectors;
    protected Ranking<EntryWithComparableKey<Double, NumericalFeatureVector>> ranking;
    protected Double remainingUncertainty;
    protected IClassifier<Double, NumericalFeatureVector> learningModel;
    public AbstractActiveLearningModel(IClassifier<Double, NumericalFeatureVector> learningModel) {
        this.learningModel = learningModel;
    }

    public Ranking<EntryWithComparableKey<Double, NumericalFeatureVector>> getRanking() {
        return ranking;
    }

    @Override
    public void setTrainingData(List<NumericalFeatureVector> featureVectors) {
        this.trainingFeatureVectors = featureVectors;
    }

    @Override
    public void setLearningCandidates(List<NumericalFeatureVector> featureVectors) {
        this.learningCandidateFeatureVectors = featureVectors;

        ranking = null;
    }

    @Override
    public List<NumericalFeatureVector> suggestCandidates(int count) {

        if (ranking == null)
            calculateRanking(count);

        List<NumericalFeatureVector> fvs = new ArrayList<>();
        for (int i = 0; i < ranking.size(); i++)
            fvs.add(i, ranking.get(i).getValue());

        return fvs;
    }

    protected abstract void calculateRanking(int count);

    @Override
    public double getRemainingUncertainty() {
        return remainingUncertainty;
    }

    @Override
    public ILearningModel<Double, NumericalFeatureVector, String> getLearningModel() {
        return learningModel;
    }
}
