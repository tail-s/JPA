package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m where m.id = 10", Member.class);

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m where m.username = :username", Member.class);
//            query1.setParameter("username", "member1");
//            Member singleResult = query1.getSingleResult(); // 결과가 없거나 둘 이상일 때 예외 발생

            Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
                            .setParameter("username", "member1")
                            .getSingleResult();


            System.out.println("singleResult = " + singleResult);

//            List<Member> resultList = query1.getResultList();
//            for (Member m : resultList) {
//                System.out.println("member = " + m);
//            }

            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.username, m.age from Member m");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
