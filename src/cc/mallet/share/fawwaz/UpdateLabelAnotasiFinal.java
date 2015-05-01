package cc.mallet.share.fawwaz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateLabelAnotasiFinal {
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedstatement = null;
	private ResultSet resultset = null;
	
	public enum jenis{
		tes,satu,dua
	}
	
	public void startConnection(){
		this.startConnection("jdbc:mysql://localhost/mytomcatapp","root","");
	}
	
	public void startConnection(String URL,String DB_USERNAME, String DB_PASSWORD){
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
	
	public void UpdateLabelAnotasiTweetFinal(String label,Integer sequence_num){
		System.out.println("label : "+label+" Sequence :"+sequence_num);
		System.out.println(connection == null ? "null":"not null");
		try{
			preparedstatement = connection.prepareStatement("update `anotasi_tweet_final` set `label2`=? where `sequence_num`=?");
			preparedstatement.setString(1, label);
			preparedstatement.setInt(2, sequence_num);
			System.out.println("Execute update is disabled for safety");
			//preparedstatement.executeUpdate();
			
			System.out.println("[SUCCES] update "+sequence_num+" with label "+label);
			
			System.out.println("[INFO] Successful inserted");
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
