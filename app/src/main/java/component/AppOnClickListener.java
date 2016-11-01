/*
 * Created on 2008/08/21
 *
 */
package component;

import android.content.DialogInterface;

/**
 * OnClickListener 扩展
 */
public abstract class AppOnClickListener implements
		DialogInterface.OnClickListener {

	private String name;

	public abstract void onClick(DialogInterface dialog, int which);

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}