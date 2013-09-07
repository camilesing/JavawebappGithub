package nds.query;

import java.util.List;

public class QueryEngine {
	public static QueryEngine getInstance(){
		return new QueryEngine();
	}
	public List doQueryList(String paramString){
		return null;
	}
	public int executeUpdate(String sql){
		return 0;
	}
	public Object doQueryOne(String sql){
		return "1";
	}
}
