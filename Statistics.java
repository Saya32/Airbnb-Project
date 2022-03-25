
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class will present a series of statistics, based on the information derived from the data set,
 * and should thus be available at the same time as the map to be observed by the user, should they
 * navigate to this panel. We will derive eight statistics over the data available.
 *
 * @Sayaka, Janet, Apria and Jayden
 * @version 22.03.2022
 */
public class Statistics extends JPanel
{
    // instance variables
    private JLabel stats;
    private ArrayList<String> statistics;
    private ArrayList<AirbnbListing> listing;
    private static final String[] BOROUGHS  = {"Barking and Dagenham","Bromley","Ealing","Havering",
            "Hillingdon","Harrow","Brent","Barnet", "Enfield", "Redbridge", "Sutton", "Lambeth", "Southwark",
            "Greenwich","Lewisham","Hounslow", "Bexley","Kingston upon Thames", "Merton", "Wandsworth","Hammersmith and Fulham",
            "Kensington and Chelsea","City of London","Westminster","Camden","Tower Hamlets", "Islington", "Hackney","Haringey",
            "Newham","Croydon","Waltham Forest", "Richmond upon Thames"};
    private int intialStatistics;
    private JPanel statisticsPanel,bottomPanel,previousPanel,nextPanel;
    private JButton previousStat, nextStat;

    /**
     * Constructor for objects of class Statistics
     */
    public Statistics()
    {
        // initialise instance variables
        AirbnbDataLoader loader = new AirbnbDataLoader();
        listing = loader.load();
        displayStatisticDetails();
        intialStatistics = 0;
        statisticsFrame();
    }
    
    public void displayStatisticDetails()
    {
        statistics = new ArrayList<>();
        statistics.add( "Average number of reviews per property: "+  Integer.toString(averageNumberOfReviewsPerProperty()));
        statistics.add("Total number of available properties: " + Integer.toString(totalNumberOfAvailableProperties ()));
        statistics.add("Number of entire apartments: " + Integer.toString(numberOfEntireHomesApartments()));
        statistics.add("Priciest neighborhood : " + priciestNeighborhood  ());
        statistics.add("Neighborhood with the most properties : " + neighbourhoodWithLeastProperites());
        statistics.add("Average price per night : " + averagePricePerNight());
        statistics.add("Host with the most properties : " + mostPropertiesHostDetails());
    }
    
    /**
     * Constructor for objects of class Statistics
     */
    private int averageNumberOfReviewsPerProperty()
    {
        int totalNumberOfReviews = 0; // initalise total number of reviews as 0

        for(AirbnbListing property : listing)
        {
            totalNumberOfReviews+=property.getNumberOfReviews();
        }
        return totalNumberOfReviews/listing.size();
    }
    
    /**
     * Constructor for objects of class Statistics
     */
    private int totalNumberOfAvailableProperties ()
    {
        int totalNumberOfAvailableProperties =0;
        for(AirbnbListing property  : listing)
        {
            if(property.getAvailability365()>0) {
                totalNumberOfAvailableProperties ++;
            }
        }
        return totalNumberOfAvailableProperties;
    }
    
    /**
     * Constructor for objects of class Statistics
     */
    private int numberOfEntireHomesApartments()
    {
        int numberOfEntireHomesApartments =0;
        for(AirbnbListing property: listing)
        {
            if(property.getRoom_type().equals("Entire homes and apartment"))
            {
                numberOfEntireHomesApartments++;
            }
        }
        return   numberOfEntireHomesApartments;
    }
    
    /**
     * Constructor for objects of class Statistics
     */
    private String priciestNeighborhood  ()
    {
        int maxPrice =0;
        String neighborhood = "";
        for(AirbnbListing property : listing)
        {
            int price = property.getPrice() * property.getMinimumNights();
            if(price>maxPrice)
            {
                maxPrice=price;
                neighborhood = property.getNeighbourhood();
            }
        }
        return neighborhood;
    }
    
    /**
     * Constructor for objects of class Statistics
     */
    private int averagePricePerNight   ()
    {
        int total =0;
        for(AirbnbListing property : listing)
        {
           total+=property.getPrice();
        }
        return total/listing.size();
    }
    
    /**
     * Constructor for objects of class Statistics
     */
    private String mostPropertiesHostDetails()
    {
        int max = 0;
        String host = "";
        for(AirbnbListing property : listing)
        {
            String hostName = property.getHost_name();
            String hostID = property.getHost_id();
            if(property.getCalculatedHostListingsCount() > max)
            {
                max = property.getCalculatedHostListingsCount();
                host = "Name : " + hostName + "  " +"ID: " + hostID;
            }
        }
        return host;
    }
    
    private int countProperties(String borough)
    {
        int count =0;
        for(AirbnbListing property : listing)
        {
            if(property.getNeighbourhood().equals(borough))
            {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Constructor for objects of class Statistics
     */
    private String neighbourhoodWithLeastProperites()
    {   
        int leastNumberOfProperties =0;
        String leastPopulatedNeighborhood ="";
        for (String name : BOROUGHS)
        {
           if(countProperties(name)< leastNumberOfProperties)
           {
               leastNumberOfProperties = countProperties(name);
               leastPopulatedNeighborhood = name;
           }
        }
        return leastPopulatedNeighborhood;
    }

    /**
     * Constructor for objects of class Statistics
     */
    public void statisticsFrame()
    {
        setLayout(new BorderLayout());

        // Initialises all the labels and buttons.
        JButton previousStat = new JButton("<");
        previousStat.addActionListener(e-> getPreviousStat());
        
        JButton nextStat = new JButton(">");
        nextStat.addActionListener(e-> goToNextStat());
        
        stats = new JLabel("statistics");
        stats.setFont(new Font("Verdana", Font.BOLD, 30));


        // Initialises all the panels and makes the layout and panels.
        statisticsPanel = new JPanel(new GridBagLayout());
        statisticsPanel.setBackground(Color.WHITE);
        statisticsPanel.add(stats);
        
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
        
        previousPanel = new JPanel(new BorderLayout());
        previousPanel.setBackground(Color.WHITE);
        previousPanel.add(previousStat,BorderLayout.CENTER);
         
        nextPanel = new JPanel(new BorderLayout());
        nextPanel.setBackground(Color.WHITE);
        nextPanel.add(nextStat,BorderLayout.CENTER);

    }
    
    private void goToNextStat()
    {
       stats.setText(statistics.get(++intialStatistics % statistics.size()));
    }

    private void getPreviousStat()
    {
        --intialStatistics;
       intialStatistics%=statistics.size();
        if(intialStatistics < 0)
        {
            intialStatistics = statistics.size()-1;
        }
        stats.setText(statistics.get(intialStatistics));
    }

}
