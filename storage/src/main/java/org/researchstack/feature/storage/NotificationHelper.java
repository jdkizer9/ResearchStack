package org.researchstack.feature.storage;

import android.content.Context;

import org.researchstack.feature.storage.database.TaskNotification;
import org.researchstack.foundation.components.utils.LogExt;

import java.sql.SQLException;
import java.util.List;

import co.touchlab.squeaky.db.sqlite.SQLiteDatabaseImpl;
import co.touchlab.squeaky.db.sqlite.SqueakyOpenHelper;
import co.touchlab.squeaky.table.TableUtils;

public class NotificationHelper {

    private static NotificationHelper sInstance;

    private NotificationSqueakyOpenHelper notificationSqueakyOpenHelper;

    private NotificationHelper(Context context) {
        this.notificationSqueakyOpenHelper = new NotificationSqueakyOpenHelper(context);
    }

    public static NotificationHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NotificationHelper(context);
        }
        return sInstance;
    }

    public List<TaskNotification> loadTaskNotifications() {
        LogExt.d(getClass(), "loadTaskNotifications()");
        try {
            return notificationSqueakyOpenHelper.getDao(TaskNotification.class).queryForAll().list();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveTaskNotification(TaskNotification notification) {
        LogExt.d(getClass(), "saveTaskNotification() : " + notification.id);

        try {
            notificationSqueakyOpenHelper.getDao(TaskNotification.class).createOrUpdate(notification);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTaskNotification(int taskNotificationId) {
        LogExt.d(getClass(), "deleteTaskNotification() : " + taskNotificationId);

        try {
            notificationSqueakyOpenHelper.getDao(TaskNotification.class).deleteById(taskNotificationId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static class NotificationSqueakyOpenHelper extends SqueakyOpenHelper {
        private static final String DB_NAME = "db_notification";

        private static int DB_VERSION = 1;

        NotificationSqueakyOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(android.database.sqlite.SQLiteDatabase sqLiteDatabase) {
            try {
                TableUtils.createTables(new SQLiteDatabaseImpl(sqLiteDatabase), TaskNotification.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onUpgrade(android.database.sqlite.SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                TableUtils.dropTables(new SQLiteDatabaseImpl(sqLiteDatabase),
                        true,
                        TaskNotification.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
