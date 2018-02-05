package com.wanhan.shouyu.event;

/**
 * Created by lan on 2017/8/16.
 */

public class NickNameEvent {
    private String nickname;

    public NickNameEvent(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
