package com.wherecar.rest.domain;

import jakarta.persistence.*;


@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;

    //TODO:Authority 관련된거 추가할지 뺄지 정하기



}
