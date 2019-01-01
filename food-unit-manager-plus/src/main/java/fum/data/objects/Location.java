package fum.data.objects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
public class Location {

	@Id
	@GeneratedValue
    private Long id;
	
	@Column(nullable = false) 
	private Double longitude;
	
	@Column(nullable = false) 
    private Double latitude;
	
	@PastOrPresent
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@NotNull
	@ManyToOne(optional = false)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "foodUnitId", nullable = false)
	private FoodUnit foodUnit;
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public FoodUnit getFoodUnit() {
		return foodUnit;
	}

	public void setFoodUnitId(FoodUnit foodUnit) {
		this.foodUnit = foodUnit;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@PrePersist
    public void prePersist() {
    	if (this.createdDate == null) {
    		this.setCreatedDate(new Date());
    	}
    }
}
