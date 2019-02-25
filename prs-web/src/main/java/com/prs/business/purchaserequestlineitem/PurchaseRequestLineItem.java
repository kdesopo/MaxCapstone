package com.prs.business.purchaserequestlineitem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.prs.business.product.Product;
import com.prs.business.purchaserequest.PurchaseRequest;

@Entity
public class PurchaseRequestLineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "purchaserequestid")
	private PurchaseRequest request;
	@ManyToOne
	@JoinColumn(name = "productid")
	private Product product;
	private int quantity;
	
	public PurchaseRequestLineItem() {
		
	}
	
	public PurchaseRequestLineItem(PurchaseRequest request, Product product, int quantity) {
		this.request = request;
		this.product = product;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PurchaseRequest getRequest() {
		return request;
	}

	public void setRequest(PurchaseRequest request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "PurchaseRequestLineItem [id=" + id + ", request=" + request + ", product=" + product + ", quantity="
				+ quantity + "]";
	}
}
