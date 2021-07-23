package dev.hypera.chameleon.core.objects.users;

import dev.hypera.chameleon.core.objects.users.permissions.PermissionHolder;
import net.kyori.adventure.audience.Audience;

/**
 * Something that can send messages, receive messages and have permissions.
 */
public interface ChatUser extends Audience, PermissionHolder {
}
