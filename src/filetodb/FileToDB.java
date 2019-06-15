/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filetodb;



import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class FileToDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       FileToDB t=new FileToDB();
       StringTokenizer tokenizer=new StringTokenizer("11;30.23;SEK",";");//test
          while (tokenizer.hasMoreTokens()) {
          String s1=tokenizer.nextToken();   
          String s2=tokenizer.nextToken(); 
          String s3=tokenizer.nextToken(); 
          try{
          long id=Long.parseLong(s1);
         double amount=Double.parseDouble(s2);
         Money money=new Money(id,amount,s3);
         t.persist(money);
          
          }
          catch(NumberFormatException e)
          {
              e.printStackTrace();}
        // System.out.println(tokenizer.nextToken());
     }
       //Money money=new Money(2l,33,"SE");//test
       /*
          Query q = em.createQuery("select m from Money m");
        int size=q.getResultList().size();
        if(size<1)
       */
       //t.persist(money);
    }
    
        public void persist(Money money) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("FileToDBPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
       
        
        try {
            em.persist(money);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        
    }
    
}

