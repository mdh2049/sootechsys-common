package kr.co.sootechsys.common.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auth")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auth {

   @Id
   @Column(name = "auth_name", length = 50)
   private String authName;
}
