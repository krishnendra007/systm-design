class Slot {
    private int id;
    VehicleType type;
    private boolean isFree;

    public Slot(int id, VehicleType type) {
        this.id = id;
        this.type = type;
        this.isFree = true;
    }

    public boolean canFit(Vehicle vehicle) {
        return this.type == vehicle.getType();
    }



    public boolean isFree() {
        return isFree;
    }

    public void park() {
        this.isFree = false;
    }

    public void unpark() {
        this.isFree = true;
    }

    public int getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }
}