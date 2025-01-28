package com.Nxer.TwistSpaceTechnology.common.misc.MachineShutDownReasons;

import gregtech.api.util.shutdown.ShutDownReason;
import gregtech.api.util.shutdown.SimpleShutDownReason;

public final class SimpleShutDownReasons {

    // #tr GT5U.gui.text.SimpleShutDownReasons.NoSpaceTimeMaintenanceFluidInput
    // # No Space Time Maintenance Fluid Input
    // #zh_CN 没有时空维护流体输入
    public static final ShutDownReason NoSpaceTimeMaintenanceFluidInput = SimpleShutDownReason
        .ofCritical("SimpleShutDownReasons.NoSpaceTimeMaintenanceFluidInput");

    // #tr GT5U.gui.text.SimpleShutDownReasons.NoCorrectFluidInput
    // # No Correct Fluid Input
    // #zh_CN 没有正确的流体输入
    public static final ShutDownReason NoCorrectFluidInput = SimpleShutDownReason
        .ofCritical("SimpleShutDownReasons.NoCorrectFluidInput");

    // #tr GT5U.gui.text.SimpleShutDownReasons.NoEnoughCriticalPhotonInput
    // # No Enough Critical Photon Input
    // #zh_CN 没有足够临界光子输入
    public static final ShutDownReason NoCriticalPhotonInput = SimpleShutDownReason
        .ofCritical("SimpleShutDownReasons.NoEnoughCriticalPhotonInput");
}
