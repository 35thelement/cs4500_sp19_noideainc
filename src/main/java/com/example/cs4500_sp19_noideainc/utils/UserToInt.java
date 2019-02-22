package com.example.cs4500_sp19_noideainc.utils;

import com.example.cs4500_sp19_noideainc.models.User;

public class UserToInt {

  private User user;
  private Integer rank;

  public UserToInt(User u, Integer i) {

    this.user = u;
    this.rank = i;
  }

  public User getTheUser() {
    return user;
  }

  public void setTheUser(User user) {
    this.user = user;
  }

  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }
}
