package com.vedant.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;

	@Column(name = "title", nullable = false)
	private String postTitle;

	@Column(length = 10000)
	private String content;

	@Lob
	@Column(columnDefinition = "LONGTEXT")
	private String image;

	private Date addedDate;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();

}
