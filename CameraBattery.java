package hw1;

/**
 * This creates a simulated model of a camera battery
 * 
 * @author Ethan Gruening
 *
 */
public class CameraBattery {

	/**
	 * Number of external charger settings. 
	 * Settings are numbered between 0 inclusive and 4 exclusive
	 */
	public static final int NUM_CHARGER_SETTINGS = 4;
	
	/**
	 * A constant used in calculating the charge rate of the external charger.
	 */
	public static final double CHARGE_RATE = 2.0;
	
	/**
	 * Default power consumption of the camera at the start of the simulation
	 */
	public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0;
	
	/**
	 * Holds the value of the charge of the battery 
	 */
	private double charge;
	
	/**
	 * Holds the value of the maximum charge the battery can have
	 */
	private double capacity;
	
	/**
	 * Holds the value of the current setting of the battery
	 */
	private int setting;
	
	/**
	 * Temporary variable used to find the change in the charge over time
	 */
	private double oldCharge;
	
	/**
	 * Keeps track of the total drain of the battery over time
	 */
	private double totalBatteryDrain;
	
	/**
	 * Hold's a true value 1 if the battery is connected to the camera and 0 if it's disconnected
	 */
	private int cameraConnection;
	
	/**
	 * Hold's a true value 1 if the battery is connected to the external charger and 0 if it's disconnected
	 */
	private int externalConnection;
	
	/**
	 * The rate at which the camera uses the battery power
	 */
	private double cameraPowerConsumption;
	
	/**
	 * Constructs a new camera battery simulation. 
	 * @param batteryStartingCharge
	 * The starting charge of the battery 
	 * @param batteryCapacity
	 * The charging capacity of the battery
	 * 
	 */
	
	public CameraBattery(double batteryStartingCharge, double batteryCapacity) {
		
		capacity = batteryCapacity;
		
		charge = Math.min(batteryStartingCharge, batteryCapacity);
		
		setting = 0;
		
		cameraConnection = 0;
		
		externalConnection = 0;
		
		totalBatteryDrain = 0;
		
		cameraPowerConsumption = DEFAULT_CAMERA_POWER_CONSUMPTION;
	}
	
	/**
	 * Indicates the user has pressed the setting button one time on the external charger.
	 * The charge setting increments by one or if already at the maximum setting wraps around to setting 0.
	 */
	
	public void buttonPress() {
		setting = (setting + 1) % NUM_CHARGER_SETTINGS;
	}
	
	/**
	 * Charges the battery connected to the camera for a given number of minutes.
	 * @param minutes
	 * The amount of minutes the battery was charged
	 * @return
	 * The actual amount the battery has been charged
	 */
	public double cameraCharge(double minutes) {
		
		oldCharge = charge;
		
		charge = Math.min(charge  + minutes * CHARGE_RATE * cameraConnection, capacity);
		
		return charge - oldCharge; 
		
	}
	
	/**
	 * Drains the battery connected to the camera for a given number of minutes.
	 * @param minutes
	 * The amount of minutes the battery was connected to the camera
	 * @return
	 * The actual amount drain from the battery
	 */
	public double drain(double minutes) {
		
		oldCharge = charge;
		
		charge = Math.max(charge - minutes * cameraPowerConsumption * cameraConnection, 0);
		
		totalBatteryDrain += oldCharge - charge;
		
		return oldCharge - charge;
	}
	
	/**
	 * Charges the battery connected to the external charger for a given number of minutes.
	 * @param minutes
	 * The number of minutes connect to external charger
	 * @return
	 * The actual amount the battery has been charged
	 */
	public double externalCharge(double minutes) {
		
		oldCharge = charge;
		
		charge = Math.min(charge  + minutes * CHARGE_RATE * setting * externalConnection, capacity);
		
		return charge - oldCharge; 
	}
	
	/**
	 * Resets the battery monitoring system by setting the total battery drain count back to 0.
	 */
	public void resetBatteryMonitor() {
		
		totalBatteryDrain = 0;
		
		
	}
	
	/**
	 * Get the battery's capacity.
	 * @return
	 * The battery capacity
	 */
	public double getBatteryCapacity() {
		
		return capacity;
		
	}
	
	/**
	 * Get the battery's current charge.
	 * @return
	 * The battery's current charge
	 */

	public double getBatteryCharge() {
		
		return charge;
		
	}
	
	/**
	 * Get current charge of the camera's battery
	 * @return
	 * The current charge of the camera's battery
	 */
	public double getCameraCharge() {
		return charge * cameraConnection;
	}
	
	/**
	 * Get the power consumption of the camera
	 * @return 
	 * The power consumption of the camera
	 */
	
	public double getCameraPowerConsumption() {
		
		return cameraPowerConsumption;
	}
	
	
	/**
	 * Get the external charger setting
	 * @return
	 * The external charger setting
	 */
	public int getChargerSetting() {
		
		return setting;
	}
	
	/**
	 * Get the total amount of power drained from the battery since the last time the battery monitor was started or reset
	 * @return
	 * The total amount of power drained from battery since started or reset
	 */
	public double getTotalDrain() {
		
		return totalBatteryDrain;
		
	}
	
	/**
	 * Move the battery to the external charger
	 */
	public void moveBatteryExternal() {
		
		cameraConnection = 0;
		externalConnection = 1;
		
	}
	
	/**
	 * Move the battery to the camera
	 */
	public void moveBatteryCamera() {
		
		externalConnection = 0;
		cameraConnection = 1;
	}
	
	/**
	 * Remove the battery from either the camera or external charger
	 */
	public void removeBattery() {
		
		externalConnection = 0;
		cameraConnection = 0;
		
	}
	
	/**
	 * Set the power consumption of the camera
	 * @param cameraPowerConsumption
	 * Desired power consumption
	 */
	public void setCameraPowerConsumption(double cameraPowerConsumption) {
		
		this.cameraPowerConsumption = cameraPowerConsumption;
		
	}
	

}