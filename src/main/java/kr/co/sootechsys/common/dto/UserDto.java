package kr.co.sootechsys.common.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.sootechsys.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

   @NotNull
   @Size(min = 3, max = 50)
   private String username;

   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   @NotNull
   @Size(min = 3, max = 100)
   private String password;

   @NotNull
   @Size(min = 3, max = 50)
   private String nickname;

   private Set<AuthDto> authDtoSet;

   public static UserDto from(User user) {
      if(user == null) return null;

      return UserDto.builder()
              .username(user.getUsername())
              .nickname(user.getNickname())
              .authDtoSet(user.getAuths().stream()
                      .map(auth -> AuthDto.builder().authName(auth.getAuthName()).build())
                      .collect(Collectors.toSet()))
              .build();
   }
}