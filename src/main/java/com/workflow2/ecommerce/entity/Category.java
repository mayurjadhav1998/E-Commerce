
package com.workflow2.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * This is the Entity class for Category table
 * @author Mayur_Jadhav & tejas_badjate
 * @version v0.0.1
 */
@Entity
@Table(name="product_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
	@Id
	@Column(name = "category_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID categoryId;

	@Column(name = "category_name", nullable = false,unique = true)
	private String categoryName;

	@Column(name = "category_image", unique = false, nullable = true, length = 16777215)
	private byte[] image;
	
}
