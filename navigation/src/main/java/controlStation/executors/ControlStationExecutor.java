package controlStation.executors;

import java.util.List;

import com.marsroverkata.controlstation.vehicles.MarsVehicle;

/**
 * @author Alberto Senserrich Montals
 *
 */
public interface ControlStationExecutor {

	public void performActions();
	
	public void performActions(List<MarsVehicle> vehicles);
}
