package com.example.cs4500_sp19_noideainc.utils;

import java.util.Comparator;

public class UserComparator implements Comparator<UserToInt> {


  @Override
  public int compare(UserToInt o1, UserToInt o2) {
     if (o1.getRank() > o2.getRank()) {
       return -1;
     }
     else if (o1.getRank() == o2.getRank()) {
       return 0;
     }
     else {
       return 1;
     }
  }
}
