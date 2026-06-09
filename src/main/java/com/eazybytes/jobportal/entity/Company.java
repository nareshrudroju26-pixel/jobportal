package com.eazybytes.jobportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
//@Getter @Setter
/*@NamedQueries({
        @NamedQuery(name = "Company.findAllWithJobsByStatusNamed",
                query = "SELECT DISTINCT c FROM Company c JOIN FETCH c.jobs j WHERE j.status=:status")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Company.findAllWithJobsByStatusNativeNamed",
                query =  "SELECT DISTINCT c.* FROM companies c JOIN  jobs j ON c.id=j.company_id WHERE j.status=?",
                resultClass = Company.class)
})*/
@NamedQueries({
        @NamedQuery(name = "Company.fetchCompaniesWithJobsByStatus", query =
                "SELECT DISTINCT c FROM Company c JOIN FETCH c.jobs j WHERE j.status = :status"),
        @NamedQuery(name = "Company.updateCompanyDetails",
                query =
                        """
                                UPDATE Company c SET
                                                            c.name = :name,
                                                            c.logo = :logo,
                                                            c.industry = :industry,
                                                            c.size = :size,
                                                            c.rating = :rating,
                                                            c.locations = :locations,
                                                            c.founded = :founded,
                                                            c.description = :description,
                                                            c.employees = :employees,
                                                            c.website = :website
                                                        WHERE c.id = :id
                        """
        )})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Company.fetchCompaniesWithJobsByStatusNative",
                query = "SELECT DISTINCT c.* FROM companies c JOIN jobs j ON c.id = j.company_id WHERE j.status = ?",
                resultClass = Company.class)
})
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",nullable = false)
    private Long id;

    @Column(name = "NAME",nullable = false,unique = true)
    private String name;

    @Column(name = "LOGO", length = 500)
    private String logo;

    @Column(name = "INDUSTRY", nullable = false, length = 100)
    private String industry;

    @Column(name = "SIZE", nullable = false, length = 50)
    private String size;

    @Column(name = "RATING", nullable = false, precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "LOCATIONS", length = 1000)
    private String locations;

    @Column(name = "FOUNDED", nullable = false)
    private Integer founded;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EMPLOYEES")
    private Integer employees;

    @Column(name = "WEBSITE", length = 500)
    private String website;

    /*
    For @ManyToOne/@OneToOne → Hibernate always uses a JOIN in the main query.
    For @OneToMany  → Hibernate's default strategy is not to JOIN by default.
    Instead, it often uses a secondary select (one query per collection) unless configured otherwise.
   */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10) // Adjust the batch size as needed
   // @SQLRestriction("status = 'ACTIVE'") // Filter to include only active jobs
    private List<Job> jobs = new ArrayList<>();

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public Integer getFounded() {
        return founded;
    }

    public void setFounded(Integer founded) {
        this.founded = founded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
