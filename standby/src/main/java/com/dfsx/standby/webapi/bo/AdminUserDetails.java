package com.dfsx.standby.webapi.bo;

import com.dfsx.standby.webapi.model.UserModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author yangcheng
 * @ClassName:
 * @Description:
 * @date 2019年10月07日 16:42
 */
@Accessors(prefix = "_")
@Data
public class AdminUserDetails implements UserDetails {
    private UserModel _userModel;

    public AdminUserDetails(UserModel userModel) {
        this._userModel = userModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return _userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return _userModel.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
