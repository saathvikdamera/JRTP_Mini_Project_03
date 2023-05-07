package com.Damera.binding;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentForm {

	private Integer blogId;
	private String guestName;
	private String guestEmail;
	private String comment;
}
