package com.damera.binding;

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
