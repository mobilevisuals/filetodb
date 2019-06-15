package filetodb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class FileToDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileToDB t = new FileToDB();
        t.readFromFile();
        t.persist();
    }
    
    private void readFromFile()
    {
        String fileName = "src/filetodb/test.csv";
        String textToRead = null;
        int counter=0;

		try (BufferedReader instream = new BufferedReader(new FileReader(
				fileName))) {
			while ((textToRead = instream.readLine()) != null) {
                            
                            if(counter>0)
			System.out.println(textToRead);
                            counter++;
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
    }

    private void persist() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("FileToDBPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("select m from Money m");
        int size=q.getResultList().size();
        if(size>0)
            return;
try {
        StringTokenizer tokenizer = new StringTokenizer("12;30.23;SEK", ";");//test
        long id = 0;
        double amount = 0;
        while (tokenizer.hasMoreTokens()) {
            String s1 = tokenizer.nextToken();
            String s2 = tokenizer.nextToken();
            String s3 = tokenizer.nextToken();
            try {
                id = Long.parseLong(s1);
                amount = Double.parseDouble(s2);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            Money money = new Money(id, amount, s3);
            em.persist(money);

        }

        
            
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }

}
