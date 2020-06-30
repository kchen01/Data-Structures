import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
    
    public Country(ArrayList<String> country){ // each country - originally called as a list, gets assigned variables based on the index of the list, making it easy to return specific values by name (using get_____)
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
    
    public String getName(){
        return name;
    }
    public double getCO2(){
        return co2Emissions;
    }
    public double getGreen(){
        return greenEmissions;
    }
    public double getElectricity(){
        return electricity;
    }
    public double getRenewable(){
        return renewableEnergy;
    }
    public double getPercentProtected(){
        return percentProtected;
    }
    public double getPopGrowth(){
        return popGrowth;
    }
    public double getTotalPop(){
        return totalPop;
    }
    public double getUrbPopGrowth(){
        return urbPopGrowth;
    }
    
    public String getCountry(){
        String countryString = (name + "," + co2Emissions + "," + greenEmissions + "," + electricity + "," + renewableEnergy + "," + percentProtected + "," + popGrowth + "," + totalPop + "," + urbPopGrowth);
        
        return countryString;    
    }


}