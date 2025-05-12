package models.lombok;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterBodyModel {

    String email, password;

}
