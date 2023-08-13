package com.insurance.policyapp.dto;

public class AddPolicyRequest {
    private String policyName;
    private String policydesc;
    private long categoryId;
    private double sumAssured;
    private double premiumAmount;
    private double tenure;

    public AddPolicyRequest() {
        
    }
    
    public AddPolicyRequest(String policyName, String policydesc, long categoryId, double sumAssured,
			double premiumAmount, double tenure) {
		super();
		this.policyName = policyName;
		this.policydesc = policydesc;
		this.categoryId = categoryId;
		this.sumAssured = sumAssured;
		this.premiumAmount = premiumAmount;
		this.tenure = tenure;
	}

	public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicydesc() {
        return policydesc;
    }

    public void setPolicydesc(String policydesc) {
        this.policydesc = policydesc;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

	public double getSumAssured() {
		return sumAssured;
	}

	public void setSumAssured(double sumAssured) {
		this.sumAssured = sumAssured;
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
    
}
