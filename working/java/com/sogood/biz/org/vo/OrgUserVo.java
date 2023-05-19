package com.sogood.biz.org.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgUserVo {
  /** the user's ID */
  protected String userId; 
  /** Password */
  protected String password; 
  /** The user's name */
  protected String name; 
  /** The user's email */
  protected String email;
}