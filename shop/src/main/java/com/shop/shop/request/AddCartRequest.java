package com.shop.shop.request;

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
public class AddCartRequest {
 private Integer productId;
}
