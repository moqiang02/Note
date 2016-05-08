package com.example.rex;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.rex.Diary;

import com.example.rex.DiaryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig diaryDaoConfig;

    private final DiaryDao diaryDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        diaryDaoConfig = daoConfigMap.get(DiaryDao.class).clone();
        diaryDaoConfig.initIdentityScope(type);

        diaryDao = new DiaryDao(diaryDaoConfig, this);

        registerDao(Diary.class, diaryDao);
    }
    
    public void clear() {
        diaryDaoConfig.getIdentityScope().clear();
    }

    public DiaryDao getDiaryDao() {
        return diaryDao;
    }

}
