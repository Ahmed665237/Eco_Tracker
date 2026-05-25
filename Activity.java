import java.time.LocalDate;
import java.util.UUID;

//Every activity has a date, ID, and a category it belongs to
abstract class Activity {
    protected final String activityID;
    protected final String category; //"Transport", "Water", etc.
    protected final LocalDate date;

    public Activity(LocalDate date, String category) {
        this.activityID = UUID.randomUUID().toString(); //Universally Unique Identifier ID
        if (date == null || category == null)
            throw new IllegalArgumentException("Null values not allowed");
        this.category = category;
        this.date = date;
    }

    public abstract double calculateCO2();

    //getters
    public String getActivityID() {
        return activityID;
    }
    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }
}

final class EmissionFactors{
    //Transport(kg per km)
    public static final double CAR         = 0.21;
    public static final double BUS         = 0.089;
    public static final double METRO       = 0.041;
    public static final double MOTORCYCLE  = 0.12;
    public static final double TAXI        = 0.25;
    public static final double TRAIN       = 0.05;
    public static final double ELECTRIC_CAR = 0.08;

    //Electricity(kg per kWh)
    public static final double GRID_KWH    = 0.233;

    //Device wattage estimates
    public static final double WATTS_AC_HEATER   = 2000;
    public static final double WATTS_TECH         = 800;
    public static final double WATTS_KITCHEN      = 1600;
    public static final double WATTS_FAN          = 75;
    public static final double WATTS_DEFAULT      = 100;

    // Water(kg per litre)
    public static final double WATER_KG_PER_LITRE = 0.0003;

    //Food(kg CO₂ per serving)
    public static final double FOOD_BEEF      = 27.0;
    public static final double FOOD_CHICKEN   = 6.9;
    public static final double FOOD_FISH      = 5.0;
    public static final double FOOD_VEG       = 0.5;
    public static final double FOOD_RICE      = 2.7;
    public static final double FOOD_DAIRY     = 3.0;
    public static final double FOOD_FAST_FOOD = 5.5;

    // Waste(kg CO₂ per unit)
    public static final double WASTE_PLASTIC_BOTTLE = 0.1;
    public static final double WASTE_PLASTIC_BAG    = 0.04;
    public static final double WASTE_PAPER          = 0.02;
    public static final double WASTE_GLASS          = 0.03;
    public static final double WASTE_BATTERY        = 0.8;
    public static final double WASTE_ELECTRONICS    = 1.0;
}

//5 Subclasses (Inheritance is widely used in this part)
class TransportationActivity extends Activity {
    private String vehicleType;
    private double distanceKm; //make sure that the user inputs distance in (km)

    public TransportationActivity(LocalDate date, String vehicleType, double distanceKm){
        super(date, "Transport");
        if (distanceKm < 0)
            throw new IllegalArgumentException("Distance cannot be negative");
        this.vehicleType=vehicleType;
        this.distanceKm=distanceKm;
    }

    public String getVehicleType(){
        return vehicleType;}

    public double getDistanceKm(){
        return distanceKm;}

    @Override
    public double calculateCO2(){
        return switch (vehicleType) {
            case "Car"->distanceKm * EmissionFactors.CAR;
            case "Bus"->distanceKm * EmissionFactors.BUS;
            case "Metro"->distanceKm * EmissionFactors.METRO;
            case "Motorcycle"->distanceKm * EmissionFactors.MOTORCYCLE;
            case "Taxi"->distanceKm * EmissionFactors.TAXI;
            case "Train"-> distanceKm * EmissionFactors.TRAIN;
            case "Electric Car"->distanceKm * EmissionFactors.ELECTRIC_CAR;
            case "Bike/Walk"->0.0;
            default -> throw new IllegalArgumentException("Invalid selection: " + vehicleType);
        };
    }
}
class ElectricityActivity extends Activity {
    private String deviceType;
    private double hoursUsed;

    public ElectricityActivity(LocalDate date, String deviceType, double hoursUsed){
        super(date, "Electricity");
        if (hoursUsed < 0)
            throw new IllegalArgumentException("Hours used cannot be negative");
        this.deviceType = deviceType;
        this.hoursUsed = hoursUsed;

    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setHoursUsed(double hoursUsed) {
        this.hoursUsed = hoursUsed;
    }

    @Override
    public double calculateCO2() {
        double watts= switch (this.deviceType) {
            case "AC/Heater"->EmissionFactors.WATTS_AC_HEATER;
            case "Tech Devices"->EmissionFactors.WATTS_TECH;
            case "Kitchen Appliances"->EmissionFactors.WATTS_KITCHEN;
            case "Fan"->EmissionFactors.WATTS_FAN;
            default ->EmissionFactors.WATTS_DEFAULT;
        }
                ;
        double kwh=(watts/ 1000.0) * hoursUsed;
        return kwh * EmissionFactors.GRID_KWH;
    }
}