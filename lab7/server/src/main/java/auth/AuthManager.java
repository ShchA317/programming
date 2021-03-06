package auth;

import user.Auth;

import java.util.Collection;

public interface AuthManager {
    void setUsers(Collection<Auth> users);
    boolean checkAuth(Auth auth);
    void addOnlineUser(Auth auth);
    void removeOnlineUser(Auth auth);
    boolean isOnline(Auth auth);
    void addUser(Auth auth);
}
