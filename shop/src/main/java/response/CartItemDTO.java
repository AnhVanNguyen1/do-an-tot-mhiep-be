package response;

import com.shop.shop.entity.Cart;
import com.shop.shop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lê Thị Thúy
 * @created 3/16/2021
 * @project shop
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private Cart cart;
    private Product product;
    private Integer id;
    private String photo;
    private Integer quantity;
}
