package com.prediction.RecommenderApp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.common.RandomUtils;

public class EvaluateItemBasedRecommender {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        RandomUtils.useTestSeed(); // to randomize the evaluation result               

		DataModel model = new FileDataModel(new File("data/test.csv"));
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {
				// ItemSimilarity similarity = new
				// EuclideanDistanceSimilarity(model);
				ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);

				// Optimizer optimizer = new NonNegativeQuadraticOptimizer();
				return new GenericItemBasedRecommender(model, similarity);
			}
		};
		
		// Recommend certain number of items for a particular user
        // Here, recommending 5 items to user_id = 9
        Recommender recommender = recommenderBuilder.buildRecommender(model);
        List<RecommendedItem> recomendations = recommender.recommend(9, 7); // recommend (user_id, number_of_items_to_recommend)
        for (RecommendedItem recommendedItem : recomendations) {
            System.out.println(recommendedItem);    
        }

		RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
		double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
		System.out.println("\nRMSE: " + score);

		RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
		IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, 4, 0.7);

		// evaluate
		// precision
		// recall
		// at
		// 10
		System.out.println("\nPrecision: " + stats.getPrecision());
		System.out.println("Recall: " + stats.getRecall());
		System.out.println("F1 Score: " + stats.getF1Measure());

	}

}
