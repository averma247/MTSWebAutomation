package jsons;

import java.util.Arrays;

public class TestJSONData {

	public String[]City;
	public int Variance;

	
	public TestJSONData(String []City,int Variance){
		this.City=City;
		this.Variance=Variance;
	}
	
	public String[] getlistofCities(){
		return City;
	}
	public int getVariance(){
		return Variance;
	}
	
	
	@Override
    public String toString() { 
        return String.format("City details: "+ Arrays.toString(City)+" Vairance: "+ Variance); 
    } 
	
	
}