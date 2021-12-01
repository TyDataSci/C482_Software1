package model;

/**
 *
 * @author Tyler Sanders
 */

public class InHouse extends Part{

    private int machineId;

    public InHouse(int id, String name, int stock, double price, int min, int max, int machineId) {
        super(id, name, stock, price, min, max);
        this.machineId = machineId;
    }
    /**
     * @return the machine id
     */

    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId sets the machine id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
