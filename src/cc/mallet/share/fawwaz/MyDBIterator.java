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
	public Integer lastid;
	ArrayList<String> sesuai_format;
	String currentTweetGroup;
	Integer currentStart,currentEnd,nextStart;
	
	
	public MyDBIterator(){
		// default constructor
		this("root","","jdbc:mysql://localhost/mytomcatapp");
	}
	
	public MyDBIterator(String username, String password,String url) {
		startConnection(url, username, password);
		anotated_list = new ArrayList<>();
		sesuai_format = new ArrayList<>();
		getData();
		CloseConnection();
	
		
		System.out.println("Anotasi silahkan dilanjutkan dengan ukuran anotated_list"+anotated_list.size());
		lastid=0;
		nextStart = 1;
		//setNextTweetGroup();
		//printSeusaiFormat();
		//System.exit(2);
	}
	
	private void setNextTweetGroup(){
		StringBuffer sb = new StringBuffer();
		anotasi_tweet_final line,nextline;
		Integer i = lastid;
		if(i==anotated_list.size()){
			return;
		}
		currentStart = nextStart;
		System.out.println("I :" + i);
		while(true){
			//System.out.println("iterasi : "+i);
			line		= anotated_list.get(i);
			if(i.equals(anotated_list.size()-1)){
				lastid = i+1;
				currentEnd = anotated_list.size();
				sb.append(line.token + " "+ line.label+"\n");
				currentTweetGroup = sb.toString();
				System.out.println("keluar dari loop karena sudah sampai akhir database dengan lastid :"+lastid);
				return;
			}
			nextline	= anotated_list.get(i+1);
			
//			System.out.println("LINE IS "+line.token);
//			System.out.println("NEXTLINE IS"+nextline.token);
//			System.out.println("LASTID : " + lastid);
//			System.out.println("I :"+i);
//			System.out.println("=====");
			if(!line.twitter_tweet_id.equals(nextline.twitter_tweet_id)){
				currentEnd = i+1;
				lastid = i+1;
				nextStart = lastid+1;
				sb.append(line.token + " "+ line.label);
				sb.append("\n");
				//System.out.println("~~ LASTId :" + lastid);
				currentTweetGroup = sb.toString();
				return;
			}else{
				sb.append(line.token + " "+ line.label);
				sb.append("\n");
			}
			i++;
		}
		
	}
	
	@Override
	public boolean hasNext() { 
		return lastid<=(anotated_list.size()-1);
	}

	@Override
	public Instance next() {
		assert(this.hasNext());
		//System.out.println("lastid : "+lastid);
		setNextTweetGroup();
		//System.out.println("Current tweet group is : \n"+currentTweetGroup);
		String name = String.valueOf(currentStart);//+"-"+currentEnd; 
		Instance carrier = new Instance(currentTweetGroup, null, name, "defaultsource");
		//System.exit(2);
		
		return carrier;
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
			preparedstatement = connection.prepareStatement("SELECT * from anotasi_tweet_final limit 100");
			//preparedstatement = connection.prepareStatement("SELECT * from anotasi_tweet");
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
			System.out.println("Token : "+ a.token+" \n\tTwitter_tweet_id : "+a.twitter_tweet_id+ " \n\tLabel : "+ a.label);
		}
	}
	private void printSeusaiFormat(){
		for (int i = 0; i < sesuai_format.size(); i++) {
			System.out.println("Sequence ke -" + i);
			System.out.println(sesuai_format.get(i));
			System.out.println("===###===###===###===");
		}
	}
}
