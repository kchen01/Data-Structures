import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/*
This class defines a Country and its instance variables and methods. 
*/
public class Country{
    private String name;
    private double co2Emissions;
    private double greenEmissions;
    private double electricity;
    private double renewableEnergy;
    private double percentProtected;
    private double popGrowth;
    private double totalPop;
    private double urbPopGrowth;
    
    public Country(ArrayList<String> country){ // each country - originally called as a list, gets assigned variables based on the index of the list, making it easy to return specific values by name (using getValue).
        name = country.get(0);
        co2Emissions = Double.valueOf(country.get(1));
        greenEmissions = Double.valueOf(country.get(2));
        electricity = Double.valueOf(country.get(3));
        renewableEnergy = Double.valueOf(country.get(4));
        percentProtected = Double.valueOf(country.get(5));
        popGrowth = Double.valueOf(country.get(6));
        totalPop = Double.valueOf(country.get(7));
        urbPopGrowth = Double.valueOf(country.get(8));
    }
    /* 
    *These following methods are getter methods for each country to return a specific value. 
    *All of the variable assignment and changing was done on initialization of the country, and all that needs to be done by the Countries post initialization is returning specific values.
    */
    public String getName(){
        return name;
    }
    public double getValue(String indicator){
        switch (indicator){
            case "CO2Emissions":
                return co2Emissions;
                
            case "TotalGreenhouseGasEmissions":
                return greenEmissions;
                
            case "AccessToElectricity":
                return electricity;
                
            case "RenewableEnergy":
                return renewableEnergy;
                
            case "ProtectedAreas":
                return percentProtected;
                
            case "PopulationGrowth":
                return popGrowth;
                
            case "PopulationTotal":
                return totalPop;
                
            case "UrbanPopulationGrowth":
                return urbPopGrowth;    
        }
        return 0;
    }
    /*
    Converts the Country object's information into a string that is printed on the command line. Called in printCountries().
    */
    public String getCountry(){
        String countryString = (name + "," + co2Emissions + "," + greenEmissions + "," + electricity + "," + renewableEnergy + "," + percentProtected + "," + popGrowth + "," + totalPop + "," + urbPopGrowth);
        
        return countryString;    
    }

}