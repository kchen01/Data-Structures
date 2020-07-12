import java.util.*;
import java.io.*;

public class CountryComparator implements Comparator<Country>{
    String indicator;
    boolean sort;

    public CountryComparator(String indicator, boolean sort){
        this.indicator = indicator;
        this.sort = sort;
    }

    public int compare(Country country1, Country country2){
        if (sort == false) {
            if (country1.getIndicator(indicator) < country2.getIndicator(indicator)) {
              return -1;
            }
            else if (country1.getIndicator(indicator) > country2.getIndicator(indicator)) {
              return 1;
            }
            else if (Double.isNaN(country1.getIndicator(indicator)) || Double.isNaN(country2.getIndicator(indicator))){
                if (Double.isNaN(country1.getIndicator(indicator)) && Double.isNaN(country2.getIndicator(indicator))){
                    return country1.getName().compareTo(country2.getName());
                }
                if (Double.isNaN(country1.getIndicator(indicator)) && !Double.isNaN(country2.getIndicator(indicator))){
                    return 1;
                }
                if (!Double.isNaN(country1.getIndicator(indicator)) && Double.isNaN(country2.getIndicator(indicator))){
                    return -1;
                }
            }
            else {
                  if (country1.getName().compareTo(country2.getName()) < 0){
                      return -1;
                  } else if (country1.getName().compareTo(country2.getName()) > 0) {
                      return 1;
                  } else {
                      return 0;
                  }
            }
        }
        else {
            if (country1.getIndicator(indicator) > country2.getIndicator(indicator)) {
                return -1;
            }
            else if (country1.getIndicator(indicator) < country2.getIndicator(indicator)) {
                return 1;
            }
            else {
                if (Double.isNaN(country1.getIndicator(indicator)) || Double.isNaN(country2.getIndicator(indicator))){
                    if (Double.isNaN(country1.getIndicator(indicator)) && Double.isNaN(country2.getIndicator(indicator))){
                        return country1.getName().compareTo(country2.getName());
                    }
                    if (Double.isNaN(country1.getIndicator(indicator)) && !Double.isNaN(country2.getIndicator(indicator))){
                        return 1;
                    }
                    if (!Double.isNaN(country1.getIndicator(indicator)) && Double.isNaN(country2.getIndicator(indicator))){
                        return -1;
                    }
                }
          }
              if (country1.getName().compareTo(country2.getName()) < 0){
                    return -1;
              } else if (country1.getName().compareTo(country2.getName()) > 0) {
                    return 1;
              }
          }
        return 0;
        }
}
