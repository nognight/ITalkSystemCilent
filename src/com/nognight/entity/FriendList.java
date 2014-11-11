package com.nognight.entity;

public class FriendList {
	private String friendname;
	private String friendIsOnline;

	public String getFriendname() {
		return friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}

	public String getFriendIsOnline() {
		return friendIsOnline;
	}

	public void setFriendIsOnline(String friendIsOnline) {
		this.friendIsOnline = friendIsOnline;
	}

	@Override
	public String toString() {
		return friendname + "--" + friendIsOnline;
	}
}
