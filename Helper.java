package sthlm.malmo.christofferwiregren.gogogreen;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Helper {


    public static String createTransactionID() throws Exception {
        return "x1x"+String.valueOf(UUID.randomUUID());
    }

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


    public static List<Vegetable> searchInListByName(final String name, List<Vegetable> vegetableList) {


        Log.e("namn", "" + name);

        return vegetableList.stream()
                .filter(item -> item.getName().contains(name))
                .collect(Collectors.toList());
    }

    public static List<Vegetable> searchInListByID(final String name, List<Vegetable> vegetableList) {


        Log.e("namn", "" + name);

        return vegetableList.stream()
                .filter(item -> item.getID().contains(name))
                .collect(Collectors.toList());
    }


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

    public static void sortedByTotal(List<Vegetable> vegetables) {

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
                        else if (o1.getTotal() >
                                o2.getTotal())
                        {
                            return -1;
                        }
                        return 1;
                    }
                });
    }

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
