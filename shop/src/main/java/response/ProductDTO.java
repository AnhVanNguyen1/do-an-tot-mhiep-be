package response;

import com.shop.shop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;

    private String name;

    private String metaTitle;

    private String summary;

    private Integer type;

    private Double price;

    private Double discount;

    private Integer quantity;

    private Integer shop;

    private Date publishedAt;

    private Date startsAt;

    private Date endsAt;

    private String content;

    private String photo;

    private String giaGiam;

    private Category category;
}
