/*
 * userservice API
 * userservice API documentation
 *
 * OpenAPI spec version: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.enjoyf.platform.userservice.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ProfileSummary
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-04-08T10:52:24.374+08:00")
public class ProfileSummary {
  @SerializedName("id")
  private Long id = null;

  @SerializedName("likeGames")
  private Integer likeGames = null;

  @SerializedName("likeds")
  private Integer likeds = null;

  @SerializedName("likers")
  private Integer likers = null;

  @SerializedName("pics")
  private Integer pics = null;

  @SerializedName("playingGames")
  private Integer playingGames = null;

  @SerializedName("profileNo")
  private String profileNo = null;

  public ProfileSummary id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProfileSummary likeGames(Integer likeGames) {
    this.likeGames = likeGames;
    return this;
  }

   /**
   * Get likeGames
   * @return likeGames
  **/
  @ApiModelProperty(value = "")
  public Integer getLikeGames() {
    return likeGames;
  }

  public void setLikeGames(Integer likeGames) {
    this.likeGames = likeGames;
  }

  public ProfileSummary likeds(Integer likeds) {
    this.likeds = likeds;
    return this;
  }

   /**
   * Get likeds
   * @return likeds
  **/
  @ApiModelProperty(value = "")
  public Integer getLikeds() {
    return likeds;
  }

  public void setLikeds(Integer likeds) {
    this.likeds = likeds;
  }

  public ProfileSummary likers(Integer likers) {
    this.likers = likers;
    return this;
  }

   /**
   * Get likers
   * @return likers
  **/
  @ApiModelProperty(value = "")
  public Integer getLikers() {
    return likers;
  }

  public void setLikers(Integer likers) {
    this.likers = likers;
  }

  public ProfileSummary pics(Integer pics) {
    this.pics = pics;
    return this;
  }

   /**
   * Get pics
   * @return pics
  **/
  @ApiModelProperty(value = "")
  public Integer getPics() {
    return pics;
  }

  public void setPics(Integer pics) {
    this.pics = pics;
  }

  public ProfileSummary playingGames(Integer playingGames) {
    this.playingGames = playingGames;
    return this;
  }

   /**
   * Get playingGames
   * @return playingGames
  **/
  @ApiModelProperty(value = "")
  public Integer getPlayingGames() {
    return playingGames;
  }

  public void setPlayingGames(Integer playingGames) {
    this.playingGames = playingGames;
  }

  public ProfileSummary profileNo(String profileNo) {
    this.profileNo = profileNo;
    return this;
  }

   /**
   * Get profileNo
   * @return profileNo
  **/
  @ApiModelProperty(required = true, value = "")
  public String getProfileNo() {
    return profileNo;
  }

  public void setProfileNo(String profileNo) {
    this.profileNo = profileNo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProfileSummary profileSummary = (ProfileSummary) o;
    return Objects.equals(this.id, profileSummary.id) &&
        Objects.equals(this.likeGames, profileSummary.likeGames) &&
        Objects.equals(this.likeds, profileSummary.likeds) &&
        Objects.equals(this.likers, profileSummary.likers) &&
        Objects.equals(this.pics, profileSummary.pics) &&
        Objects.equals(this.playingGames, profileSummary.playingGames) &&
        Objects.equals(this.profileNo, profileSummary.profileNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, likeGames, likeds, likers, pics, playingGames, profileNo);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProfileSummary {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    likeGames: ").append(toIndentedString(likeGames)).append("\n");
    sb.append("    likeds: ").append(toIndentedString(likeds)).append("\n");
    sb.append("    likers: ").append(toIndentedString(likers)).append("\n");
    sb.append("    pics: ").append(toIndentedString(pics)).append("\n");
    sb.append("    playingGames: ").append(toIndentedString(playingGames)).append("\n");
    sb.append("    profileNo: ").append(toIndentedString(profileNo)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}
