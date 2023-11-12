package application;
/*
import model.dao.DaoFactory;
import model.dao.TipoItemDao;
import model.entities.TipoItem;

import java.util.List;
import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        TipoItemDao tipoItemDao = DaoFactory.createTipoItemDao();

        System.out.println("=== TEST 1: findById =======");
        TipoItem tipo = tipoItemDao.findById(1);
        System.out.println(tipo);

        System.out.println("\n=== TEST 2: findAll =======");
        List<TipoItem> list = tipoItemDao.findAll();
        for (TipoItem d : list) {
            System.out.println(d);
        }

        System.out.println("\n=== TEST 3: insert =======");
        TipoItem newTipoItem = new TipoItem(null, "Music");
        tipoItemDao.insert(newTipoItem);
        System.out.println("Inserted! New id: " + newTipoItem.getId());

        System.out.println("\n=== TEST 4: update =======");
        TipoItem tipo2 = tipoItemDao.findById(1);
        tipo2.setTipo("Food");
        tipoItemDao.update(tipo2);
        System.out.println("Update completed");

        System.out.println("\n=== TEST 5: delete =======");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        tipoItemDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();
    }
}
*/



import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.ItemDao;
import model.dao.TipoItemDao;
import model.entities.TipoItem;
import model.entities.Item;

public class Teste {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ItemDao itemDao = DaoFactory.createItemDao();

        System.out.println("=== TEST 1: seller findById =====");
        Item item = itemDao.findById(1);
        System.out.println(item);

        System.out.println("\n=== TEST 2: seller findByDepartment =====");
        TipoItem tipoItem = new TipoItem(1, null);
        List<Item> list = itemDao.findByTipoItem(tipoItem);
        for (Item obj : list) {
            System.out.println(obj);
        }

        System.out.println("\n=== TEST 3: seller findAll =====");
        list = itemDao.findAll();
        for (Item obj : list) {
            System.out.println(obj);
        }

        System.out.println("\n=== TEST 4: seller insert =====");
        Item newItem = new Item(null, "Greg", "Greg", "Greg", "Greg", "Greg", "Greg", "Greg", "Greg",  new Date(), new Date(), new Date(), tipoItem);
        itemDao.insert(newItem);
        System.out.println("Inserted! New id = " + newItem.getId());

        System.out.println("\n=== TEST 5: seller update =====");
        item = itemDao.findById(1);
        item.setDescricao("Martha Waine");
        itemDao.update(item);
        System.out.println("Update completed");

        System.out.println("\n=== TEST 6: seller delete =====");
        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        itemDao.deleteById(id);
        System.out.println("Delete completed");


        sc.close();
    }
}