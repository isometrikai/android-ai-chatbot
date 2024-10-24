package io.isometrik.androidchatbot.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

import org.json.JSONException;
import org.json.JSONObject;

import io.isometrik.androidchatbot.presentation.AiChatBotSdk;

/**
 * The helper class to save/retrieve details of logged in user.
 */
public class UserSession {

  private final SharedPreferences sharedPreferences;

  /**
   * Instantiates a new User session.
   *
   * @param context the context
   */
  @SuppressLint("HardwareIds")
  public UserSession(Context context) {

    sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
    sharedPreferences.edit()
        .putString("deviceId",
            Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID))
        .apply();
  }

  /**
   * Gets user id.
   *
   * @return the user id
   */
  public String getUserId() {
    return sharedPreferences.getString("userId", null);
  }

  /**
   * Gets user token.
   *
   * @return the user token
   */
  public String getUserToken() {
    return sharedPreferences.getString("chatUserToken", null);
  }

  /**
   * Sets user token.
   *
   * @param userToken the user token
   */
  public void setUserToken(String userToken) {
    sharedPreferences.edit().putString("chatUserToken", userToken).apply();
  }

  /**
   * Gets user name.
   *
   * @return the user name
   */
  public String getUserName() {
    return sharedPreferences.getString("userName", null);
  }

  /**
   * Gets device id.
   *
   * @return the device id
   */
  public String getDeviceId() {
    return sharedPreferences.getString("deviceId", null);
  }

  /**
   * Gets user profile pic.
   *
   * @return the user profile pic
   */
  public String getUserProfilePic() {
    return sharedPreferences.getString("userProfilePic", null);
  }

  /**
   * Gets user identifier.
   *
   * @return the user identifier
   */
  public String getUserIdentifier() {
    return sharedPreferences.getString("userIdentifier", null);
  }

  /**
   * Gets user selected.
   *
   * @return the user selected
   */
  public boolean getUserSelected() {
    return sharedPreferences.getBoolean("userSelected", true);
  }

  /**
   * Gets user metadata.
   *
   * @return the user metadata
   */
  public JSONObject getUserMetadata() {

    try {
      return new JSONObject(sharedPreferences.getString("userMetadata", ""));
    } catch (JSONException e) {
      return new JSONObject();
    }
  }

  /**
   * Gets user notification.
   *
   * @return the user notification
   */
  public boolean getUserNotification() {
    return sharedPreferences.getBoolean("userNotification", true);
  }

  /**
   * Clear.
   */
  public void clear() {

    try {
      NotificationManagerCompat.from(AiChatBotSdk.getInstance().getContext()).cancelAll();
    } catch (Exception ignore) {
    }

    sharedPreferences.edit().clear().apply();
  }

  /**
   * Switch user.
   *
   * @param userId the user id
   * @param userToken the user token
   * @param userName the user name
   * @param userIdentifier the user identifier
   * @param userProfilePic the user profile pic
   * @param userSelected the user selected
   * @param userMetadata the user metadata
   * @param notification the user notification setting
   * @param deliveryStatusUpdatedUpto the time upto which messages has been delivered to given user
   */
  public void switchUser(String userId, String userToken, String userName, String userIdentifier,
      String userProfilePic, boolean userSelected, JSONObject userMetadata, boolean notification,
      long deliveryStatusUpdatedUpto) {

    sharedPreferences.edit().putString("userId", userId).apply();
    sharedPreferences.edit().putString("userToken", userToken).apply();
    sharedPreferences.edit().putString("userName", userName).apply();
    sharedPreferences.edit().putString("userProfilePic", userProfilePic).apply();
    sharedPreferences.edit().putString("userIdentifier", userIdentifier).apply();
    sharedPreferences.edit().putBoolean("userSelected", userSelected).apply();
    sharedPreferences.edit().putString("userMetadata", userMetadata.toString()).apply();
    sharedPreferences.edit().putBoolean("userNotification", notification).apply();
    sharedPreferences.edit().putString("deviceId", Settings.Secure.getString(AiChatBotSdk.getInstance().getContext().getContentResolver(), Settings.Secure.ANDROID_ID)).apply();

    if (deliveryStatusUpdatedUpto == 0) {
      sharedPreferences.edit()
          .putLong("deliveryStatusUpdatedUpto", deliveryStatusUpdatedUpto)
          .apply();
    }
  }

  /**
   * Sets user name.
   *
   * @param userName the user name
   */
  public void setUserName(String userName) {
    sharedPreferences.edit().putString("userName", userName).apply();
  }

  /**
   * Sets user profile pic.
   *
   * @param userProfilePic the user profile pic
   */
  public void setUserProfilePic(String userProfilePic) {
    sharedPreferences.edit().putString("userProfilePic", userProfilePic).apply();
  }

  /**
   * Sets user identifier.
   *
   * @param userIdentifier the user identifier
   */
  public void setUserIdentifier(String userIdentifier) {
    sharedPreferences.edit().putString("userIdentifier", userIdentifier).apply();
  }

  /**
   * Sets user metadata.
   *
   * @param userMetadata the user metadata
   */
  public void setUserMetadata(JSONObject userMetadata) {
    sharedPreferences.edit().putString("userMetadata", userMetadata.toString()).apply();
  }

  /**
   * Sets user notification.
   *
   * @param userNotification the user notification
   */
  public void setUserNotification(boolean userNotification) {
    sharedPreferences.edit().putBoolean("userNotification", userNotification).apply();
  }

  /**
   * Sets delivery status updated upto.
   *
   * @param deliveryStatusUpdatedUpto the delivery status updated upto
   */
  public void setDeliveryStatusUpdatedUpto(long deliveryStatusUpdatedUpto) {
    sharedPreferences.edit()
        .putLong("deliveryStatusUpdatedUpto", deliveryStatusUpdatedUpto)
        .apply();
  }

  /**
   * Gets delivery status updated upto.
   *
   * @return the delivery status updated upto
   */
  public long getDeliveryStatusUpdatedUpto() {
    return sharedPreferences.getLong("deliveryStatusUpdatedUpto", 0);
  }

  //
  //public void setUserSelected(boolean userSelected) {
  //  sharedPreferences.edit().putBoolean("userSelected", userSelected).apply();
  //}
}

