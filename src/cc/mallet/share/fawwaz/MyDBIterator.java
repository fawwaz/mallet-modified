package cc.mallet.share.fawwaz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cc.mallet.types.Instance;

public class MyDBIterator implements Iterator<Instance>{
	
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedstatement = null;
	private ResultSet resultset = null;
	ArrayList<anotasi_tweet_final> anotated_list;
	
	public MyDBIterator(){
		// default constructor
		this("root","","jdbc:mysql://localhost/mytomcatapp");
	}
	
	public MyDBIterator(String username, String password,String url) {
		startConnection(url, username, password);
		anotated_list = new ArrayList<>();
		getData();
		CloseConnection();
		System.out.println("Anotasi silahkan dilanjutkan");
		printData();
		System.exit(2);
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Instance next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Private functions
	 * */
	
	private void startConnection(String URL,String DB_USERNAME, String DB_PASSWORD){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		System.out.println("[INFO] Getting environment variables");
		System.out.println("DB_USERNAME \t: "+ DB_USERNAME);
		System.out.println("DB_PASSWORD \t: "+ DB_PASSWORD);
		System.out.println("URL \t\t:"+URL);

		try{
			connection 	= (Connection) DriverManager.getConnection(URL,DB_USERNAME,DB_PASSWORD);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void getData(){
		try{
			preparedstatement = connection.prepareStatement("SELECT * from anotasi_tweet_final");
			resultset = preparedstatement.executeQuery();
			
			while(resultset.next()){
				anotasi_tweet_final anotate_data = new anotasi_tweet_final();
				anotate_data.sequence_num 		= resultset.getInt("sequence_num");
				anotate_data.twitter_tweet_id	= resultset.getString("twitter_tweet_id");
				anotate_data.token 				= resultset.getString("token");
				anotate_data.label				= resultset.getString("label");
				 
				anotated_list.add(anotate_data);
			}
			System.out.println("[INFO] Successful inserted");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void CloseConnection(){
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
	
	private void printData(){
		System.out.println("Data yang diload");
		for (int i = 0; i < anotated_list.size(); i++) {
			anotasi_tweet_final a = anotated_list.get(i);
			System.out.println("Token : "+ a.token+" \n\tTwitter_tweet_id"+a.twitter_tweet_id+ " \n\tLabel : "+ a.label);
		}
	}
}
