package com.insurance.policyapp.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
@Table(name = "policy_categories")
@EntityListeners(AuditingEntityListener.class)
public class Policycategory {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "category_id")
		private long categoryId;

		private String name;
		
		@CreatedDate
		@Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "created_at", nullable = false, updatable = false)
		private Date createdAt;
		
		@LastModifiedDate
		@Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "updated_at", nullable = false)
	    private Date updatedAt;
		
		public Policycategory() {
			super();
		}

		public Policycategory(long categoryId, String name, Date createdAt, Date updatedAt) {
			super();
			this.categoryId = categoryId;
			this.name = name;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}

		public long getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(long categoryId) {
			this.categoryId = categoryId;
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
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Policycategory [categoryId=" + categoryId + ", name=" + name + ", createdAt=" + createdAt
					+ ", updatedAt=" + updatedAt + "]";
		}
		
	}

