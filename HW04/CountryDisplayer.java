import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

public class CountryDisplayer{
ArrayList<Country> countries;

/* Creates a new CountryDisplayer with a blank arraylist of countries
*/
public CountryDisplayer(){
    
    countries = new ArrayList<Country>();
}
 
/* Fills the blank arraylist of countries created earlier with sets of data from a given file path (CountryDataset.csv in this case). Does this by scanning each line of file, splitting it up by commas, adding those split substrings into a blank arraylist<string> that then is used to make a new Country to add to the ArrayList<Country> */
public void loadCountries(String filePath){
     File file = new File(filePath);
        try{
            Scanner scan = new Scanner(file);
            while (scan.hasNext()){
                String line = scan.nextLine();
                ArrayList<String> c = new ArrayList<String>(); // This is an Arraylist defining one specific country, in the lines of code directly underneath this we split the string of numbers in each line of CountryDataset.csv by the commas inside and add each component as a seperate part of a list, then use that list to create one new object Country. We continue to do this over and over for each line in the dataset until there are no more lines.
                
                for (String i: line.split(",")){ // adding to country for each Substring created by splitting by a comma until there are no more commas to split.
                    c.add(i);
                }
                Country country = new Country(c);
                countries.add(country); // once we create this ArrayList for each specific country, we add it to the original ArrayList, and continue the looping
                }

            
        }catch(FileNotFoundException e){
            System.out.println("The file does not exist in this directory");
        }
}
    
/* This method is called with 2 parameters, an indicator and a sort direction(greatestToLeast or leastToGreatest), and then sorts the list of countries based on those parameters. we create a temporary list to take out all countries with value "NaN", and then add that temp list to the end after we are done sorting */    
public void sort(String indicator, String sort){
    ArrayList<Country> temp = new ArrayList<Country>();
 if(indicator.equals("Name")){ // this uses an if else statement in case someone wants to sort by name, otherwise it will sort by the indicator given. We use this because getName() returns a string while getValue returns a double, and must have two different ways of sorting because Strings and Doubles are not able to be used interchangeably without a lot of .valueOf() scattered throughout the code
             Boolean swap = true;
            while (swap == true){
                swap = false;
                for(int i = 0; i < countries.size() - 1;i ++){
                 if (countries.get(i).getName().compareTo(countries.get(i+1).getName()) > 0) {
                       Collections.swap(countries,i,i + 1);

                        swap = true;
                 }}}
 }else{
            Boolean swap = true;
             temp = new ArrayList<Country>();
            while (swap == true){
                swap = false; // this makes it so if no swap is done(neither of the if statements actually run) - the while loop will stop.
                
                for(int i = 0; i < countries.size() - 1;i ++){
                    if (String.valueOf(countries.get(i).getValue(indicator)).equals("NaN")){ // If the value is NaN, Add it to a temp list to be combined later and remove the index , setting swap= true 
                        temp.add(countries.get(i));
                        countries.remove(i);
                        
                        swap = true;
                    }else if (countries.get(i).getValue(indicator) < countries.get(i + 1).getValue(indicator)) { // used to compare
                       Collections.swap(countries,i,i + 1); 
                        swap = true; // sets swap to true after swapping
                    }}} 
   
    if(sort.equals("leastToGreatest")){
        Collections.reverse(countries); //Sorting is done the same way for both leastToGreatest and GreatestToLeast, just in opposite ways, meaning they are the exact opposite/reverse lists. This means we can use Collections.reverse to change between greatestToLeast and leastToGreatest. So we decided to sort by GreatestToLeast by default and then check to see if they actually wanted it sorted as leastToGreatest, and if they do, we reverse the list.
    }
    countries.addAll(temp); // adding NaN countries on the end after list has been completely sorted, meaning the NaN countries will always be the last countries in the list.
 }
}
    
/* This method prints out the list of countries after being sorted. This is called when three command line arguments are given.
*/
public void printCountries(String indicator, String sort){
    this.sort(indicator, sort);
    for ( Country c: countries){
    System.out.println(c.getCountry());
}
}
/*
This method creates a BarChart and adds values of the top 10 countries based on indicator 1 and also displays the values of the second indicator for those same 10 countries. 
*/
public void makeGraph(String indicator, String indicator2, String sort){
     BarChart bar = new BarChart("Top 10 " + indicator + " and their " + indicator2, "Countries", "Value");
        this.sort(indicator, sort);
        ArrayList<Country> countries = this.getCountries();
        for(int i = 0; i < 10; i ++){
                    bar.addValue(countries.get(i).getName(), countries.get(i).getValue(indicator), indicator);
                    bar.addValue(countries.get(i).getName(), countries.get(i).getValue(indicator2), indicator2);
            }
                bar.displayChart();
}
/*
This method returns the list of countries, allowing the list to be used outside of the class.
*/
public ArrayList<Country> getCountries(){
    return countries;
}
    
public static void main(String[] args){
    Boolean check = false;
    String filePath = args[0];             
    String indicator = args[1];              
    String sort = args[2];              
    String indicator2 = "";
    if ( 4 == args.length) { // sets a boolean check to true if there are 4 arguments in the main
    indicator2 = args[3];
    check = true;
    
    }
    CountryDisplayer countrydisplayer = new CountryDisplayer();
    countrydisplayer.loadCountries(filePath);
    if (check){
        countrydisplayer.makeGraph(indicator, indicator2, sort);
    }
    else{
        countrydisplayer.printCountries(indicator, sort);
}
}
}
    
