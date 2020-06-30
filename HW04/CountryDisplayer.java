import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

public class CountryDisplayer{


public static void main(String[] args){
ArrayList<Country> countries = new ArrayList<Country>();
String filePath = args[0];
                            // System.out.println(filePath);
String indicator = args[1];
                            // System.out.println(indicator);
String sort = args[2];
                            // System.out.println(sort);
if ( 4 == args.length) {
    String indicator2 = args[3];
}

File file = new File(filePath);
        try{
            Scanner scan = new Scanner(file);
            while (scan.hasNext()){
                String line = scan.nextLine();
                ArrayList<String> c = new ArrayList<String>(); // This is an Arraylist defining one specific country, in the lines of code directly underneath this we split the string of numbers in each line of CountryDataset.csv by the commas inside and add each component as a seperate part of a list

                for (String i: line.split(",")){ // adding to country for each Substring created by splitting by a comma until there are no more commas to split.
                    c.add(i);

                }
                Country country = new Country(c);
                countries.add(country); // once we create this ArrayList for each specific country, we add it to the original ArrayList, and continue the looping

                //System.out.println(country); // to check the value for country to make sure everything works -- we also wanted to make sure it printed the final country, because if there wasn't an extra indentation at the end of CountryDataset.csv we would be stopping the while loop before zimbabwe was added. - commented out

                }
           // System.out.println(countries); // testing to see if countries is actually saving the things we add to it - it is

        }catch(FileNotFoundException e){
            System.out.println("The file does not exist in this directory");

        }
                            // System.out.println("We got here ****** ");
if (sort.equals("greatestToLeast")){ // theres a bunch of commented out printing, from when we used == to compare strings instead of .equals()... oops
                            // System.out.println("greatestToLeast");
    switch (indicator){
        case "Name":
            break;
        case "CO2Emissions":
            Boolean swap = true;
            while (swap == true){
                swap = false;
                for(int i = 0; i < countries.size() - 1;i ++){
                    if (String.valueOf(countries.get(i).getCO2()).equals("NaN")){
                        countries.add(countries.get(i));
                        countries.remove(i);
                        swap = true;
                    }
                    else if (countries.get(i).getCO2() < countries.get(i + 1).getCO2()) {
                       Collections.swap(countries,i,i + 1);
                       //System.out.println("Country Swapped!");
                       swap = true;
                    }
                }
            }
            break;
        case "TotalGreenhouseGasEmissions":
            break;
        case "AccessToElectricity":
            break;
        case "RenewableEnergy":
            break;
        case "ProtectedAreas":
            break;
        case "PopulationGrowth":
            break;
        case "PopulationTotal":
            break;
        case "UrbanPopulationGrowth":
            break;


    }
} else if(sort == "leastToGreatest"){
    System.out.println("LeastToGreatest");
       switch (indicator){
        case "Name":
            break;
        case "CO2Emissions":
            Boolean swap = true;
            while (swap = true){
                swap = false;
                for(int i = 0; i < countries.size();i ++){
                    if (countries.get(1).getCO2() > countries.get(2).getCO2()) {
                       Collections.swap(countries,1,2);
                        swap = true;
                    }


                }
            }
            break;
        case "TotalGreenhouseGasEmissions":
            break;
        case "AccessToElectricity":
            break;
        case "RenewableEnergy":
            break;
        case "ProtectedAreas":
            break;
        case "PopulationGrowth":
            break;
        case "PopulationTotal":
            break;
        case "UrbanPopulationGrowth":
            break;

    }

}

for ( Country c: countries){
    System.out.println(c.getCountry());
}
    /*

    Country Name,CO2 emissions (metric tons per capita),Total greenhouse gas emissions (kt of CO2 equivalent),Access to electricity (% of population),Renewable energy consumption (% of total final energy consumption),Terrestrial protected areas (% of total land area),Population growth (annual %),Population (total),Urban population growth (annual %)

    */


}
}
