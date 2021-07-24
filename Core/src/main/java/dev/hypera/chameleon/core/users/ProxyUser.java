package dev.hypera.chameleon.core.users;

import org.jetbrains.annotations.NotNull;

/**
 * A client-side user in the scope of a proxy.
 */
public interface ProxyUser extends User {

    void sendToServer(@NotNull String server);

}
