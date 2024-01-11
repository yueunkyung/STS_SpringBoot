package com.shinhan.sbproject.vo4;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable //복합키로 사용이 가능
public class MultiKeyB implements Serializable {
	private Integer id1;
	private Integer id2;
}
