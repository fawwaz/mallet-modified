
package cc.mallet.fst;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class MyDB {
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedstatement = null;
	private ResultSet resultset = null;
	
	
	/*
	 * ITERASI SELURUH DB
	 * */
	
	public void updateGuessedToken(String predicted){
		
		/*Twokenize tokenizer = new Twokenize();
		
		try{
			preparedstatement = connection.prepareStatement("SELECT twitter_tweet_id,tweet from filtered_tweet where label = 1 LIMIT 180");
			resultset = preparedstatement.executeQuery();
			Integer a = 0;
			while(resultset.next()){
				String tweet 			= resultset.getString("tweet").toLowerCase();
				Long twitter_tweet_id 	= resultset.getLong("twitter_tweet_id"); 
				System.out.println("Tweet :"+tweet);
				List<String> tokenized = tokenizer.tokenizeRawTweetText(tweet);
				for(String token : tokenized){
					InsertTokenToAnotasiTweet(token, twitter_tweet_id);
				}
			}
			System.out.println("[INFO] Successful inserted");
		}catch(SQLException e){
			e.printStackTrace();
		}*/
	}

	
	public void startConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		String DB_USERNAME	 	= "root";
		String DB_PASSWORD		= "";
		String DB_URL 			= "mysql://localhost/";
		String DB_NAME			= "mytomcatapp";
		String URL 				= "jdbc:"+DB_URL+DB_NAME;

		System.out.println("[INFO] Getting environment variables");
		System.out.println("DB_USERNAME \t: "+ DB_USERNAME);
		System.out.println("DB_PASSWORD \t: "+ DB_PASSWORD);
		System.out.println("DB_URL	\t: "+ DB_URL);
		System.out.println("DB_NAME \t: "+ DB_NAME);
		System.out.println("URL \t\t:"+URL);

		try{
			connection 	= (Connection) DriverManager.getConnection(URL,DB_USERNAME,DB_PASSWORD);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void CloseConnection(){
		try{
			if(resultset!=null){
				resultset.close();
			}
			if(statement!=null){
				statement.close();
			}
			if(connection!=null){
				connection.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
