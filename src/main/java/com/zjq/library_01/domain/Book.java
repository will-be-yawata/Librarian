package com.zjq.library_01.domain;

import javax.persistence.*;

@Entity(name="book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String ISBN;
	private String name;
	private double price;
	private String author;
	private String published;
	private String img;
	@OneToOne
	@JoinColumn(name = "category_id",referencedColumnName="id")
	private Category category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", ISBN='" + ISBN + '\'' +
				", name='" + name + '\'' +
				", price=" + price +
				", author='" + author + '\'' +
				", published='" + published + '\'' +
				", img='" + img + '\'' +
				", category=" + category +
				'}';
	}
}
