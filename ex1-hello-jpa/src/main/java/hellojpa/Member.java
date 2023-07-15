package hellojpa;

import javax.persistence.*;

@Entity
public class Member {
//public class Member extends BaseEntity {


    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
//    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="city", column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name="street", column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name="zipcode", column = @Column(name = "WORK_ZIPCODE")),
    })
    private Address workAddress;



    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;

        team.getMembers().add(this);

        // 기존 팀에서 빼내는 로직, 바뀌는 팀에 내가 없는지 확인하는 로직 등... 구현해야 할 내용이 많다.
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team +  // team.toString()을 호출하게 됨 (무한 루프의 시작)
                '}';
    }
}
