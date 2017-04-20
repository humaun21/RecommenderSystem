package com.prediction.RecommenderApp;

import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class FindMovieRank {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> movieId = new ArrayList<Integer>();
		List<Integer> rank = new ArrayList<Integer>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/recommender_app", "root", "");
			/*Statement stm = conn.createStatement();
			String query = "SELECT movie_id,number_of_rating,CASE WHEN @PreviousRecord = number_of_rating THEN @Rank WHEN @PreviousRecord := number_of_rating THEN @Rank := @Rank + 1 END AS ScoreRank FROM movie_rankings ORDER BY number_of_rating DESC";
			ResultSet rs = stm.executeQuery(query);
			int count = 0;*/
			PreparedStatement ps1 = conn.prepareStatement("SET @PreviousRecord = NULL");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("SET @Rank = 0");
			ps2.executeUpdate();
			Statement stm = conn.createStatement();
			String query = "SELECT movie_id,number_of_rating,CASE WHEN @PreviousRecord = number_of_rating THEN @Rank WHEN @PreviousRecord := number_of_rating THEN @Rank := @Rank + 1 END AS ScoreRank FROM movie_rankings ORDER BY movie_id DESC";
			ResultSet rs = stm.executeQuery(query);
			while (rs.next()) {
				movieId.add(rs.getInt("movie_id"));
				rank.add(rs.getInt("scoreRank"));
			}
			for (int i = 0; i < movieId.size(); i++) {
				System.out.println(movieId.get(i)+"-"+rank.get(i));
			}
			//To update the movie_ranks table
			//To update rant based on movie id
			/*stm.close();
			Statement upstm = conn.createStatement();
			for (int i = 0; i < movieId.size(); i++) {
				String updet = "UPDATE movie_rankings SET rank='" + rank.get(i) + "' WHERE movie_id='" + movieId.get(i)+ "'";
				int j = upstm.executeUpdate(updet);
			}
			upstm.close();*/

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
