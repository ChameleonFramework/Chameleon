package dev.hypera.chameleon.core.users.permissions;

import org.jetbrains.annotations.NotNull;

public interface PermissionHolder {

    boolean hasPermission(@NotNull String permission);

}
