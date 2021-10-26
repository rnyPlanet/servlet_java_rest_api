package dao.models;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class User {
    String id;
    String login;
    String password;
}
