package budget;

/*
 *  This class sort given purchase list using merge sort based o price.
 */
public class Sort {

    protected static void mergeSort(String[] purchaseList, int size) {
        if (size < 2) {
            return;
        }

        int midIndex = size / 2;

        String[] leftList = new String[midIndex];
        String[] rightList = new String[size - midIndex];

        for (int i = 0; i < midIndex; i++) {
            leftList[i] = purchaseList[i];
        }

        for (int i = midIndex; i < size; i++) {
            rightList[i - midIndex] = purchaseList[i];
        }

        mergeSort(leftList, leftList.length);
        mergeSort(rightList, rightList.length);

        mergeList(purchaseList, leftList, rightList, leftList.length, rightList.length);

    }

    private static void mergeList(String[] purchaseList, String[] leftList, String[] rightList, int leftLength, int rightLength) {
        int leftIndex = 0;
        int rightIndex = 0;
        int index = 0;

        while (leftIndex < leftLength && rightIndex < rightLength) {
            double leftPrice = extractPrice(leftList[leftIndex]);
            double rightPrice = extractPrice(rightList[rightIndex]);

            if (leftPrice > rightPrice) {
                purchaseList[index] = leftList[leftIndex];
                index++;
                leftIndex++;
            } else if (rightPrice > leftPrice) {
                purchaseList[index] = rightList[rightIndex];
                index++;
                rightIndex++;
            } else {
                purchaseList[index] = leftList[leftIndex];
                index++;
                leftIndex++;
            }
        }

        while (leftIndex < leftLength) {
            purchaseList[index] = leftList[leftIndex];
            index++;
            leftIndex++;
        }

        while (rightIndex < rightLength) {
            purchaseList[index] = rightList[rightIndex];
            index++;
            rightIndex++;
        }
    }

    private static double extractPrice(String purchase) {
        int priceIndex = purchase.lastIndexOf("$");
        double price = Double.parseDouble(purchase.substring(priceIndex + 1));
        return price;
    }

}
