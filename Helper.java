package sthlm.malmo.christofferwiregren.gogogreen;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * Helper class that provides with search and sort methods
 *
 */
public class Helper {


    /**
     * Create a unique ID
     * @return
     * @throws Exception
     */
    public static String createTransactionID() {
        return "x1x"+String.valueOf(UUID.randomUUID());
    }

    /**
     * Sort a list by name
     * @param vegetables
     */
    public static void sortedByName(List<Vegetable> vegetables) {

        Collections.sort(vegetables, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Vegetable p1 = (Vegetable) o1;
                Vegetable p2 = (Vegetable) o2;
                return p1.getName().compareToIgnoreCase(p2.getName());
            }
        });

    }

    /**
     * Sort a list by ID
     * @param vegetableList
     */

    public static List<Vegetable> searchInListByName(final String name, List<Vegetable> vegetableList) {

        return vegetableList.stream()
                .filter(item -> item.getName().contains(name))
                .collect(Collectors.toList());
    }


    /**
     *
     * @param name
     */

    public static Boolean searchInListIfNameExists(
            String name, List<Vegetable> vegetables) {

        for (Vegetable customer : vegetables) {
            if (customer.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static int searchIndexOfNameInList(String name, List<Vegetable> vegetables) {

        for (Vegetable customer : vegetables) {
            if (customer.getName().equals(name)) {
                return vegetables.indexOf(customer);
            }
        }
        return -1;
    }

    /**
     *
     * Search in a list by using a ID.
     * @param name
     * @param vegetableList
     * @return
     */

    public static List<Vegetable> searchInListByID(final String name, List<Vegetable> vegetableList) {

        return vegetableList.stream()
                .filter(item -> item.getID().contains(name))
                .collect(Collectors.toList());
    }

    /**
     * Sorted list by Amount.
     * @param vegetables
     */

    public static void sortedByAmount(List<Vegetable> vegetables) {

        Collections.sort(vegetables,
                new Comparator<Vegetable>()
                {
                    public int compare(Vegetable o1,
                                       Vegetable o2)
                    {
                        if (o1.getQuantity() ==
                                o2.getQuantity())
                        {
                            return 0;
                        }
                        else if (o1.getQuantity() >
                                o2.getQuantity())
                        {
                            return -1;
                        }
                        return 1;
                    }
                });
    }


    /**
     *
     * Sorted list by price
     * @param vegetables
     */

    public static void sortedByPrice(List<Vegetable> vegetables) {

        Collections.sort(vegetables,
                new Comparator<Vegetable>()
                {
                    public int compare(Vegetable o1,
                                       Vegetable o2)
                    {
                        if (o1.getPrice() ==
                                o2.getPrice())
                        {
                            return 0;
                        }
                        else if (o1.getPrice() >
                                o2.getPrice())
                        {
                            return -1;
                        }
                        return 1;
                    }
                });
    }

    /**
     *
     * Sorted list by price
     * @param vegetables
     */

    public static void sortedByTotal(List<Vegetable> vegetables) {

        Collections.sort(vegetables,
                (o1, o2) -> {
                    if (o1.getPrice() ==
                            o2.getPrice())
                    {
                        return 0;
                    }
                    else if (o1.getTotal() >
                            o2.getTotal())
                    {
                        return -1;
                    }
                    return 1;
                });
    }

    /**
     *
     * Check if a string contains numeric characters
     * @param str
     */
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
