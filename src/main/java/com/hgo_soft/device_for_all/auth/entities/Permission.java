package com.hgo_soft.device_for_all.auth.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();



    /*private String description*/;

   /* @ManyToMany(mappedBy = "permissions")
    private Set<User> users;*/


}
