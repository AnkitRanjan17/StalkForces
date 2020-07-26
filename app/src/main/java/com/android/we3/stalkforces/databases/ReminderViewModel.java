package com.android.we3.stalkforces.databases;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {

    private RemindersDao remindersDao;
    private RemindersDatabase remindersDatabase;

    public ReminderViewModel(Application application) {
        super(application);

        remindersDatabase = RemindersDatabase.getDatabase(application);
        remindersDao = remindersDatabase.remindersDao();
    }

    public LiveData<List<Reminders>> getAllReminders(String contestId)
    {
        return remindersDao.getReminders(contestId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void insertReminders(Reminders reminders)
    {
        new InsertAsyncTask(remindersDao).execute(reminders);
    }

    public void deleteReminders(String id)
    {
        new DeleteAsyncTask(remindersDao).execute(id);
    }

    private class OperationsAsyncTask extends AsyncTask<Reminders, Void, Void> {

        RemindersDao mAsyncTaskDao;

        OperationsAsyncTask(RemindersDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Reminders... reminders) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(RemindersDao mRemindersDao) {
            super(mRemindersDao);
        }

        @Override
        protected Void doInBackground(Reminders... reminders) {
            mAsyncTaskDao.insertReminders(reminders[0]);
            return null;
        }
    }

    private class DeleteOperationsAsyncTask extends AsyncTask<String, Void, Void> {

        RemindersDao mAsyncTaskDao;

        DeleteOperationsAsyncTask(RemindersDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(String... id) {
            return null;
        }
    }

    private class DeleteAsyncTask extends DeleteOperationsAsyncTask {

        public DeleteAsyncTask(RemindersDao remindersDao) {
            super(remindersDao);
        }

        @Override
        protected Void doInBackground(String... id) {
            mAsyncTaskDao.deleteReminders(id[0]);
            return null;
        }
    }
}
