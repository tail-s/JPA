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

            Address address = new Address("city", "street", "10000");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            em.persist(member);

            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setHomeAddress(copyAddress); // member2에 영향을 끼치지 않기 위해 복사본(copyAddress)를 set한다.
            em.persist(member2);


//            member.getHomeAddress().setCity("newCity"); Address 객체의 set을 구현하지 않으면 (혹은 private으로 구현하면) 불변객체로 활용. 값을 바꿀 때에는 Address를 새로 만들어서 member에 다시 지정해야 함.

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
