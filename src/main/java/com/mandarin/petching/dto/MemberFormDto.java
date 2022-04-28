package com.mandarin.petching.dto;

import com.mandarin.petching.domain.GenderType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class MemberFormDto {

//    @NotBlank(message = "아이디는 필수 입력 값입니다.")
//    @Valid
//    private String userId;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String userPwd;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String userName;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String userEmail;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;

    @NotEmpty(message = "생년월일 8자리를 입력해주세요.")
    private String userBth;

    @NotEmpty(message = "전화번호를 입력해주세요.")
    @Length(max=13)
    private String userTel;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "성별을 체크해주세요")
    private GenderType userGender;

}