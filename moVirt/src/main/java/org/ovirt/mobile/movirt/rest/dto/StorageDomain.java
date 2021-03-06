package org.ovirt.mobile.movirt.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.ovirt.mobile.movirt.rest.RestEntityWrapper;
import org.ovirt.mobile.movirt.util.ObjectUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class StorageDomain implements RestEntityWrapper<org.ovirt.mobile.movirt.model.StorageDomain> {
    public String id;
    public String name;
    public String type;
    public String available;
    public String used;
    public Storage storage;
    public String storage_format;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Storage {
        public String address;
        public String type;
        public String path;
    }

    @Override
    public org.ovirt.mobile.movirt.model.StorageDomain toEntity() {
        org.ovirt.mobile.movirt.model.StorageDomain storageDomain = new org.ovirt.mobile.movirt.model.StorageDomain();
        storageDomain.setId(id);
        storageDomain.setName(name);
        storageDomain.setStorageFormat(storage_format);

        if (storage != null) {
            if (storage.address != null) {
                storageDomain.setStorageAddress(storage.address);
            }
            if (storage.type != null) {
                storageDomain.setStorageType(storage.type);
            }
            if (storage.path != null) {
                storageDomain.setStoragePath(storage.path);
            }
        }

        storageDomain.setType(mapType(type));
        storageDomain.setAvailableSize(ObjectUtils.parseLong(available));
        storageDomain.setUsedSize(ObjectUtils.parseLong(used));

        return storageDomain;
    }

    protected org.ovirt.mobile.movirt.model.StorageDomain.Status mapStatus(String status) {
        try {
            if (status == null) { // sometimes behaves as unknown when null
                return org.ovirt.mobile.movirt.model.StorageDomain.Status.ACTIVE;
            }

            return org.ovirt.mobile.movirt.model.StorageDomain.Status.valueOf(status.toUpperCase());
        } catch (Exception e) {
            return org.ovirt.mobile.movirt.model.StorageDomain.Status.UNKNOWN;
        }
    }

    private static org.ovirt.mobile.movirt.model.StorageDomain.Type mapType(String type) {
        try {
            return org.ovirt.mobile.movirt.model.StorageDomain.Type.valueOf(type.toUpperCase());
        } catch (Exception e) {
            // guess it is this...
            return org.ovirt.mobile.movirt.model.StorageDomain.Type.DATA;
        }
    }
}
