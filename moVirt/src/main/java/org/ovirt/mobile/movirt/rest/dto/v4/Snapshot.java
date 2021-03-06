package org.ovirt.mobile.movirt.rest.dto.v4;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by suomiy on 11/25/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Snapshot extends org.ovirt.mobile.movirt.rest.dto.Snapshot {
    public Vm vm;
    public String snapshot_type;

    public Snapshot() {
    }

    public Snapshot(String description, boolean persistMemoryState) {
        super(description, persistMemoryState);
    }

    public org.ovirt.mobile.movirt.model.Snapshot toEntity() {
        org.ovirt.mobile.movirt.model.Snapshot snapshot = super.toEntity();
        snapshot.setType(super.getSnapshotType(snapshot_type));

        if (vm != null) {
            snapshot.setVm(vm.toEntity());
        }

        return snapshot;
    }
}
