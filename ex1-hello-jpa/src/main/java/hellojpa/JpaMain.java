package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team teamB = new Team();
            team.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member1.getId());
//            System.out.println("m = " + m.getTeam().getClass());

            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();

            // SQL : select * from Member
            // SQL : select * from Team where TEAM_ID = xxx...



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
