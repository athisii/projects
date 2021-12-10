package com.athisii.base.model;

import com.athisii.base.listener.AuditListener;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */
@MappedSuperclass
@EntityListeners({AuditListener.class})
public class AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean active = true;
    @JsonIgnore
    private boolean deleted = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public AuditModel(Long id, boolean active, boolean deleted, Date createdDate, Date modifiedDate) {
        this.id = id;
        this.active = active;
        this.deleted = deleted;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public AuditModel() {
    }

    public Long getId() {
        return this.id;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonIgnore
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a")
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a")
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof final AuditModel other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        return Objects.equals(this$id, other$id);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AuditModel;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    public String toString() {
        return "AuditModel(id=" + this.getId() + ")";
    }
}
