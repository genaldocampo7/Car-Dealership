public class LeaseContract extends Contract {
private double expectedEndingValue;
private double leaseFee;


    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
        expectedEndingValue = vehicleSold.getPrice() * 0.5;
        leaseFee = vehicleSold.getPrice() * 0.07;
    }

    @Override
    public double getTotalPrice() {
        return getMonthlyPayment() * 36 + leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        return 0.04 * getVehicleSold().getPrice() / 12;
    }

    @Override
    public String toString() {
        return "LEASE|" + getDate() + "|" + getCustomerName() + "|" + getCustomerEmail() + "|" + getVehicleSold().toFileString() + "|" + expectedEndingValue + "|" + leaseFee + "|" + getTotalPrice() + "|"+ getMonthlyPayment();

    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }
}
