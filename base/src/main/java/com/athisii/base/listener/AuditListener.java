package com.athisii.base.listener;

import com.athisii.base.model.AuditModel;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */

public class AuditListener {
    public AuditListener() {
    }

    @PrePersist
    protected void beforePersist(AuditModel abstractDomain) {
        abstractDomain.setCreatedDate(new Date());
        abstractDomain.setModifiedDate(new Date());
    }

    @PreUpdate
    protected void beforeUpdate(AuditModel abstractDomain) {
        abstractDomain.setModifiedDate(new Date());
    }
}