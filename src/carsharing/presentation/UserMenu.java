package carsharing.presentation;

import carsharing.business.CustomerService;
import carsharing.model.Car;
import carsharing.business.CarService;
import carsharing.model.Company;
import carsharing.business.CompanyService;
import carsharing.model.Customer;

import java.util.List;
import java.util.Scanner;

public class UserMenu {
    private final Scanner scanner;
    private final CompanyService companyService;
    private final CarService carService;
    private final CustomerService customerService;
    private List<Company> companyList;
    private List<Customer> customerList;
    private List<Car> carList;

    public UserMenu(CompanyService companyService,
                    CarService carService,
                    CustomerService customerService) {
        this.scanner = new Scanner(System.in);
        this.companyService = companyService;
        this.carService = carService;
        this.customerService = customerService;
    }

    public void startUI() {
        while (true) {
            System.out.println("1. Log in as a manager" +
                    "\n2. Log in as a customer" +
                    "\n3. Create a customer" +
                    "\n0. Exit");

            int action = Integer.valueOf(scanner.nextLine());

            switch (action) {
                case 0 -> {
                    return;
                }
                case 1 -> companyManagerMenu();
                case 2 -> {
                    Integer chosenCustomerIndex = customerPickerMenu();
                    if (!(chosenCustomerIndex == null)) {
                        customerMenu(chosenCustomerIndex);
                    }
                }
                case 3 -> addCustomerPrompt();
            }
        }
    }


    private Integer customerPickerMenu() {
        while (true) {
            updateCustomerList();

            if (isCustomerListEmpty()) {
                System.out.println("The customer list is empty!");
                break;
            }

            System.out.println("Choose a customer:");
            listAllCustomers();
            System.out.println("0. Back");

            int action = Integer.valueOf(scanner.nextLine());

            if (action == 0) {
                break;
            }

            return action - 1;
        }

        return null;
    }

    private void updateCustomerList() {
        customerList = customerService.getAllCustomers();
    }

    private void customerMenu(Integer chosenCustomerIndex) {
        int chosenCustomerID = chosenCustomerIndex + 1;
        while (true) {
            Car customerCar = customerService.getRentedCarForCustomer(chosenCustomerID);
            System.out.println("1. Rent a car" +
                            "\n2. Return a rented car" +
                            "\n3. My rented car" +
                            "\n0. Back"
            );

            int action = Integer.valueOf(scanner.nextLine());

            switch (action) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    if (customerCar != null) {
                        System.out.println("You've already rented a car!");
                        continue;
                    }

                    updateCompanyList();

                    System.out.println("Choose a company:");
                    listAllCompanies();
                    System.out.println("0. Back");

                    int chosenCompanyIndex = companyPickerMenu();
                    int chosenCompanyID = chosenCompanyIndex + 1;
                    updateCarListSelectedCompany(chosenCompanyID);

                    if (isCarListEmpty()) {
                        System.out.println("The car list is empty!");
                        continue;
                    }

                    System.out.println("Choose a car:");
                    listCars();
                    System.out.println("0. Back");

                    int input = Integer.valueOf(scanner.nextLine());

                    if (input == 0) {
                        continue;
                    }

                    int chosenCarIndex = input - 1;
                    String carName = carList.get(chosenCarIndex).getName();
                    int chosenCarID = carService.getCarIDByName(carName);
                    customerService.rentCar(chosenCustomerID, chosenCarID);
                    System.out.println("You rented \'" + carName + "\'");
                }
                case 2 -> {
                    if (customerCar == null) {
                        System.out.println("You didn't rent a car!");
                        continue;
                    }

                    customerService.returnRentedCar(chosenCustomerID);
                    System.out.println("You've returned a rented car!");
                }
                case 3 -> {
                    if (customerCar == null) {
                        System.out.println("You didn't rent a car!");
                        continue;
                    }

                    Company rentedCarCompany = companyService.getCompanyById(customerCar.getCompanyId());

                    System.out.println("Your rented car:" +
                            "\n" + customerCar.getName() +
                            "\nCompany:" +
                            "\n" + rentedCarCompany.getName());
                }
            }
        }
    }

    private void listAllCustomers() {
        for (int i = 0; i < customerList.size(); i++) {
            System.out.println((i + 1) + ". " + customerList.get(i).getName());
        }
    }

    private boolean isCustomerListEmpty() {
        if (customerList.isEmpty()) {
            return true;
        }

        return false;
    }

    private void addCustomerPrompt() {
        System.out.println("Enter the customer name:");
        String customerName = scanner.nextLine();
        customerService.addCustomer(new Customer(0, customerName));
        System.out.println("The customer was added!");
    }

    private void companyManagerMenu() {
        while (true) {
            System.out.println("1. Company list" +
                    "\n2. Create a company" +
                    "\n0. Back");

            Integer action = Integer.valueOf(scanner.nextLine());

            switch (action) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    Integer chosenCompanyIndex = companyPickerMenu();
                    if (chosenCompanyIndex != null) {
                        companyMenu(chosenCompanyIndex);
                    }
                }
                case 2 -> {
                    addCompanyPrompt();
                }
            }
        }
    }

    private void addCompanyPrompt() {
        System.out.println("Enter the company name:");
        String companyName = scanner.nextLine();
        companyService.addCompany(new Company(0, companyName));
    }

    private void listAllCompanies() {
        for (int i = 0; i < companyList.size(); i++) {
            System.out.println((i + 1) + ". " + companyList.get(i).getName());
        }
    }

    private boolean isCompanyListEmpty() {
        if (companyList.isEmpty()) {
            return true;
        }

        return false;
    }

    private Integer companyPickerMenu() {
        while (true) {
            updateCompanyList();

            if (isCompanyListEmpty()) {
                System.out.println("The company list is empty!");
                break;
            }

            System.out.println("Choose a company:");
            listAllCompanies();
            System.out.println("0. Back");

            String input = scanner.nextLine();

            if (input.equals("0")) {
                break;
            }

            return Integer.valueOf(input) - 1;
        }

        return null;
    }

    private void updateCompanyList() {
        companyList = companyService.getAllCompanies();
    }

    private void companyMenu(Integer chosenCompanyIndex) {
        System.out.println("\'" + companyList.get(chosenCompanyIndex).getName() + "\' company:" );
        Integer chosenCompanyID = chosenCompanyIndex + 1;
        while (true) {
            System.out.println(
                    "\n1. Car list" +
                    "\n2. Create a car" +
                    "\n0. Back"
            );

            String input = scanner.nextLine();

            if (input.equals("0")) {
                break;
            }

            if (input.equals("1")) {
                updateCarListSelectedCompany(chosenCompanyID);

                if (isCarListEmpty()) {
                    System.out.println("The car list is empty!");
                    continue;
                }

                System.out.println("Car list:");
                listCars();
            }

            if (input.equals("2")) {
                addCarPrompt(chosenCompanyID);
            }
        }
    }

    private void addCarPrompt(Integer companyID) {
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine();
        carService.addCar(new Car(0, carName, companyID));
        System.out.println("The car was added!");

    }

    private void updateCarListSelectedCompany(Integer companyID) {
        carList = carService.getAllAvailableCarsFromCompanyByID(companyID);
    }

    private boolean isCarListEmpty() {
        if (carList.isEmpty()) {
            return true;
        }

        return false;
    }

    private void listCars() {
        for (int i = 0; i < carList.size(); i++) {
            System.out.println((i + 1) + ". " + carList.get(i).getName());
        }
    }
}
