package com.shinhan.sbproject.vo;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tbl_depatments")
public class DeptVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//Oracle: sequence(AUTO) 
	//MySQL: auto increment(IDENTITY), AUTO이면 테이블로 만들어짐
	int departmentId;
	
	@Column(length = 50)
	String departmentName;
	int managerId;
	int locationId;
	
	@CreationTimestamp
	Timestamp regDate;
	
	@UpdateTimestamp
	Timestamp updateDate;
}
