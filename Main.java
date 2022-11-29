package budget;
import java.util.*;


/*
 *  This budget manager allows -
 *        - Add income
 *        - Add different type of purchase
 *        - Show list of purchases
 *        - Show balance
 *        - Save all records
 *        - Load all records
 *        - Sort purchases by price.
 */
public class Main {
    final private static Scanner readIp = new Scanner(System.in);

    public static void main(String[] args) {
        BudgetManager budgetManager = new BudgetManager();
        int action = -1;
        int purchaseType = -1;
        String purchaseDetails = "";
        double price = 0.0;

        while (action != 0) {
            System.out.println();
            printInstruction();
            action = Integer.parseInt(readIp.nextLine());
            System.out.println();

            switch (action) {
                case 1:
                    System.out.println("Enter income:");
                    double amount = Double.parseDouble(readIp.nextLine());
                    budgetManager.addIncome(amount);
                    System.out.println();
                    break;
                case 2:
                    while (purchaseType != 5) {
                        System.out.println();
                        purchaseAddInstruction();
                        purchaseType = Integer.parseInt(readIp.nextLine());

                        if (purchaseType != 5) {
                            System.out.println("\nEnter purchase name:");
                            purchaseDetails = readIp.nextLine();
                            System.out.println("Enter its price:");
                            price = Double.parseDouble(readIp.nextLine());
                        }
                        switch (purchaseType) {
                            case 1 -> budgetManager.addFood(purchaseDetails, price);
                            case 2 -> budgetManager.addClothes(purchaseDetails, price);
                            case 3 -> budgetManager.addEntertainment(purchaseDetails, price);
                            case 4 -> budgetManager.addOthers(purchaseDetails, price);
                        }
                        System.out.println();
                    }
                    break;
                case 3:
                    System.out.println();
                    if (budgetManager.isShoppingListEmpty()) {
                        System.out.println("The purchase list is empty!");
                        System.out.println();
                    } else {
                        while (purchaseType != 6) {
                            purchaseShowInstruction();
                            purchaseType = Integer.parseInt(readIp.nextLine());
                            System.out.println();
                            switch (purchaseType) {
                                case 1 -> budgetManager.showFoodPurchases();
                                case 2 -> budgetManager.showClothesPurchases();
                                case 3 -> budgetManager.showEntertainmentPurchases();
                                case 4 -> budgetManager.showOthersPurchases();
                                case 5 -> budgetManager.showAllPurchases();
                            }
                            System.out.println();
                        }
                    }
                    break;
                case 4:
                    budgetManager.showBalance();
                    System.out.println();
                    break;
                case 5:
                    budgetManager.savePurchases();
                    break;
                case 6:
                    budgetManager.loadPurchases();
                    break;
                case 7:
                    int sortType = 0;

                    while (sortType != 4) {
                        sortInstruction();
                        sortType = Integer.parseInt(readIp.nextLine());
                        System.out.println();
                        switch (sortType) {
                            case 1:
                                budgetManager.sortByCategory("All");
                                break;
                            case 2:
                                budgetManager.sortCategories();
                                break;
                            case 3:
                                sortCategoryInstruction();
                                int categoryType = Integer.parseInt(readIp.nextLine());
                                System.out.println();

                                switch (categoryType) {
                                    case 1:
                                        budgetManager.sortByCategory("Food");
                                        break;
                                    case 2:
                                        budgetManager.sortByCategory("Clothes");
                                        break;
                                    case 3:
                                        budgetManager.sortByCategory("Entertainment");
                                        break;
                                    case 4:
                                        budgetManager.sortByCategory("Other");
                                        break;
                                }
                        }
                    }
                    break;
                case 0:
                    System.out.println("Bye!");
                    break;
            }
        }
    }

    private static void sortInstruction() {
        System.out.println("""
                How do you want to sort?
                1) Sort all purchases
                2) Sort by type
                3) Sort certain type
                4) Back""");
    }

    private static void sortCategoryInstruction() {
        System.out.println("""
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other""");
    }

    private static void purchaseAddInstruction() {
        System.out.println("""
                Choose the type of purchases
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) Back""");
    }

    private static void purchaseShowInstruction() {
        System.out.println("""
                Choose the type of purchases
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) All
                6) Back""");
    }

    private static void printInstruction() {
        System.out.println("""
                Choose your action:
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                5) Save
                6) Load
                7) Analyze (Sort)
                0) Exit""");
    }
}

