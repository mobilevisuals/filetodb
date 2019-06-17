package filetodb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class FileToDB {

    public static void main(String[] args) {
        FileToDB t = new FileToDB();
        t.persist();
    }

    private void persist() {
       
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("FileToDBPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
         File file = new File("src/filetodb/test.csv");
        Query q = em.createQuery("select m from Money m");
        int size = q.getResultList().size();
        if (size > 0) {
            return;
        }

        try {

            parseAndSaveRows(em, file);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            file.delete();
            em.close();
        }

    }

    private void parseAndSaveRows(EntityManager em, File file) {
        String textToRead = null;
        int counter = 0;

        try (BufferedReader instream = new BufferedReader(new FileReader(
                file))) {
            while ((textToRead = instream.readLine()) != null) {

                if (counter > 0) {

                    StringTokenizer tokenizer = new StringTokenizer(textToRead, ";");
                    long id = 0;
                    double amount = 0;
                    while (tokenizer.hasMoreTokens()) {
                        String s1 = tokenizer.nextToken();
                        String s2 = tokenizer.nextToken();
                        String s3 = tokenizer.nextToken();
                        try {
                            id = Long.parseLong(s1);
                            amount = Double.parseDouble(s2);

                        } catch (NumberFormatException e) {//not catch ??
                            e.printStackTrace();
                        }

                        Money money = new Money(id, amount, s3);
                        em.persist(money);

                    }

                }
                counter++;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();//rollback anrop??
        } catch (IOException e1) {
            e1.printStackTrace();//rollback anrop??
        }
    }

}
