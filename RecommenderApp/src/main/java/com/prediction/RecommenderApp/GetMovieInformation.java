package com.prediction.RecommenderApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetMovieInformation {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBConnect db=new DBConnect("root", "");
		//Check out whether there are users with fewer than 10 ratings, who might need to be filtered out to reduce noise
		int check=0;
		try {
			db.connect();
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/recommender_app", "root", "");
			//Statement stm = conn.createStatement();
			String chekRating = "select user_id,movie_id,rating, count(movie_id)AS cnt FROM movie_ratings GROUP BY user_id HAVING cnt > '10'";
			ResultSet rs = db.getStatement().executeQuery(chekRating);
			System.out.println(rs);
			while (rs.next()) {
				check=1;
			}
			db.closeConnection();
			System.out.println("check:"+check);
		} catch (Exception e) {
		}
		//If we have users more than 10 rating then we calculate number of rating and average rating
		if(check==1)
		{
		try {
			db.connect();
			int count=0;
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/recommender_app", "root", "");
			//Statement stm = conn.createStatement();
			String query = "SELECT movie_id, COUNT(rating) AS num_rating, AVG(rating) AS avg_rating FROM movie_ratings GROUP BY movie_id";
			ResultSet rs = db.getStatement().executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString("movie_id") + "::" + rs.getString("num_rating") + "::"
						+ rs.getString("avg_rating"));
				count++;
			}
			System.out.println("count"+count);
		} catch (Exception e) {
		}
	}
	}
}
