package com.yedam;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ydbrd {
	private String id;
	private String pw;
	private int brdnum;
	private int commentnum;
	private String content;
	private String title;
	private Date date;
	private String comment;
	private int count;
	private int like;
	private String email;
}
