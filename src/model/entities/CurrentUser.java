package model.entities;

public class CurrentUser {
    private static Integer userId;

    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        CurrentUser.userId = Integer.valueOf(userId);
    }

    public static void clear() {
        userId = null;
    }
}

