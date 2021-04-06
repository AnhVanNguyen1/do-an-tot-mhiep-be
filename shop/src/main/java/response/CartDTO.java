package response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lê Thị Thúy
 * @created 3/13/2021
 * @project shop
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Integer cartId;

    private String firstName;

    private String lastName;

    private String mobile;

    private String email;

    private Integer userId;

    private Integer productId;


}
