package pack;

import pack.cd.CdAlbum;

import java.util.Scanner;

class UIHelper {

    static Scanner sc = new Scanner(System.in);

    static CdAlbum createCdAlbumFromUser() {
        System.out.print("Provide album title : ");
        String title = sc.nextLine();

        System.out.print("Provide album artist : ");
        String artist = sc.nextLine();

        System.out.print("Provide album description : ");
        String description = sc.nextLine();


        System.out.print("Provide price you bought the album : ");
        while (!sc.hasNextDouble()) {
            System.out.println("That's not a valid floating point number!");
            System.out.println("Provide price you bought the album : ");
            sc.next(); // this is important!
        }
        double priceBought = sc.nextDouble();
        sc.nextLine();

        return new CdAlbum(artist, title, description, priceBought);
    }

    static int getIdFromUser() {
        int id;

        System.out.print("Provide album id : ");
        while (!sc.hasNextInt()) {
            System.out.println("That's not a number!");
            System.out.println("Provide album id : ");
            sc.next(); // this is important!
        }
        id = sc.nextInt();
        sc.nextLine();
        return id;
    }

    static String getSearchStringFromUser() {
        System.out.print("Provide search term : ");
        return sc.nextLine();
    }

    static int getSortOptionFromUser() {
        int sortOption =1;
        System.out.println(MenuHelper.SORT_MENU);

        System.out.print("Enter sort option : ");
        while (!sc.hasNextInt()) {
            System.out.println("That's not a number!");
            System.out.println("Provide album id : ");
            sc.next(); // this is important!
        }
        sortOption = sc.nextInt();
        sc.nextLine();
        return sortOption;
    }
}
