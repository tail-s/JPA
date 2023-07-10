package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sound.midi.Soundbank;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        /**
         * 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
         *
         * 엔티티 매니저는 쓰레드간 공유하지 않는다.
         *
         * JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
         */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");

//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

//            em.remove(findMember);

//            findMember.setName("HelloJPA");
//            em.persist(findMember); 수정 시 이 코드는 필요가 없음

//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
////                    .setFirstResult(1)
////                    .setMaxResults(4)
//                    .getResultList();

//            for (Member member : result) {
//                System.out.println("member.name = " + member.getName());
//            }

//            // 비영속
//            Member member = new Member();
//            member.setId(100L);
//            member.setName("HelloJPA");
//
//            // 영속
//            System.out.println("=== BEFORE ===");
//            em.persist(member); // 이 시점에 쿼리가 실행되지 않음  em.flush(); 를 이용하면 그 즉시 쿼리 실행
////            em.detach(member);
////            em.remove(member);
//            System.out.println("=== AFTER ===");
//
//            Member findMember = em.find(Member.class, 100L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());    // DB를 조회하지 않고 1차 캐시에서 조회 (line 56에 의해)

//            Member f1 = em.find(Member.class, 1L);
//            Member f2 = em.find(Member.class, 1L);  // 쿼리문은 한번만 실행됨 -> 1차 캐시에서 조회

//            System.out.println("동일성 보장 여부 : " + (f1 == f2));

            Member member = new Member();
//            member.setId(3L);
            member.setUsername("C");
            member.setRoleType(RoleType.USER);

            em.persist(member);

            tx.commit();    // 이 시점에 쿼리가 실행됨
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
