package it.sensorplatform.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String name;
	
	@NotNull
	@Column(nullable = true)
	private Double latitude;
	
	@NotNull
	@Column(nullable = true)
	private Double longitude;
	
	@Column(nullable = true, unique = true)
	private String devEui;
	
	@NotBlank
	@Column(unique = true, nullable = false)
	private String macAddress;
	
	@NotBlank
	@Column(nullable = true)
	private String emailOwner;
	
	@ManyToOne
	private Project project;
	
	@OneToMany
	private List <MeasurementRecord> records;
	
	@ManyToOne
	private Group group;
	
	@ManyToOne
	private TypeOfDevice tod;
	
	public Long getId() {
		return id;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Double getLatitude() {
		return latitude;
	}
	
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	
	public Double getLongitude() {
		return longitude;
	}
	
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
	public String getDevEui() {
		return devEui;
	}
	
	
	public void setDevEui(String devEui) {
		this.devEui = devEui;
	}


	public String getMacAddress() {
		return macAddress;
	}


	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	
	public List<MeasurementRecord> getRecords() {
		return records;
	}


	public void setRecords(List<MeasurementRecord> records) {
		this.records = records;
	}


	public String getEmailOwner() {
		return emailOwner;
	}

	
	public void setEmailOwner(String emailOwner) {
		this.emailOwner = emailOwner;
	}


	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}
	
	
	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}
	
	

	public TypeOfDevice getTod() {
		return tod;
	}


	public void setTod(TypeOfDevice tod) {
		this.tod = tod;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, macAddress);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		return Objects.equals(id, other.id) && Objects.equals(macAddress, other.macAddress);
	}
	
	
}
