package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 프록시와 즉시로딩 주의
 *
 * 1. 가급적 지연 로딩만 사용 (특히 실무에서)
 *
 * 2. 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
 *
 * 3. 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
 *
 * 4. @ManyToOne, @OneToOne은 기본이 즉시 로딩 -> LAZY로 설정
 *
 * 5. @OneToMany, @ManyToMany는 기본이 지연로딩
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

//            // JPQL
//            List<Member> result = em.createQuery("select m from Member as m where m.username like '%kim'").getResultList();
//
//            for (Member m : result) {
//                System.out.println("member = " + m);
//            }



//            // Criteria -> 동적으로 작성 가능하나 유지보수가 힘들다 (복잡하고 실용성이 없다.)
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//
//            Root<Member> m = query.from(Member.class);
//
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
//            List<Member> resultList = em.createQuery(cq).getResultList();



            // Q

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }

    private static void printMember(Member member) {
        System.out.println("member = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}
