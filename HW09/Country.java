/**
 * Country.java
 * This class store each country as an object with its values,
 * this comes with several methods to get and change the values.
 */

public class Country {
    private String name; //name of the country
    private double co2; //CO2Emissions
    private double gas; //TotalGreenhouseGasEmissions
    private double elec; //AccessToElectricity
    private double ener; //RenewableEnergy
    private double area; //ProtectedAreas
    private double popG; //PopulationGrowth
    private double popT; //PopulationTotal
    private double urb; //UrbanPopulationGrowth

    /**
     * This is the constructor to create a country object with its values
     * @param name        name of the country
     * @param co2         CO2Emissions
     * @param gas         TotalGreenhouseGasEmissions
     * @param elec        AccessToElectricity
     * @param ener        RenewableEnergy
     * @param area        ProtectedAreas
     * @param popG        PopulationGrowth
     * @param popT        PopulationTotal
     * @param urb         UrbanPopulationGrowth
     */
    public Country (String name, double co2, double gas, double elec, double ener, double area, double popG, double popT, double urb) {
        this.name = name;
        this.co2 = co2;
        this.gas = gas;
        this.elec = elec;
        this.ener = ener;
        this.area = area;
        this.popG = popG;
        this.popT = popT;
        this.urb = urb;
    }


    public Country(String[] countryData){
      this.name = countryData[0];
      this.co2 = Double.valueOf(countryData[1]);
      this.gas = Double.valueOf(countryData[2]);
      this.elec = Double.valueOf(countryData[3]);
      this.ener = Double.valueOf(countryData[4]);
      this.area = Double.valueOf(countryData[5]);
      this.popG = Double.valueOf(countryData[6]);
      this.popT = Double.valueOf(countryData[7]);
      this.urb = Double.valueOf(countryData[8]);
    }


    public boolean equals(Country otherCountry){
      return false;
    }


    /**
	 * This method get the name
	 * @return the name of the country
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method gets the value of the indicator
     * @param indicator   the indicator's name
     * @return            the value of that indicator
     */
    public double getIndicator(String indicator){
        switch (indicator) {
        case "CO2Emissions":
            return this.co2;
        case "TotalGreenhouseGasEmissions":
            return this.gas;
        case "AccessToElectricity":
            return this.elec;
        case "RenewableEnergy":
            return this.ener;
        case "ProtectedAreas":
            return this.area;
        case "PopulationGrowth":
            return this.popG;
        case "PopulationTotal":
            return this.popT;
        //case "UrbanPopulationGrowth":
        default:
            return this.urb;
        }
    }

    /**
	 * This method changes the value of the indicator
     * @param indicator   the indicator being changed
	 * @param no          the new value
     */
    public void changeIndicator(String indicator, Double no){
        switch (indicator) {
        case "CO2Emissions":
            this.co2 = no;
            break;
        case "TotalGreenhouseGasEmissions":
            this.gas = no;
            break;
        case "AccessToElectricity":
            this.elec = no;
            break;
        case "RenewableEnergy":
            this.ener = no;
            break;
        case "ProtectedAreas":
            this.area = no;
            break;
        case "PopulationGrowth":
            this.popG = no;
            break;
        case "PopulationTotal":
            this.popT = no;
            break;
        case "UrbanPopulationGrowth":
            this.urb = no;
            break;
        default:
            System.out.println("please input the right indicator format");
            break;
        }
    }


}
