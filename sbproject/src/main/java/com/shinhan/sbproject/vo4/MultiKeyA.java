package com.shinhan.sbproject.vo4;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultiKeyA implements Serializable {
	private Integer id1;
	private Integer id2;
}