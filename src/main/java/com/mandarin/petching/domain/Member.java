package com.mandarin.petching.domain;

import java.time.LocalDate;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn
public class Member {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String userId;
    private String userPwd;
    private String userName;
    private LocalDate userBth;
    @Enumerated(EnumType.STRING)
    private GenderType userGender;
    private String userEmail;
    private String userTel;
    @Enumerated(EnumType.STRING)
    private MemberType memberType;
    private String imageUrl;
    private String chatUrl;

    public Member() {
    }

    public Member(Long id, String userId, String userPwd, String userName, LocalDate userBth, GenderType userGender, String userEmail, String userTel, MemberType memberType, String imageUrl, String chatUrl) {
        this.id = id;
        this.userId = userId;
        this.userPwd = userPwd;
        this.userName = userName;
        this.userBth = userBth;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userTel = userTel;
        this.memberType = memberType;
        this.imageUrl = imageUrl;
        this.chatUrl = chatUrl;
    }

    public static Member.MemberBuilder builder() {
        return new Member.MemberBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserPwd() {
        return this.userPwd;
    }

    public String getUserName() {
        return this.userName;
    }

    public LocalDate getUserBth() {
        return this.userBth;
    }

    public GenderType getUserGender() {
        return this.userGender;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getUserTel() {
        return this.userTel;
    }

    public MemberType getMemberType() {
        return this.memberType;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getChatUrl() {
        return this.chatUrl;
    }

    public static class MemberBuilder {
        private Long id;
        private String userId;
        private String userPwd;
        private String userName;
        private LocalDate userBth;
        private GenderType userGender;
        private String userEmail;
        private String userTel;
        private MemberType memberType;
        private String imageUrl;
        private String chatUrl;

        MemberBuilder() {
        }

        public Member.MemberBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Member.MemberBuilder userId(final String userId) {
            this.userId = userId;
            return this;
        }

        public Member.MemberBuilder userPwd(final String userPwd) {
            this.userPwd = userPwd;
            return this;
        }

        public Member.MemberBuilder userName(final String userName) {
            this.userName = userName;
            return this;
        }

        public Member.MemberBuilder userBth(final LocalDate userBth) {
            this.userBth = userBth;
            return this;
        }

        public Member.MemberBuilder userGender(final GenderType userGender) {
            this.userGender = userGender;
            return this;
        }

        public Member.MemberBuilder userEmail(final String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Member.MemberBuilder userTel(final String userTel) {
            this.userTel = userTel;
            return this;
        }

        public Member.MemberBuilder memberType(final MemberType memberType) {
            this.memberType = memberType;
            return this;
        }

        public Member.MemberBuilder imageUrl(final String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Member.MemberBuilder chatUrl(final String chatUrl) {
            this.chatUrl = chatUrl;
            return this;
        }

        public Member build() {
            return new Member(this.id, this.userId, this.userPwd, this.userName, this.userBth, this.userGender, this.userEmail, this.userTel, this.memberType, this.imageUrl, this.chatUrl);
        }

        public String toString() {
            return "Member.MemberBuilder(id=" + this.id + ", userId=" + this.userId + ", userPwd=" + this.userPwd + ", userName=" + this.userName + ", userBth=" + this.userBth + ", userGender=" + this.userGender + ", userEmail=" + this.userEmail + ", userTel=" + this.userTel + ", memberType=" + this.memberType + ", imageUrl=" + this.imageUrl + ", chatUrl=" + this.chatUrl + ")";
        }
    }
}
