package com.insurance.policyapp.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "policies")
@EntityListeners(AuditingEntityListener.class)
public class Policy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "policy_id")
	private int policyId;

	@Column(name = "policy_name")
	private String policyName;

	@ManyToOne
	private Policycategory policycategory;

	@Column(name = "policy_desc")
	private String policydesc;
	
	@Column(name = "premium_amount")
	private double premiumAmount;
	
	@Column(name = "tenure")
	private double tenure;
	
	@Column(name = "sum_assured")
	private double sumAssured;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;
	
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

	public Policy() {

	}
	
	public Policy(int policyId, String policyName, Policycategory policycategory, String policydesc,
			double premiumAmount, double tenure, double sumAssured, Date createdAt, Date updatedAt) {
		super();
		this.policyId = policyId;
		this.policyName = policyName;
		this.policycategory = policycategory;
		this.policydesc = policydesc;
		this.premiumAmount = premiumAmount;
		this.tenure = tenure;
		this.sumAssured = sumAssured;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	
	public Policycategory getPolicycategory() {
		return policycategory;
	}

	public void setPolicycategory(Policycategory policycategory) {
		this.policycategory = policycategory;
	}

	public String getPolicydesc() {
		return policydesc;
	}

	public void setPolicydesc(String policydesc) {
		this.policydesc = policydesc;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public double getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public double getTenure() {
		return tenure;
	}

	public void setTenure(double tenure) {
		this.tenure = tenure;
	}

	public double getSumAssured() {
		return sumAssured;
	}

	public void setSumAssured(double sumAssured) {
		this.sumAssured = sumAssured;
	}

	@Override
	public String toString() {
		return "Policy [policyId=" + policyId + ", policyName=" + policyName + ", policycategory=" + policycategory
				+ ", policydesc=" + policydesc + ", premiumAmount=" + premiumAmount + ", tenure=" + tenure
				+ ", sumAssured=" + sumAssured + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}