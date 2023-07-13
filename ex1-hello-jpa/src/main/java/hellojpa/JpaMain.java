package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

/**
 * 양방향 매핑 정리
 *
 * 1. 단방향 매핑만으로도 이미 연관관계 매핑은 완료
 *
 * 2. 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
 *
 * 3. JPQL에서 역방향으로 탐색할 일이 많음
 *
 * 4. 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨 (테이블에 영향을 주지 않음) -> 객체 입장에서 양방향 매핑 시 이득이 별로 없음 (연관관계 편의 메소드 생성 등 고민거리만 많아짐)
 *
 * 5. 비즈니스 로직을 기준으로 연관관계의 주인을 선택하기보다(연과관계 편의 메소드 구현 위치로 해결) 외래 키의 위치를 기준으로 정하는 것이 성능이나 운영 등 다양한 관점에서 좋음
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("user1");
            member.setCreatedBy("kim");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

            em.flush();
            em.clear();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
