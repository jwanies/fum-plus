package fum.data.objects;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class FoodUnit {

	@Id 
	@GeneratedValue
    private Long id;
	
	@NotBlank
	@Column(nullable = false)
    private String owner;
	
	@NotNull
    @ManyToOne (optional = false)
    @JoinColumn(name="productTypeId", nullable = false)
    private ProductType productType;
	
	@NotBlank
    @Column(nullable = false)
	private String unitDescription;
	
	@PastOrPresent
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@PositiveOrZero
    @Column(nullable = true)
    private Long mass;
	
    @FutureOrPresent
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
    
    @ElementCollection
    @MapKeyColumn(name="attribute")
    @CollectionTable(name = "food_unit_additional_attributes")
    @Column(name = "value", nullable = true)
    Map<String, String> typeSpecificAttributes = new HashMap<String, String>();
        
    public Map<String, String> getTypeSpecificAttributes() {
		return typeSpecificAttributes;
	}

	public void setTypeSpecificAttributes(Map<String, String> typeSpecificAttributes) {
		this.typeSpecificAttributes = typeSpecificAttributes;
	}

	public String getUnitDescription() {
		return unitDescription;
	}

	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getMass() {
		return mass;
	}

	public void setMass(Long mass) {
		this.mass = mass;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
    
    @PrePersist
    public void prePersist() {
    	if (this.createdDate == null) {
    		this.setCreatedDate(new Date());
    	}
    }
}
