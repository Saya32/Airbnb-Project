import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * This class will present a series of statistics, based on the information derived from the data set,
 * and should thus be available at the same time as the map to be observed by the user, should they
 * navigate to this panel. We will derive eight statistics over the data available.
 *
 * @author: Sayaka, Janet, Apria and Jayden
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
    private JButton previousStat, nextStat, mapPanel;

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

    /**
     * Stores different Statistics details in an Array List which will be displayed on the Window.
     */
    public void displayStatisticDetails()
    {
        statistics = new ArrayList<>();
        statistics.add( "Average number of reviews per property: "+  Integer.toString(averageNumberOfReviewsPerProperty()));
        statistics.add("Total number of available properties: " + totalNumberOfAvailablePropertie s());
        statistics.add("Number of entire homes and apartments: " + Integer.toString(numberOfEntireHomesApartments()));
        statistics.add("Expensive Borough : " + expensiveBorough());
        statistics.add("Neighborhood with the most properties : " + neighbourhoodWithMostProperites());
        statistics.add("Average price per night : " + averagePricePerNight());
        statistics.add("Host with the most properties : " + mostPropertiesHostDetails());
        statistics.add("Prices of available properties : " + pricesOfAvailableProperties());
    }

    /**
     * @returns Average number of reviews per property.
     */
    protected int averageNumberOfReviewsPerProperty()
    {
        int totalNumberOfReviews = 0; // initalise total number of reviews as 0
        for(AirbnbListing property : listing)
        {
            totalNumberOfReviews+=property.getNumberOfReviews();
        }
        return totalNumberOfReviews/listing.size();
    }

    /**
     * Iterate through the csv file, checking the properties' availability 
     * and adding them to the list of numberOfAvailableProperties  if the value is greater than 0. 
     *
     * @return number of available properties  as Array List of integers 
     */
    private ArrayList <Integer> numberOfAvailableProperties ()
    {
        ArrayList <Integer> numberOfAvailableProperties  = new ArrayList<>();
        for(AirbnbListing property : listing)
        {
            int available = property.getAvailability365();
            if (available > 0)
            {
                numberOfAvailableProperties .add(available);
            }
        }
        return numberOfAvailableProperties ;
    }

    /**
     * @returns Total number of available properties.
     */
    protected int totalNumberOfAvailableProperties ()
    {
        return numberOfAvailableProperties ().size();
    }

    /**
     * Iterate through the csv file, checking the properties' availability 
     * and price adding them to the list of pricesOfAvailableProperties 
     * if the value avaibility is greater than 0 and price is greater than -1.
     * 
     * @return Prices of available properties as an ArrayList integer.
     */
    protected ArrayList<Integer> pricesOfAvailableProperties()
    {
        ArrayList <Integer> pricesOfAvailableProperties = new ArrayList<>();
        for(AirbnbListing property : listing)
        {
            int available = property.getAvailability365();
            int price = property.getPrice()*property.getMinimumNights();
            if (available > 0 && price > -1)
            {
                pricesOfAvailableProperties.add(price);
            }
        }
        return pricesOfAvailableProperties;
    }

    /**
     * @returns the number of entire home and apartments (as opposed to private rooms).
     */
    protected int numberOfEntireHomesApartments()
    {
        int numberOfEntireHomesApartments = 0;
        for(AirbnbListing property: listing)
        {
            if(property.getRoom_type().equals("Entire home/apt"))
            {
                numberOfEntireHomesApartments++;
            }
        }
        return  numberOfEntireHomesApartments;
    }

    /**
     * @returns the most expensive borough.
     */
    protected String expensiveBorough()
    {
        int maxPrice =0;
        String expensiveBorough = "";
        for(AirbnbListing property : listing)
        {
            int price = property.getPrice() * property.getMinimumNights();
            if(price>maxPrice)
            {
                maxPrice=price;
                expensiveBorough = property.getNeighbourhood();
            }
        }
        return expensiveBorough;
    }

    /**
     * @returns the avergae price per night.
     */
    protected int averagePricePerNight   ()
    {
        int total =0;
        for(AirbnbListing property : listing)
        {
            total+=property.getPrice();
        }
        return total/listing.size();
    }

    /**
     * @returns the name and ID of the host who owns most properties.
     */
    protected String mostPropertiesHostDetails()
    {
        int max = 0;
        String hostDetails = "";
        for(AirbnbListing property : listing)
        {
            String hostName = property.getHost_name();
            String hostID = property.getHost_id();
            if(property.getCalculatedHostListingsCount() > max)
            {
                max = property.getCalculatedHostListingsCount();
                hostDetails = "Name : "+hostName+" "+"ID: "+hostID;
            }
        }
        return hostDetails;
    }

    /**
     * Counts the number of properties for a given borough. 
     */
    protected int countProperties(String borough)
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
     * @returns the neighborhood with the most properties.
     */
    protected String neighbourhoodWithMostProperites()
    {   
        int maxNumberOfProperties =0;
        String mostPopulatedNeighborhood ="";
        for (String property : BOROUGHS)
        {
            if(countProperties(property)> maxNumberOfProperties)
            {
                maxNumberOfProperties = countProperties(property);
                mostPopulatedNeighborhood = property;
            }
        }
        return mostPopulatedNeighborhood;
    }

    /**
     * Creates the contents of the Statistics frame.
     */
    public void statisticsFrame()
    {
        setLayout(new BorderLayout());

        // Initialises all the labels and buttons.
        previousStat = new JButton("<");
        previousStat.addActionListener(e-> PreviousStat());

        nextStat = new JButton(">");
        nextStat.addActionListener(e-> NextStat());

        mapPanel = new JButton("Map");
        mapPanel.addActionListener(e-> displayMap());

        stats = new JLabel("Statistics"); 
        stats.setFont(new Font("Verdana", Font.BOLD, 15));

        // Initialises all the panels and makes the layout and panels.
        statisticsPanel = new JPanel(new GridBagLayout());
        statisticsPanel.setBackground(Color.WHITE);
        statisticsPanel.add(stats);
        add(statisticsPanel,BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
        bottomPanel.add(mapPanel, BorderLayout.WEST);
        add(bottomPanel,BorderLayout.SOUTH);

        previousPanel = new JPanel(new BorderLayout());
        previousPanel.setBackground(Color.WHITE);
        previousPanel.add(previousStat,BorderLayout.CENTER);
        add(previousPanel,BorderLayout.WEST);

        nextPanel = new JPanel(new BorderLayout());
        nextPanel.setBackground(Color.WHITE);
        nextPanel.add(nextStat,BorderLayout.CENTER);
        add(nextPanel,BorderLayout.EAST);
    }

    /**
     * Display the next statistics on window.
     */
    private void NextStat()
    {
        ++intialStatistics;
        intialStatistics %= statistics.size();
        if(intialStatistics < 0)
        {
            intialStatistics = statistics.size()+1;
        }
        stats.setText(statistics.get(intialStatistics));
    }

    /**
     * Display the previous statistics on window.
     */
    private void PreviousStat()
    {
        --intialStatistics;
        intialStatistics %= statistics.size();
        if(intialStatistics < 0)
        {
            intialStatistics = statistics.size()-1;
        }
        stats.setText(statistics.get(intialStatistics));
    }

    /**
     * It goes back to map.
     */
    private void displayMap() {
        mainWindow.frame.remove(mainWindow.stats);
        mainWindow.frame.add(mainWindow.map);
        mainWindow.frame.revalidate();
        mainWindow.frame.repaint();
    }
}
