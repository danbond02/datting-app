package edu.kpi.iasa.mmsa.blockaccountservice.repo.model;

import javax.persistence.*;

@Entity
@Table(name="blocked_accounts")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name="blocked_user_id")
    private Long blockedUserId;

    private Block() {}

    public Block(Long userId, Long blockedUserId) {
        this.userId = userId;
        this.blockedUserId = blockedUserId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBlockedUserId() {
        return blockedUserId;
    }

    public void setBlockedUserId(Long blockedUserId) {
        this.blockedUserId = blockedUserId;
    }
}
