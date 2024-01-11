package com.shinhan.sbproject.vo4;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_child1")
@IdClass(MultiKeyA.class)
public class MultiKeyAUsing {
	@Id
	private Integer id1;
	@Id
	private Integer id2;
	private String userName;
	private String address;
}
