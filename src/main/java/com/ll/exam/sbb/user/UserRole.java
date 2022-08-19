package com.ll.exam.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
    // enum 은 안정성을 확보할 때 사용한다.
    // 즉 나이대별로 선택지를 처리해줄 때
    // 20대, 30대 같은 정해져 있는 것을 사용자가 직접 입력하기 보다는
    // 코드가 정해준다고 생각하면 된다.
    // private static UserRole ADMIN = new UserRole("ROLE_ADMIN"); == ADMIN("ROLE_ADMIN);
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
