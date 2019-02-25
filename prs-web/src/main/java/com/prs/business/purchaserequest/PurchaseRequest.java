package com.prs.business.purchaserequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.prs.business.user.User;


@Entity
public class PurchaseRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "UserID")
	private User user;
	private String description;
	private String justification;
	private LocalDate dateneeded;
	private String deliverymode;
	private String status;
	private double total;
	private LocalDateTime submitteddate;
	private String reasonforrejection;
	public static final String NEW = "New";
	public static final String SUBMITTED = "Submitted";
	public static final String APPROVED = "Approved";
	public static final String REJECTED = "Rejected";
	
	
	public PurchaseRequest() {
		id = 0;
		user = null;
		description = "";
		justification = "";
		dateneeded = null;
		deliverymode = "";
		status = NEW;
		total = 0.0;
		submitteddate = LocalDateTime.now();
	}

	public PurchaseRequest(int id, User user, String description, String justification, LocalDate dateneeded,
			String deliverymode, String status, double total, LocalDateTime submitteddate, String reasonforrejection) {
		this.id = id;
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateneeded = dateneeded;
		this.deliverymode = deliverymode;
		this.status = status;
		this.total = total;
		this.submitteddate = submitteddate;
		this.reasonforrejection = reasonforrejection;
	}
	
	public PurchaseRequest(User user, String description, String justification, LocalDate dateneeded,
			String deliverymode, double total) {
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateneeded = dateneeded;
		this.deliverymode = deliverymode;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public LocalDate getDateneeded() {
		return dateneeded;
	}

	public void setDateneeded(LocalDate dateneeded) {
		this.dateneeded = dateneeded;
	}

	public String getDeliverymode() {
		return deliverymode;
	}

	public void setDeliverymode(String deliverymode) {
		this.deliverymode = deliverymode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDateTime getSubmitteddate() {
		return submitteddate;
	}

	public void setSubmitteddate(LocalDateTime submitteddate) {
		this.submitteddate = submitteddate;
	}

	public String getReasonforrejection() {
		return reasonforrejection;
	}

	public void setReasonforrejection(String reasonforrejection) {
		this.reasonforrejection = reasonforrejection;
	}
}