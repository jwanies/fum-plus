package fum.data.objects;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="productTypeId", nullable=true)
    Set<Attributes> attributes = new HashSet<Attributes>();

    public Set<Attributes> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attributes> attributes) {
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
