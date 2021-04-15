package chess.dto;

import java.util.Objects;

public class UserDto {
    private String name;
    private String password;

    public UserDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDto userDto = (UserDto)o;
        return name.equals(userDto.name) && password.equals(userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
