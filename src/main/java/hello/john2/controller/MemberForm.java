package hello.john2.controller;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
@Getter
@Setter
public class MemberForm {
@NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;
@NotEmpty(message = "지역 이름은 필수 입니다.")
    private String city;
    //@NotEmpty(message = "거리 이름은 필수 입니다.")
    private String street;
    private String zipcode;
}
