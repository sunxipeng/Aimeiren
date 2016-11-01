package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.rt.orm.sqlite.SQLiteDao;

/**
 * dao业务层
 */
public abstract class WebDao extends SQLiteDao {

	public WebDao(Context context, String path, String name,
			CursorFactory factory, int version) {
		super(context, path, name, factory, version);
	}

}
