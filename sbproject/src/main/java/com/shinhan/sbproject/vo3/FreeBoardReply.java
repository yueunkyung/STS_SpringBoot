package com.shinhan.sbproject.vo3;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "board2")
@Builder
@Entity
@Table(name="tbl_free_replies")
public class FreeBoardReply { //PPT 80p
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	private String reply;
	private String replyer;

	@CreationTimestamp
	private Timestamp regdate;
	
	@UpdateTimestamp
	private Timestamp updatedate;

	@JsonIgnore	// 무한 loop되지 않도록 FreeBoard -> FreeBoardReply -> 다시 FreeBoard로 가는 것을 막아야한다.
				// 자바 객체가 browser로 내려갈때 JSON data로 변경되어 내려간다. //com.fasterxml.jackson.databind 오류 때문에 추가
	@ManyToOne //FK: board_bno
	FreeBoard board2;
	
}
