package budget;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/*
 *  This class store various purchase list and show statistics about them.
 */

public class BudgetManager implements Serializable {

    public static final DecimalFormat df = new DecimalFormat("0.00");
    final private LinkedHashSet<String> foodList;
    final private LinkedHashSet<String> clothesList;
    final private LinkedHashSet<String> entertainmentList;
    final private LinkedHashSet<String> othersList;
    final private Map<Categories, Double> balanceRecords;
    private double totalBalance;
    final private String pathToFile;
    public static final String separator = "############################";

    BudgetManager() {
        this.pathToFile = "./purchases.txt";
        this.othersList = new LinkedHashSet<>();
        this.entertainmentList = new LinkedHashSet<>();
        this.foodList = new LinkedHashSet<>();
        this.clothesList = new LinkedHashSet<>();
        this.totalBalance = 0.0;

        this.balanceRecords = new LinkedHashMap<>();
        for (Categories category : Categories.values()) {
            this.balanceRecords.put(category, 0.0);
        }
    }

    protected void addOthers(String details, double price) {
        this.othersList.add(details + " $" + df.format(price));
        this.balanceRecords.put(Categories.Other, this.balanceRecords.get(Categories.Other) + price);
        updateBalance(price);

        System.out.println("Purchase was added!");

    }

    protected void addEntertainment(String details, double price) {
        this.entertainmentList.add(details + " $" + df.format(price));
        this.balanceRecords.put(Categories.Entertainment, this.balanceRecords.get(Categories.Entertainment) + price);
        updateBalance(price);

        System.out.println("Purchase was added!");
    }

    protected void addClothes(String details, double price) {
        this.clothesList.add(details + " $" + df.format(price));
        this.balanceRecords.put(Categories.Clothes, this.balanceRecords.get(Categories.Clothes) + price);
        updateBalance(price);

        System.out.println("Purchase was added!");
    }

    protected void addFood(String details, double price) {
        this.foodList.add(details + " $" + df.format(price));
        this.balanceRecords.put(Categories.Food, this.balanceRecords.get(Categories.Food) + price);

        updateBalance(price);

        System.out.println("Purchase was added!");
    }

    private void updateBalance(double price) {
        if (this.totalBalance >= price) {
            this.totalBalance -= price;
        } else {
            this.totalBalance = 0.0;
        }
    }

    protected void showAllPurchases() {
        LinkedHashSet<String> allPurchases = getAllPurchases();

        System.out.println("All:");
        for (String purchase : allPurchases) {
            System.out.println(purchase);
        }

        double totalPurchase = 0.0;
        for (Categories category : this.balanceRecords.keySet()) {
            totalPurchase += this.balanceRecords.get(category);
        }
        System.out.printf("Total sum: $%.2f\n", totalPurchase);
    }

    private LinkedHashSet<String> getAllPurchases() {
        LinkedHashSet<String> allPurchases = new LinkedHashSet<>();
        allPurchases.addAll(foodList);
        allPurchases.addAll(clothesList);
        allPurchases.addAll(entertainmentList);
        allPurchases.addAll(othersList);
        return allPurchases;
    }

    protected void showClothesPurchases() {
        System.out.println("Clothes:");
        if (this.clothesList.isEmpty()) {
            System.out.println("The purchase list is empty!");
        } else {
            for (String purchase : this.clothesList) {
                System.out.println(purchase);
            }
            System.out.printf("Total sum: $%.2f\n", this.balanceRecords.get(Categories.Clothes));
        }
    }

    protected void showOthersPurchases() {
        System.out.println("Other:");
        if (this.othersList.isEmpty()) {
            System.out.println("The purchase list is empty!");
        } else {
            for (String purchase : this.othersList) {
                System.out.println(purchase);
            }
            System.out.printf("Total sum: $%.2f\n", this.balanceRecords.get(Categories.Other));
        }
    }

    protected void showEntertainmentPurchases() {
        System.out.println("Entertainment:");
        if (this.entertainmentList.isEmpty()) {
            System.out.println("The purchase list is empty!");
        } else {
            for (String purchase : this.entertainmentList) {
                System.out.println(purchase);
            }
            System.out.printf("Total sum: $%.2f\n", this.balanceRecords.get(Categories.Entertainment));
        }
    }

    protected void showFoodPurchases() {
        System.out.println("Food:");
        if (this.foodList.isEmpty()) {
            System.out.println("The purchase list is empty!");
        } else {
            for (String purchase : this.foodList) {
                System.out.println(purchase);
            }
            System.out.printf("Total sum: $%.2f\n", this.balanceRecords.get(Categories.Food));
        }
    }

    protected void showBalance() {
        System.out.printf("Balance: $%.2f", this.totalBalance);
    }

    protected void addIncome(double amount) {
        this.totalBalance += amount;
        System.out.println("Income was added!");

    }

    protected boolean isShoppingListEmpty() {
        return this.clothesList.isEmpty() && this.foodList.isEmpty() && this.entertainmentList.isEmpty() && this.othersList.isEmpty();
    }

    protected void savePurchases() {

        StringBuilder purchaseHistory = new StringBuilder();
        purchaseHistory.append("Food: ".concat(foodList.toString()));
        purchaseHistory.append(separator);
        purchaseHistory.append("Clothes: ".concat(clothesList.toString()));
        purchaseHistory.append(separator);
        purchaseHistory.append("Entertainment: ".concat(entertainmentList.toString()));
        purchaseHistory.append(separator);
        purchaseHistory.append("Other: ".concat(othersList.toString()));
        purchaseHistory.append(separator);
        purchaseHistory.append("Balance: ".concat(String.valueOf(totalBalance)));

        try {
            FileOutputStream fileOutputStream
                    = new FileOutputStream(this.pathToFile);
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(purchaseHistory);
            objectOutputStream.flush();
            objectOutputStream.close();
            System.out.println("Purchases were saved!");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    protected void loadPurchases() {

        Object data = "";

        try {
            FileInputStream fileInputStream
                    = new FileInputStream(this.pathToFile);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            data = objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException ex1) {
            ex1.getMessage();
        }

        updatePurchaseDetails(data.toString());
        System.out.println("Purchases were loaded!");
    }

    private void updatePurchaseDetails(String data) {
        String[] purchaseHistory = data.split(separator);

        for (String input : purchaseHistory) {
            String[] list = input.split(":");
            String category = list[0].trim();

            switch (category) {
                case "Food" -> {
                    String[] items = list[1].substring(2, list[1].length() - 1).split(",");
                    for (String item : items) {
                        if (foodList.add(item.trim())) {
                            int priceIndex = item.lastIndexOf("$");
                            double price = Double.parseDouble(item.substring(priceIndex + 1));
                            this.balanceRecords.put(Categories.Food, this.balanceRecords.get(Categories.Food) + price);
                        }
                    }
                }
                case "Clothes" -> {
                    String[] items = list[1].substring(2, list[1].length() - 1).split(",");
                    for (String item : items) {
                        if (clothesList.add(item.trim())) {
                            int priceIndex = item.lastIndexOf("$");
                            double price = Double.parseDouble(item.substring(priceIndex + 1));
                            this.balanceRecords.put(Categories.Clothes, this.balanceRecords.get(Categories.Clothes) + price);
                        }
                    }
                }
                case "Entertainment" -> {
                    String[] items = list[1].substring(2, list[1].length() - 1).split(",");
                    for (String item : items) {
                        if (entertainmentList.add(item.trim())) {
                            int priceIndex = item.lastIndexOf("$");
                            double price = Double.parseDouble(item.substring(priceIndex + 1));
                            this.balanceRecords.put(Categories.Entertainment, this.balanceRecords.get(Categories.Entertainment) + price);
                        }
                    }
                }
                case "Other" -> {
                    String[] items = list[1].substring(2, list[1].length() - 1).split(",");

                    for (String item : items) {
                        if (othersList.add(item.trim())) {
                            int priceIndex = item.lastIndexOf("$");
                            double price = Double.parseDouble(item.substring(priceIndex + 1));
                            this.balanceRecords.put(Categories.Other, this.balanceRecords.get(Categories.Other) + price);
                        }
                    }
                }
                case "Balance" -> {
                    double balance = Double.parseDouble(list[1]);
                    this.totalBalance += balance;
                }
            }
        }

    }

    protected void sortCategories() {
        String[] category = new String[Categories.values().length];
        int index = 0;
        for (Categories cat : this.balanceRecords.keySet()) {
            category[index] = cat.name().concat(" - $").concat(df.format(this.balanceRecords.get(cat)));
            index++;
        }

        Sort.mergeSort(category, category.length);

        for (String cat : category) {
            System.out.println(cat);
        }
        System.out.println();
    }

    protected void sortByCategory(String category) {
        String[] purchaseList = null;

        switch (category) {
            case "Food" -> purchaseList = this.foodList.toArray(new String[0]);
            case "Clothes" -> purchaseList = this.clothesList.toArray(new String[0]);
            case "Entertainment" -> purchaseList = this.entertainmentList.toArray(new String[0]);
            case "Other" -> purchaseList = this.othersList.toArray(new String[0]);
            case "All" -> {
                LinkedHashSet<String> allPurchases = getAllPurchases();
                purchaseList = allPurchases.toArray(new String[0]);
            }
        }

        if (purchaseList.length == 0) {
            System.out.println("The purchase list is empty!");
            System.out.println();
        } else {
            Sort.mergeSort(purchaseList, purchaseList.length);
            System.out.println(category + ":");
            for (String purchase : purchaseList) {
                System.out.println(purchase);
            }
            System.out.println();
        }


    }

}