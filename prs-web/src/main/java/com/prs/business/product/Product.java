package com.prs.business.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.prs.business.vendor.Vendor;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="vendorID")
	private Vendor vendor;
	private String partnumber;
	private String name;
	private double price;
	private String unit;
	private String photopath;
	
	public Product() {
		
	}

	public Product(Vendor v, String partnumber, String name, double price, 
			String unit) {
		this.vendor = v;
		this.partnumber = partnumber;
		this.name = name;
		this.price = price;
		this.unit = unit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor v) {
		this.vendor = v;
	}

	public String getPartNumber() {
		return partnumber;
	}

	public void setPartNumber(String partnumber) {
		this.partnumber = partnumber;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPhotoPath() {
		return photopath;
	}

	public void setPhotoPath(String photopath) {
		this.photopath = photopath;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", vendor=" + vendor.getName() + ", partNumber=" + partnumber + ", name=" + name + ", price="
				+ price + ", unit=" + unit + ", photoPath=" + photopath + "]";
	}
}