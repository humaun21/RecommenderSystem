package com.prediction.RecommenderApp;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class ItemSimilarityRecommender {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DataModel dm = new FileDataModel(new File("data/movieRatings.csv"));
		TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);
		
		int x=1;
		for(LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {
			long itemId = items.nextLong();
			List<RecommendedItem>recommendations = recommender.mostSimilarItems(itemId, 5);
			
			for(RecommendedItem recommendation : recommendations) {
				System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
			}
			x++;
			if(x>10) System.exit(1);
		}
	}

}
