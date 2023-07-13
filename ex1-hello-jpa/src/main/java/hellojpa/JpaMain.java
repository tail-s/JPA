package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

//            Team team = new Team();
//            team.setName("Team Ace");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("Member No.1");
//            member.setTeam(team);
////            team.addMember(member); 무한루프 조심! 연관관계 편의 메소드는 한 쪽에서만 사용!
//            em.persist(member);
//
////            team.getMembers().add(member);
//            /**
//             * 위 " team.getMembers().add(member); " 코드가 필요한 이유
//             *
//             * 1. flush & clear 시 문제가 없지만, 그렇지 않는 경우 위에서 셋팅한 값들과 연관 관계까 1차 캐시에 다 들어간 상태이기 때문에 team에는 member가 들어가 있지 않게 된다.
//             *
//             * 2. 테스트 케이스 작성 시 JPA 없이도 작동 가능하도록 작성하기 때문에 양 쪽에 값 세팅을 해주는 것이 좋다.
//             *
//             * 제안 : 위 코드 대신 Member 클래스의 setTeam에 로직을 추가하여 사용 (연관관계 편의 메소드) 로직 변경 시 이름 바꿔주는 것도 좋다. ex) setTeam -> changeTeam
//             *
//             * 물론 다른 쪽(Team)에서 구현할 수 있다. 다만 한 쪽에서만 구현하는 것이 좋다.
//             *
//             * 추가 : 양방향 매핑 시 무한 루프를 조심하자.
//             * ex ) toString(), lombok, JSON 생성 라이브러리 (Controller에서 Entity를 직접 Response로 보내버리는 경우 JSON으로 바뀔 때 무한 호출이 됨)
//             *
//             * lombok에서 toString생성은 사용하지 않기 (쓰더라도 빼고 쓰자)
//             * Controller에서 Entity를 절대 반환하지 않기 ( 1. 무한루프의 위험성 , 2. Entity는 변경될 수 있으며 그 때 API 스펙이 바뀌어 버림 ) -> DTO로 변환하여 반환 권장
//             */
//
//
////            em.flush();
////            em.clear();
//
//            Team findTeam = em.find(Team.class, team.getId());  // 1차 캐시에서 호출, team은 순수한 객체 상태
//            List<Member> members = findTeam.getMembers();
//
//            System.out.println("==========");
//
//            for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//            }

            Movie movie = new Movie();
            movie.setDirector("A");
            movie.setActor("B");
            movie.setName("바람과 어쩌꾸");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
