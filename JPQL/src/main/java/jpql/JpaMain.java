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

            em.flush();
            em.clear();

            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            Query query3 = em.createQuery("select m.username, m.age from Member m");
//            List resultList = query3.getResultList();
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//            System.out.println("username = " + result[0]);
//            System.out.println("age = " + result[1]);

//            List<Member> resultList = query1.getResultList();   // resultList(리스트 내의 Member들)는 영속성 컨텍스트에서 관리됨

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m where m.id = 10", Member.class);

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m where m.username = :username", Member.class);
//            query1.setParameter("username", "member1");
//            Member singleResult = query1.getSingleResult(); // 결과가 없거나 둘 이상일 때 예외 발생

//            Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                            .setParameter("username", "member1")
//                            .getSingleResult();
//
//
//            System.out.println("singleResult = " + singleResult);

            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO + " + memberDTO.toString());




//            List<Team> result = em.createQuery("select m.team from Member m", Team.class).getResultList();
//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();
//            List<Address> result = em.createQuery("select o.address from Order o", Address.class).getResultList();





            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
