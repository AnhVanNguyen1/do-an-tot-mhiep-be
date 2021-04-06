package response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private  Integer id;
    private String firstName;

    private String lastName;

    private String mobile;

    private String email;

    private String photo;

    private MultipartFile file;

    private String userRole;
}
