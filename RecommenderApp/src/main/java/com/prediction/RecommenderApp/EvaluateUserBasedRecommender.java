package com.prediction.RecommenderApp;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class EvaluateUserBasedRecommender {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// to randomize the evaluation result
		RandomUtils.useTestSeed(); 
		DataModel model = new FileDataModel(new File("data/test.csv"));
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {				
				// SpearmanCorrelationSimilarity similarity = new SpearmanCorrelationSimilarity(model);
				UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
				// neighborhood size = 100
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, model);
				return new GenericUserBasedRecommender(model, neighborhood, similarity);
			}
		};

		// Recommend certain number of items for a particular user
		// Here, recommending 5 items to user_id = 9
		Recommender recommender = recommenderBuilder.buildRecommender(model);
		List<RecommendedItem> recomendations = recommender.recommend(9, 5);
		for (RecommendedItem recommendedItem : recomendations) {
			System.out.println(recommendedItem);
		}
		
		//trainingPercentage – percentage of each user’s preferences to use to produce recommendations;
		//the rest are compared to estimated preference values to evaluate Recommender performance
		//evaluationPercentage – percentage of users to use in evaluation
		RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
		double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
		System.out.println("\nRMSE: " + score);
		
		/*RStatistics evaluate(RecommenderBuilder recommenderBuilder,
		DataModelBuilder dataModelBuilder,
		DataModel dataModel,
		IDRescorer rescorer,
		int at,
		double relevanceThreshold,
		double evaluationPercentage)
		throws TasteException
		dataModel – dataset to test on
		rescorer – if any, to use when computing recommendations
		at – as in, “precision at 5”. The number of recommendations to consider when evaluating precision, etc.
		relevanceThreshold – items whose preference value is at least this value are considered “relevant” for the purposes of computations
		*/	
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
