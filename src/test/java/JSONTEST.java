import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import jsons.TestJSONData;

public class JSONTEST {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//jsonTestDataProvider();
		System.out.println("&#8451"+"C");

	}

	public static void jsonDataProvider() throws Exception{

		Gson gson = new Gson();

		Reader reader = Files.newBufferedReader(Paths.get("./TestData/CityData.json"));
		Map<?,?> map= gson.fromJson(reader, Map.class);

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}

		reader.close();
	}

	@DataProvider(name="citydata")
	public static Object[][] jsonTestDataProvider() throws IOException{
		Gson gson = new Gson();
		Reader reader = Files.newBufferedReader(Paths.get("./TestData/CityData.json"));
		TestJSONData data= gson.fromJson(reader, TestJSONData.class);
		
		Object[][] obj = new Object[1][1];
		obj[0][0]=data;
		reader.close();
		return obj;
		
		
		
		
	}
	
	
	public static TestJSONData jsonTestDataReader() throws IOException{
		Gson gson = new Gson();

		Reader reader = Files.newBufferedReader(Paths.get("./TestData/CityData.json"));
		TestJSONData data= gson.fromJson(reader, TestJSONData.class);

		System.out.println(data);
		reader.close();
		
		return data;		

	}
	
	
	
	public static void jsonTestDataProviderArray() throws IOException{
		Gson gson = new Gson();

		Reader reader = Files.newBufferedReader(Paths.get("./TestData/CityData.json"));
		List<TestJSONData> data=gson.fromJson(reader, new TypeToken<List<TestJSONData>>() {}.getType());

		data.forEach(System.out::println);
		
		reader.close();
		

	}
	
	
	@Test(dataProvider = "citydata")
    public void testMethod(TestJSONData data) {
        
		String[]Cities=data.getlistofCities();
		int varience= data.getVariance();
		for(String city: Cities){
			
			System.out.println("Cityname: "+ city);
		}
		System.out.println("Varience Value: "+ varience);
		
    }
	
	
	

}

