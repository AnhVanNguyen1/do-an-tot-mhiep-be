package response;

import com.shop.shop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private Date createdAt;
    private String userRole;

    public static UserDto fromEntity(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .createdAt(user.getRegisteredAt())
                .userRole(user.getUserRole())
                .build();
    }
}
