package dev.hypera.chameleon.core.users;

import dev.hypera.chameleon.core.users.permissions.PermissionHolder;
import net.kyori.adventure.audience.Audience;

/**
 * Something that can send messages, receive messages and have permissions.
 */
public interface ChatUser extends Audience, PermissionHolder {

}
