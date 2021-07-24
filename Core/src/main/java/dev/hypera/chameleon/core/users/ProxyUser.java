package dev.hypera.chameleon.core.users;

/**
 * A client-side user in the scope of a proxy.
 */
public interface ProxyUser extends User {

    void sendToServer(String server);

}
