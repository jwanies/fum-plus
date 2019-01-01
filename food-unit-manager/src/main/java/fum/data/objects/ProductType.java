package fum.data.objects;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProductType {

	@Id 
	@GeneratedValue
	@JsonIgnore
    private Long id;
	
	@NotBlank
	@Column(nullable = false, unique = true)
	private String typeName;
	
	@ElementCollection
    @CollectionTable(name = "product_type_attributes")
    @Column(name = "attribute", nullable = true)
    Set<String> attributes = new HashSet<String>();

    public Set<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<String> attributes) {
		this.attributes = attributes;
	}

	public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
