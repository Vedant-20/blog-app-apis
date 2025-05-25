package com.vedant.blog.payloads;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {


	private int id;
	
	@NotEmpty
	private String name;
	
	@Email(message = "Email address is not valid ! ! ")
	private String email;
	
	@NotEmpty
	@Size(max = 15, min = 3, message = "Password must be min 3 chars and max 15 chars")
	private String password;
	
	@NotEmpty
	private String about;
	
}
