import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

public class UserInterface {
    private Dealership dealership;

    public UserInterface() {
    }

    private static Scanner scanner = new Scanner(System.in);

    private void init() {
        this.dealership = DealershipFileManager.getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicleList) {
        for (Vehicle v : vehicleList) {
            System.out.println(v);
        }
    }

    public void display() {
        init();
        System.out.println("Welcome. What would you like to do?");
        scanner.nextLine();
        System.out.println("\n " +
                "1 - Find vehicles within a price range \n " +
                "2 - Find vehicles by make/model \n " +
                "3 - Find vehicles by year range \n " +
                "4 - Find vehicles by color \n " +
                "5 - Find vehicles by mileage range \n " +
                "6 - Find vehicles by type (car, truck, SUV, van \n " +
                "7 - List ALL vehicles \n " +
                "8 - Add a vehicle \n " +
                "9 - Remove a vehicle \n " +
                "10 - Make a Contract \n " +
                "99 - Quit");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Vehicles by price: ");
                processGetByPriceRequest();
                break;
            case 2:
                System.out.println("vehicles by make/model: ");
                processGetByMakeModelRequest();
                break;
            case 3:
                System.out.println("Vehicles by year range: ");
                processGetByYearRequest();
                break;
            case 4:
                System.out.println("vehicles by color: ");
                processGetByColorRequest();
                break;
            case 5:
                System.out.println("vehicles by mileage range: ");
                processGetByMileageRequest();
                break;
            case 6:
                System.out.println("vehicles by type: ");
                processGetByVehicleTypeRequest();
                break;
            case 7:
                System.out.println("ALL vehicles: ");
                processGetAllVehiclesRequest();
                break;
            case 8:
                System.out.println("Add a vehicle: ");
                processAddVehicleRequest();
                break;
            case 9:
                System.out.println("Remove a vehicle: ");
                processRemoveVehicleRequest();
                break;
            case 10:
                System.out.println("Make a contract: ");
                processCreateContract();
            case 99:
                System.out.println("Quiting...");
                System.out.println("Thank you. Have a nice day!");
                System.exit(0);
                break;
        }
    }

    public void processGetByPriceRequest() {
        scanner.nextLine();
        System.out.println("min price: ");
        double min = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("max price: ");
        double max = scanner.nextDouble();
        List<Vehicle> vehicleList = dealership.getVehicleByPrice(min, max);
        displayVehicles(vehicleList);
    }

    public void processGetByMakeModelRequest() {
        scanner.nextLine();
        System.out.println("Make: ");
        String make = scanner.nextLine();
        System.out.println("Model: ");
        String model = scanner.nextLine();
        List<Vehicle> vehicleList = dealership.getVehicleByMakeModel(make, model);
        displayVehicles(vehicleList);
    }

    public void processGetByYearRequest() {
        scanner.nextLine();
        System.out.println("min year: ");
        int min = scanner.nextInt();
        System.out.println("max year: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicleList = dealership.getVehicleByYear(min, max);
        displayVehicles(vehicleList);
    }

    public void processGetByColorRequest() {
        scanner.nextLine();
        System.out.println("What is color?");
        String color = scanner.nextLine();
        List<Vehicle> vehicleList = dealership.getVehicleByColor(color);
        displayVehicles(vehicleList);
    }

    public void processGetByMileageRequest() {
        scanner.nextLine();
        System.out.println("min mileage: ");
        int min = scanner.nextInt();
        System.out.println("max mileage: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicleList = dealership.getVehicleByMileage(min, max);
        displayVehicles(vehicleList);
    }

    public void processGetByVehicleTypeRequest() {
        scanner.nextLine();
        System.out.println("What is type?");
        String type = scanner.nextLine();
        List<Vehicle> vehicleList = dealership.getVehicleByType(type);
        displayVehicles(vehicleList);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> vehicleList = dealership.getAllVehicles();
        displayVehicles(vehicleList);
    }

    public void processAddVehicleRequest() {
        System.out.println("Vin?");
        int vin = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Year?");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Make");
        String make = scanner.nextLine();
        System.out.println("Model?");
        String model = scanner.nextLine();
        System.out.println("Vehicle type?");
        String vehicleType = scanner.nextLine();
        System.out.println("Color?");
        String color = scanner.nextLine();
        System.out.println("Odemeter?");
        int odometer = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Price?");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

        this.dealership.addVehicle(vehicle);
        DealershipFileManager.saveDealership(this.dealership);
    }

    public void processRemoveVehicleRequest() {
        System.out.println("What is vin of vehicle to be removed?");
        int vin = scanner.nextInt();
        scanner.nextLine();
        dealership.removeVehicle(vin);
        displayVehicles(this.dealership.getInventory());
        DealershipFileManager.saveDealership(this.dealership);
    }

    public void processCreateContract() {

        System.out.println("Sale or lease?");
        String saleOrLease = scanner.nextLine();
        System.out.println("Customer name?");
        String customerName = scanner.nextLine();
        System.out.println("Customer email?");
        String customerEmail = scanner.nextLine();
        System.out.println("Date of contract?");
        String date = scanner.nextLine();
        System.out.println("Vehicle vin?");
        String vin = scanner.nextLine();
        Vehicle vehicle = dealership.getVehiclesVin(Integer.parseInt(vin));

        Contract contract = createContract(saleOrLease, date, customerName, customerEmail, vehicle);

        ContractFileManager.saveContract(contract);
        dealership.removeVehicle(Integer.parseInt(vin));
        DealershipFileManager.saveDealership(dealership);
    }

    public Contract createContract(String saleOrLease, String date, String customerName, String customerEmail, Vehicle vehicle) {
        boolean finance;
        Contract contract = null;
        if (saleOrLease.equalsIgnoreCase("sale")) {
            System.out.println("Finance? y/n");
            String financeChoice = scanner.nextLine();
            finance = financeChoice.equalsIgnoreCase("y");
            contract = new SalesContract(date, customerName, customerEmail, vehicle, finance);
        } else if (saleOrLease.equalsIgnoreCase("lease")) {
            contract = new LeaseContract(date, customerName, customerEmail, vehicle);
        } else {
            System.out.println("We don't offer " + saleOrLease);
        }

        return contract;
    }
}


