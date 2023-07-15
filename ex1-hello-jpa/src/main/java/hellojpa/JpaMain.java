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

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
//            em.persist(child1);   cascade 설정 시 이 코드들은 자동으로 실행됨. 보통 child마다 parent가 하나일 때 (소유자가 하나인 경우) cascade 사용. cascade는 mapping과 별개
//            em.persist(child2);

            em.flush();
            em.clear();

            // orphanRemoval은 주의해서 사용. 특정 엔티티가 개인 소유할 때 사용
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);



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
